package com.example.weatherapp.screens.home_page.models.hourly.hourly_details

import android.graphics.Bitmap
import com.example.weatherapp.weather_app.base.model.ModelBase
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

class HourlyDetailsModelImpl(
    private val temp: Int = 0,
    private val time: String = "",
    icon: Single<Bitmap>? = null,
    private val isFahrenheitTemp : Flowable<Boolean>
) : ModelBase(icon = icon, currentTemp = temp, isFahrenheitTemp = isFahrenheitTemp), IHourlyDetailsItem {

    override fun getForecastTime(): String  =  time

}