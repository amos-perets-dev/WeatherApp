package com.example.weatherapp.di.modules

import com.example.weatherapp.di.scope.AppScope
import com.example.weatherapp.network.error.HandleNetworkError
import com.example.weatherapp.network.error.IHandleNetworkError
import com.example.weatherapp.util.image.IImageUtil
import com.example.weatherapp.util.image.ImageUtilImpl
import com.example.weatherapp.util.location.ILocationUtil
import com.example.weatherapp.util.location.LocationUtilImpl
import com.example.weatherapp.util.permission.IPermissionsUtil
import com.example.weatherapp.util.permission.PermissionsUtil
import com.example.weatherapp.util.time.ITimeUtil
import com.example.weatherapp.util.time.TimeUtilImpl
import com.example.weatherapp.weather_app.WeatherApplication
import dagger.Module
import dagger.Provides

@Module
 class UtilsModule  {

    @Provides
    @AppScope
    fun provideLocationUtil(application: WeatherApplication): ILocationUtil {
        return LocationUtilImpl(application.applicationContext)
    }

    @Provides
    @AppScope
    fun provideImageUtil(application: WeatherApplication): IImageUtil {
        return ImageUtilImpl(application.applicationContext)
    }

    @Provides
    @AppScope
    fun provideTimeUtil(): ITimeUtil {
        return TimeUtilImpl()
    }

    @Provides
    @AppScope
    fun provideHandleNetworkError(): IHandleNetworkError {
        return HandleNetworkError()
    }

    @Provides
    @AppScope
    fun providePermissionsUtil(): IPermissionsUtil {
        return PermissionsUtil()
    }

}