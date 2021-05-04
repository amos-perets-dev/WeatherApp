package com.example.weatherapp.manageres.home_page

import com.example.weatherapp.weather_app.base.BaseManager
import io.reactivex.Observable

interface IHomePageManager : BaseManager {

    /**
     * Get the all items of the home page
     *
     * @return [Observable]
     */
    fun getHomePageData(): Observable<ArrayList<Any>>?
}