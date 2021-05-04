package com.example.weatherapp.util.converter

class TempConverterUtilImpl : ITempConverterUtil {
    override fun convertFahrenheitToCelsius(fahrenheit: Int): Int = ((fahrenheit-32)*(0.5556)).toInt()
}