package com.example.weatherapp.di.modules

import com.example.weatherapp.di.scope.AppScope
import com.example.weatherapp.manageres.cities.CitiesListManager
import com.example.weatherapp.manageres.cities.ICitiesListManager
import com.example.weatherapp.manageres.home_page.HomePageManager
import com.example.weatherapp.manageres.home_page.IHomePageManager
import com.example.weatherapp.manageres.realm.IRealmManager
import com.example.weatherapp.manageres.realm.RealmManager
import com.example.weatherapp.manageres.settings.ISettingsManager
import com.example.weatherapp.manageres.settings.SettingsManager
import com.example.weatherapp.network.api.WeatherApi
import com.example.weatherapp.repo.CitiesRepo
import com.example.weatherapp.repo.ICitiesRepo
import com.example.weatherapp.screens.cities_list.dapter.CitiesListAdapter
import com.example.weatherapp.screens.cities_list.model.ICity
import com.example.weatherapp.screens.home_page.adapters.HomePageAdapter
import com.example.weatherapp.util.image.IImageUtil
import com.example.weatherapp.util.image.ImageUtilImpl
import com.example.weatherapp.util.location.ILocationUtil
import com.example.weatherapp.util.time.ITimeUtil
import com.example.weatherapp.weather_app.WeatherApplication
import dagger.Module
import dagger.Provides

@Module(
    includes = [ViewModelModule::class, NetworkModule::class, UtilsModule::class]
)
class AppModule {

    @Provides
    @AppScope
    fun provideRealmManager(): IRealmManager {
        return RealmManager()
    }

    @Provides
    @AppScope
    fun provideSettingsManager(realmManager: IRealmManager): ISettingsManager {
        return SettingsManager(realmManager)
    }


    @Provides
    @AppScope
    fun provideCitiesListManager(
        settingsManager: ISettingsManager,
        citiesAPi: WeatherApi.Cities,
        application: WeatherApplication,
        imageUtil: IImageUtil
    ): ICitiesListManager {
        return CitiesListManager(
            settingsManager,
            citiesAPi,
            application.applicationContext,
            imageUtil
        )
    }

    @Provides
    @AppScope
    fun provideCitiesRepo(
        citiesListManager: ICitiesListManager,
        realmManager: IRealmManager
    ): ICitiesRepo {
        return CitiesRepo(citiesListManager, realmManager)
    }

    @Provides
    @AppScope
    fun provideHomePageManager(
        settingsManager: ISettingsManager,
        application: WeatherApplication,
        locationUtil: ILocationUtil,
        imageUtilImpl: IImageUtil,
        homePageAPi: WeatherApi.HomePage,
        timeUtil: ITimeUtil
    ): IHomePageManager {
        return HomePageManager(
            settingsManager,
            application.applicationContext,
            locationUtil,
            imageUtilImpl,
            homePageAPi,
            timeUtil
        )
    }

    @Provides
    @AppScope
    fun provideHomePageAdapter(): HomePageAdapter {
        return HomePageAdapter(arrayListOf())
    }

    @Provides
    @AppScope
    fun provideCitiesListAdapter(): CitiesListAdapter {
        return CitiesListAdapter(arrayListOf())
    }


}