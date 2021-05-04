package com.example.weatherapp.data

import com.google.gson.annotations.SerializedName

data class WeatherDataResponse(
    @SerializedName("weather")
    val weatherList: List<WeatherData> = arrayListOf(),

    @SerializedName("main")
    val main: Main? = null,

    @SerializedName("wind")
    val wind: Wind? = null,

    @SerializedName("clouds")
    val clouds: Clouds? = null,

    @SerializedName("sys")
    val sys: Sys? = null,

    @SerializedName("name")
    val locationName: String = "",

    @SerializedName("visibility")
    val visibility: Long = 0L,

    @SerializedName("dt_txt")
    val time: String = "",

    @SerializedName("timezone")
    val timezone: Long = 0L,

    @SerializedName("id")
    val id: Long = 0L


            

) {

    data class WeatherData(
        @SerializedName("description")
        val description: String,

        @SerializedName("icon")
        val icon: String

    )

    data class Main(
        @SerializedName("temp")
        val temp: Double,

        @SerializedName("feels_like")
        val feels_like: Double,

        @SerializedName("temp_min")
        val temp_min: Double,

        @SerializedName("temp_max")
        val temp_max: Double,

        @SerializedName("pressure")
        val pressure: Double,

        @SerializedName("humidity")
        val humidity: Double

    )

    data class Wind(
        @SerializedName("speed")
        val speed: Double
    )

    data class Clouds(
        @SerializedName("all")
        val clouds: Double
    )

    data class Sys(
        @SerializedName("sunrise")
        val sunrise: Long,

        @SerializedName("sunset")
        val sunset: Long
    )

}