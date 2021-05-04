package com.example.weatherapp.screens.home_page.models.main

import android.content.Context
import com.example.weatherapp.weather_app.base.model.IModelBase
import io.reactivex.Flowable
import io.reactivex.Observable

interface IMainItem : IModelBase {

    fun getDateTitle() : Observable<String>

    fun getTimeTitle() : Observable<String>

    fun getMinMaxTempTitle(context: Context): Flowable<String>

    fun getFeelsLikeTitle() : String

    fun getLocationMsgTitle() : String

}