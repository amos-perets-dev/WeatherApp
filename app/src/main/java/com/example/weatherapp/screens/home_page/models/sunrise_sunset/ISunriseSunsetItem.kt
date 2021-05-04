package com.example.weatherapp.screens.home_page.models.sunrise_sunset

import com.example.weatherapp.data.DataRangDay
import io.reactivex.Observable

interface ISunriseSunsetItem {

    fun getSunriseTitle() : String

    fun getSunsetTitle() : String

    fun getLengthOfDayTitle() : String

    fun getNotifyTime(): Observable<Int>?

    /**
     * Get rang time of day
     *
     * @return [Pair] [first - time long] [second - sunset]
     */
    fun getRangeDay() : DataRangDay
}