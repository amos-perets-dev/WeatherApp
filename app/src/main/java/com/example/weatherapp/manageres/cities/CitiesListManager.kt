package com.example.weatherapp.manageres.cities

import android.content.Context
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.WeatherDataResponse
import com.example.weatherapp.manageres.settings.ISettingsManager
import com.example.weatherapp.network.api.WeatherApi
import com.example.weatherapp.screens.cities_list.model.CityImpl
import com.example.weatherapp.screens.cities_list.model.ICity
import com.example.weatherapp.util.image.IImageUtil
import com.example.weatherapp.util.image.ImageUtilImpl
import com.example.weatherapp.weather_app.WeatherConfiguration
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CitiesListManager @Inject constructor(
    private val settingsManager: ISettingsManager,
    private val citiesAPi: WeatherApi.Cities,
    private val context: Context,
    private val imageUtilImpl: IImageUtil
) :
    ICitiesListManager {

    override fun getCitiesData(): Observable<MutableList<ICity>> {

        return citiesAPi
            .getCities(
                String.format(
                    WeatherApi.Cities.CITY_BASE_URL,
                    WeatherConfiguration().citiesIdsList
                )
            )
            .subscribeOn(Schedulers.io())
            .flatMapSingle {
                return@flatMapSingle Observable.fromIterable(it.list)
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { model -> convertDataModelToICity(model) }
                    .toList()
            }
            .observeOn(AndroidSchedulers.mainThread())

    }

    override fun getCityData(id: Long): Single<ICity?>? {
        return citiesAPi
            .getCity(
                String.format(
                    WeatherApi.Cities.CITY_BASE_URL,
                    id
                )
            )
            .subscribeOn(Schedulers.io())
            .flatMap {
                return@flatMap Observable.fromIterable(it.list)
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { model -> convertDataModelToICity(model) }
                    .toList()
            }
            .map { it.firstOrNull() }
            .observeOn(AndroidSchedulers.mainThread())

    }

    private fun convertDataModelToICity(model: WeatherDataResponse): ICity {

        val generalWeather = model.weatherList.firstOrNull()

        val loadImage = imageUtilImpl.loadImage(
            "${BuildConfig.BASE_IMAGE_URL}${generalWeather?.icon}.png"
        )

        return CityImpl(
            generalWeather?.description ?: "",
            model.main?.temp?.toInt() ?: 0,
            model.locationName,
            loadImage,
            model.id,
            settingsManager.isFahrenheitTemp()
        )
    }

    override fun dispose() {

    }

}