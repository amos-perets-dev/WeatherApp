package com.example.weatherapp.screens.home_page.models.hourly.hourly_details

import com.example.weatherapp.weather_app.base.model.IModelBase

interface IHourlyDetailsItem : IModelBase {

    fun getForecastTime() : String

}