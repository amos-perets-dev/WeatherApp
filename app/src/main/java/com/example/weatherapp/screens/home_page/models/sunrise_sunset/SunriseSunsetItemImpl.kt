package com.example.weatherapp.screens.home_page.models.sunrise_sunset

import com.example.weatherapp.data.DataRangDay
import com.example.weatherapp.weather_app.WeatherConfiguration
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class SunriseSunsetItemImpl(
    private val sunriseTitle: String,
    private val sunsetTitle: String,
    private val lengthDayTitle: String,
    private val dayTime: Long,
    private val sunriseTime: Long
) : ISunriseSunsetItem {

    private val timeUpdate = WeatherConfiguration.TIME_TO_UPDATE

    private val calendar = Observable.interval(10, TimeUnit.SECONDS)
        .map { Calendar.getInstance() }

    override fun getNotifyTime(): Observable<Int> {
        return calendar
            .map { Date().time }
            .map { currTime ->
                ((currTime - sunriseTime) / timeUpdate).toInt()
            }
    }

    override fun getRangeDay(): DataRangDay {
        return DataRangDay(
            ((Date().time - sunriseTime) / timeUpdate).toInt(),
            ((dayTime / timeUpdate).toInt())
        )
    }

    override fun getSunriseTitle(): String {
        return sunriseTitle
    }

    override fun getSunsetTitle(): String {
        return sunsetTitle
    }

    override fun getLengthOfDayTitle(): String {
        return lengthDayTitle
    }

}