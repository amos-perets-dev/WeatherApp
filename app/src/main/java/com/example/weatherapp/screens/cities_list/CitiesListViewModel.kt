package com.example.weatherapp.screens.cities_list

import com.example.weatherapp.manageres.settings.ISettingsManager
import com.example.weatherapp.network.error.IHandleNetworkError
import com.example.weatherapp.repo.ICitiesRepo
import com.example.weatherapp.screens.cities_list.model.DataClick
import com.example.weatherapp.screens.cities_list.model.ICity
import com.example.weatherapp.util.SingleLiveEvent
import com.example.weatherapp.weather_app.base.ViewModelBase
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CitiesListViewModel @Inject constructor(
    private val citiesRepo: ICitiesRepo,
    private val settingsManager: ISettingsManager,
    private val handleNetworkError: IHandleNetworkError
) :
    ViewModelBase(handleNetworkError) {

    val nextPage = SingleLiveEvent<Long>()

    private val compositeDisposable = CompositeDisposable()


    init {
        onClickRefreshContentList()
    }

    fun getCities(): Observable<List<ICity>> = citiesRepo.getCitiesList()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    /**
     * Call when the user click on the toggle temp symbol
     */
    fun onClickUnitTemp() {
        compositeDisposable.add(
            settingsManager.toggleTempUnit()
                .subscribe({}, (Throwable::printStackTrace))
        )

    }

    /**
     * Call when the user filter the cities list
     */
    fun searchCity(input: String) {
        citiesRepo.generateListBySearch(input)
    }

    /**
     * Call when the user click on the refresh list content
     */
    fun onClickRefreshContentList() {
        citiesRepo.getCitiesData()
            ?.subscribe({}, this::setError)
            ?.let {
                compositeDisposable.add(
                    it
                )
            }
    }

    fun deleteItem(data: DataClick): Completable = citiesRepo.deleteItem(data.cityId)

    fun onClickRestore(): Completable = citiesRepo.clearIdsList()

    fun onClickItem(cityId: Long) {
        nextPage.postValue(cityId)
    }

    /**
     * Get the city name from the cities lis
     * @return [Single]
     */
    fun getCityName(): Observable<String>? {
        return getCities()
            .map {
                it.firstOrNull()
            }
            .map(ICity::getLocationName)
    }

}