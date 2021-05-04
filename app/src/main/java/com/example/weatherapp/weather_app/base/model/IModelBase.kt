package com.example.weatherapp.weather_app.base.model

import android.graphics.Bitmap
import io.reactivex.Flowable
import io.reactivex.Single

interface IModelBase {

    fun getCurrTempTitle(): Flowable<String>

    fun getLocationName(): String

    fun getWeatherStateTitle(): String

    fun getWeatherStateIcon(): Single<Bitmap>?

}