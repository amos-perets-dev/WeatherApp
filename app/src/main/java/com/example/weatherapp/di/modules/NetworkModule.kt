package com.example.weatherapp.di.modules

import com.example.weatherapp.di.scope.AppScope
import com.example.weatherapp.network.api.WeatherApi
import com.example.weatherapp.network.base.BaseNetworkManager
import com.example.weatherapp.network.base.IBaseNetworkManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
 class NetworkModule  {

    @Provides
    @AppScope
    fun provideBaseNetworkManager(): Retrofit {
        return BaseNetworkManager().buildRetrofit()
    }

    @Provides
    @AppScope
    fun provideWeatherApiCities(retrofit: Retrofit): WeatherApi.Cities {
        return retrofit.create(WeatherApi.Cities::class.java)
    }

    @Provides
    @AppScope
    fun provideWeatherApiHomePage(retrofit: Retrofit): WeatherApi.HomePage {
        return retrofit.create(WeatherApi.HomePage::class.java)
    }

}