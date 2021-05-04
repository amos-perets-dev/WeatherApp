package com.example.weatherapp.screens.home_page.models.hourly.hourly

import com.example.weatherapp.screens.home_page.models.hourly.hourly_details.IHourlyDetailsItem
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class HourlyItemImpl(
    private val hourlyDataNotifier: BehaviorSubject<List<IHourlyDetailsItem>>) :
    IHourlyItem {

    override fun getHourlyDetailsData(): Observable<List<IHourlyDetailsItem>> {
        return hourlyDataNotifier
    }

}