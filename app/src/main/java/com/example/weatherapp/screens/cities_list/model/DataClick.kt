package com.example.weatherapp.screens.cities_list.model

data class DataClick(
    val cityId: Long = 0L,
    val cityName: String = "",
    val position: Int = -1,
    val isLongClick: Boolean = false
) {
}