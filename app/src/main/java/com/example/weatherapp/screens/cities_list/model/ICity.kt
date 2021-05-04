package com.example.weatherapp.screens.cities_list.model

import com.example.weatherapp.weather_app.base.model.IModelBase

interface ICity : IModelBase{

    /**
     * Get the city id
     *
     * @return [Long] id
     */
    fun getCityId() : Long

    /**
     * call when the user click on the city item
     *
     * @return [DataClick]
     */
    fun onClick(longClick: Boolean, adapterPosition: Int) : DataClick

}