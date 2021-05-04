package com.example.weatherapp.screens.home_page.models.main

import android.content.Context
import android.graphics.Bitmap
import com.example.weatherapp.data.LocationDataType
import com.example.weatherapp.util.time.ITimeUtil
import com.example.weatherapp.weather_app.base.model.ModelBase
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit

class MainModelImpl(
    private val timeUtil: ITimeUtil,
    weatherStateTitle: String,
    private val minTemp: Int,
    private val maxTemp: Int,
    currentTemp: Int,
    locationName: String,
    icon: Single<Bitmap>,
    private val feelsLikeTitle: String,
    private val isFahrenheitTemp: Flowable<Boolean>,
    val locationMsg: String = "",
    val feelsLikeValue: Double = 0.0
) : ModelBase(
    locationName,
    weatherStateTitle,
    icon,
    currentTemp,
    isFahrenheitTemp = isFahrenheitTemp
), IMainItem {

    private val calendar = Observable.interval(1, TimeUnit.MINUTES)
        .map { Calendar.getInstance() }
        .startWith(Calendar.getInstance())

    override fun getDateTitle(): Observable<String> {
        return calendar
            .map {
                timeUtil.getDate()
            }
    }

    override fun getTimeTitle(): Observable<String> {
        return calendar
            .map {
                timeUtil.getTime()
            }
    }

    override fun getMinMaxTempTitle(context: Context): Flowable<String> =
        getMinMaxTitle(minTemp, maxTemp, context)

    override fun getFeelsLikeTitle(): String = feelsLikeTitle
    override fun getLocationMsgTitle(): String = locationMsg
}
