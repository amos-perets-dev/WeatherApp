package com.example.weatherapp.di

import com.example.weatherapp.di.modules.AppModule
import com.example.weatherapp.di.modules.BuildersModule
import com.example.weatherapp.di.scope.AppScope
import com.example.weatherapp.weather_app.WeatherApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        BuildersModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: WeatherApplication): Builder
        fun build(): AppComponent
    }

    fun inject(application: WeatherApplication)

}