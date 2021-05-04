package com.example.weatherapp.manageres.settings

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface ISettingsManager {

    /**
     * Notifier if the temp' symbol is f / C
     *
     * @return [Flowable][Boolean]
     */
    fun isFahrenheitTemp() : Flowable<Boolean>

    /**
     * Change the temp' symbol between f - c
     *
     * @return [Completable]
     */
    fun toggleTempUnit(): Completable
}