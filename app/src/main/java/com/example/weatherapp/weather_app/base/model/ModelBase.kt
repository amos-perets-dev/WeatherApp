package com.example.weatherapp.weather_app.base.model

import android.content.Context
import android.graphics.Bitmap
import com.example.weatherapp.R
import com.example.weatherapp.util.converter.ITempConverterUtil
import com.example.weatherapp.util.converter.TempConverterUtilImpl
import com.example.weatherapp.util.image.IImageUtil
import com.example.weatherapp.weather_app.WeatherApplication
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

open class ModelBase(
    private val locationName: String = "",
    private val weatherStateTitle: String = "",
    private val icon: Single<Bitmap>?,
    private val currentTemp: Int,
    private val isFahrenheitTemp: Flowable<Boolean>,
    val itemId : Long = 0L,
) : IModelBase {

    private val tempConverterUtil: ITempConverterUtil = TempConverterUtilImpl()

    private fun getTempWithSymbol(temp: Int, context: Context): String {
        return context.resources?.getString(R.string.temp_symbol, temp.toString()) ?: ""
    }

    private fun getCurrentTempTitle(currentTemp: Int): Flowable<String> {
        val currTempFahrenheitTitle = "$currentTemp°"
        val currTempCelsiusTitle = "${tempConverterUtil.convertFahrenheitToCelsius(currentTemp)}°"

        return isFahrenheitTemp
            .map { isFahrenheit ->
                if (isFahrenheit) currTempFahrenheitTitle else currTempCelsiusTitle
            }
    }

    protected fun getMinMaxTitle(
        minTemp: Int,
        maxTemp: Int,
        context: Context
    ): Flowable<String> {
        val minMaxFahrenheitTitle = "${getTempWithSymbol(minTemp, context)} - ${getTempWithSymbol(
            maxTemp,
            context
        )}"

        return isFahrenheitTemp
            .map { isFahrenheit ->
                if (isFahrenheit) minMaxFahrenheitTitle else getMinMaxTempCelsius(
                    minTemp,
                    maxTemp,
                    context
                )
            }
    }

    private fun getMinMaxTempCelsius(
        minTemp: Int,
        maxTemp: Int,
        context: Context
    ): String {
        val minTempCelsius = tempConverterUtil.convertFahrenheitToCelsius(minTemp)
        val maxTempCelsius = tempConverterUtil.convertFahrenheitToCelsius(maxTemp)

        return "${getTempWithSymbol(minTempCelsius, context)} - ${getTempWithSymbol(
            maxTempCelsius,
            context
        )}"
    }

    override fun getCurrTempTitle(): Flowable<String> = getCurrentTempTitle(currentTemp)

    override fun getWeatherStateTitle(): String = weatherStateTitle

    override fun getWeatherStateIcon(): Single<Bitmap>? = icon

    override fun getLocationName(): String = locationName
}