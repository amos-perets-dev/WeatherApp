package com.example.weatherapp.repo

import com.example.weatherapp.screens.cities_list.model.ICity
import com.example.weatherapp.weather_app.base.BaseManager
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ICitiesRepo : BaseManager {

    /**
     * Add the cities list from the server
     * @param citiesDataList - list from the server
     *
     * @return [Completable]
     */
    fun addCitiesList(citiesDataList: MutableList<ICity>): Completable

    /**
     * Get the details city
     * @param id - city id
     *
     * @return [ICity]
     */
    fun getCityById(id: Long): ICity?

    /**
     * Get the cities list
     *
     * @return [Observable][ICity]
     */
    fun getCitiesList(): Observable<List<ICity>>

    /**
     *  Generate new cities list
     *  @param input - search term
     */
    fun generateListBySearch(input: String)

    /**
     * Delete city item
     * @param cityId - city id that need to delete
     *
     * @return [Completable]
     */
    fun deleteItem(cityId: Long): Completable

    /**
     * cleaer the ids list
     *
     * @return [Completable]
     */
    fun clearIdsList(): Completable

    /**
     * Get the cities data
     *
     * @return [Completable]
     */
    fun getCitiesData(): Completable?

    /**
     * Get the city data
     *
     * @return [Single][ICity]
     */
    fun getCityData(id: Long): Single<ICity?>?
}