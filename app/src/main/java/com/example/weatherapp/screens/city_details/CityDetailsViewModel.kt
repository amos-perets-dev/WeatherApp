package com.example.weatherapp.screens.city_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.network.error.IHandleNetworkError
import com.example.weatherapp.repo.CitiesRepo
import com.example.weatherapp.repo.ICitiesRepo
import com.example.weatherapp.screens.cities_list.model.ICity
import com.example.weatherapp.weather_app.base.ViewModelBase
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CityDetailsViewModel @Inject constructor(
    private val citiesRepo: ICitiesRepo,
    private val handleNetworkError: IHandleNetworkError
) :
    ViewModelBase(handleNetworkError) {

    var cityId = 0L
    private val compositeDisposable = CompositeDisposable()

    val cityData = MutableLiveData<ICity>()
    val cityName = MutableLiveData<String>()

    init {

    }

    fun createCityDetails() {
        cityData.postValue(citiesRepo.getCityById(cityId))
        cityName.postValue(citiesRepo.getCityById(cityId)?.getLocationName())
    }

    /**
     * Call when the user click on the refresh list content
     */
    fun onClickRefreshContentList() {
        citiesRepo.getCityData(cityId)
            ?.subscribe({ data ->
                cityData.postValue(data)
            }, (this::setError))?.let {
                compositeDisposable.add(
                    it
                )
            }

    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


}