package com.example.weatherapp.screens.home_page.models.hourly.hourly

import com.example.weatherapp.screens.home_page.models.hourly.hourly_details.IHourlyDetailsItem
import io.reactivex.Observable

interface IHourlyItem {

    fun getHourlyDetailsData() : Observable<List<IHourlyDetailsItem>>

}