package com.example.weatherapp.manageres.cities

import com.example.weatherapp.screens.cities_list.model.ICity
import com.example.weatherapp.weather_app.base.BaseManager
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ICitiesListManager : BaseManager {

    /**
     * Get the data cities by location or default
     *
     * @return [Observable][ICity]
     */
    fun getCitiesData(): Observable<MutableList<ICity>>?

    /**
     * Get the details city
     * @param id - city id
     * @return [Observable][MutableList][ICity]
     */
    fun getCityData(id: Long): Single<ICity?>?

}