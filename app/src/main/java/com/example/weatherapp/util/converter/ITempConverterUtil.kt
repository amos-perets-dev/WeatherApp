package com.example.weatherapp.util.converter

interface ITempConverterUtil {
    /**
     * Convert the temp' to the cel'
     *
     * @param fahrenheit- need to be converted
     */
    fun convertFahrenheitToCelsius(fahrenheit : Int) : Int
}