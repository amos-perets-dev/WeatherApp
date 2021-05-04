package com.example.weatherapp.weather_app

import android.app.Application
import com.example.weatherapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

class WeatherApplication : Application(), HasAndroidInjector {

    @Inject internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val realmConfiguration = RealmConfiguration.Builder()
            .name("user.realm")
            .encryptionKey(ByteArray(64))
            .schemaVersion(1)
            .build()

        Realm.setDefaultConfiguration(realmConfiguration)

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

}