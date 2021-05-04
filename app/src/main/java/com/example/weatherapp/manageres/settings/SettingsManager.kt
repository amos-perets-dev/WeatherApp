package com.example.weatherapp.manageres.settings

import com.example.weatherapp.manageres.realm.IRealmManager
import com.example.weatherapp.manageres.realm.RealmManager
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class SettingsManager @Inject constructor(private val realmManager: IRealmManager) :
    ISettingsManager {

    override fun isFahrenheitTemp(): Flowable<Boolean> =
        realmManager.getUserProfileDataChanges()
            .map { it.isFahrenheit }

    override fun toggleTempUnit(): Completable {
        return realmManager.setIsFahrenheit()
    }

}