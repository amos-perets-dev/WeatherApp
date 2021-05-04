package com.example.weatherapp.screens.cities_list.model

import android.graphics.Bitmap
import com.example.weatherapp.weather_app.base.model.ModelBase
import io.reactivex.Flowable
import io.reactivex.Single

class CityImpl(
    weatherStateTitle: String = "",
    currentTemp: Int = 0,
    private val cityName: String = "",
    icon: Single<Bitmap>? = null,
    private val id : Long = 0L,
    private val isFahrenheitTemp : Flowable<Boolean>
) : ModelBase(cityName, weatherStateTitle, icon, currentTemp, isFahrenheitTemp), ICity {

    override fun getCityId(): Long = id
    override fun onClick(longClick: Boolean, adapterPosition: Int): DataClick {
        return DataClick(id, cityName, adapterPosition, longClick)
    }

}