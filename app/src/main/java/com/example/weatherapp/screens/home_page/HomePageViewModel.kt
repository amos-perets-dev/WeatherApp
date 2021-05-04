package com.example.weatherapp.screens.home_page

import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.R
import com.example.weatherapp.manageres.settings.ISettingsManager
import com.example.weatherapp.manageres.home_page.IHomePageManager
import com.example.weatherapp.network.error.IHandleNetworkError
import com.example.weatherapp.util.SingleLiveEvent
import com.example.weatherapp.weather_app.base.ViewModelBase
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val settingsManager: ISettingsManager,
    private val homePageManager: IHomePageManager,
    private val handleNetworkError: IHandleNetworkError
) :
    ViewModelBase(handleNetworkError) {

    val itemsList = MutableLiveData<List<Any>>()
    val nextPage = SingleLiveEvent<Int>()

    private val compositeDisposable = CompositeDisposable()

    init {
        initData()
    }

    private fun initData() {
        homePageManager
            .getHomePageData()
            ?.subscribeOn(Schedulers.io())
            ?.subscribe(itemsList::postValue, this::setError)
            ?.let {
                compositeDisposable.add(
                    it
                )
            }
    }

    fun onClickRefreshContentList() {
        initData()
    }

    fun onClickShowCitiesListList() {
        nextPage.postValue(R.id.action_HomePageFragment_to_CitiesListFragment)
    }

    fun getTempUnitName(): Flowable<Int> {
        return settingsManager
            .isFahrenheitTemp()
            .map { isFahrenheitTemp ->
                if (isFahrenheitTemp) R.string.fahrenheit_title_name
                else R.string.celsius_title_name
            }

    }

    fun onClickUnitTemp() {
        compositeDisposable.add(
            settingsManager.toggleTempUnit()
                .subscribe({}, (Throwable::printStackTrace))
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        homePageManager.dispose()
        super.onCleared()
    }

    fun permissionResult(isNeedUpdate: Boolean) {
        if (isNeedUpdate.not()) return
        initData()
    }

}