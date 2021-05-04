package com.example.weatherapp.data

import com.google.gson.annotations.SerializedName

data class WeatherListDataResponse(
    @SerializedName("list")
    val list: List<WeatherDataResponse>
)