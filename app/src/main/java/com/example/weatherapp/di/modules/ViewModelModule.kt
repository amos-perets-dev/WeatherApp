package com.example.weatherapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.di.factory.ViewModelProviderFactory
import com.example.weatherapp.di.mapkey.ViewModelKey
import com.example.weatherapp.di.scope.AppScope
import com.example.weatherapp.screens.cities_list.CitiesListViewModel
import com.example.weatherapp.screens.city_details.CityDetailsViewModel
import com.example.weatherapp.screens.home_page.HomePageViewModel
import com.example.weatherapp.screens.main.MainViewModel
import com.example.weatherapp.weather_app.base.ViewModelBase
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule  {

    @Binds
    @IntoMap
    @ViewModelKey(HomePageViewModel::class)
    abstract fun bindHomePageViewModel(homePageViewModel: HomePageViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(CitiesListViewModel::class)
    abstract fun bindCitiesListViewModel(citiesListViewModel: CitiesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CityDetailsViewModel::class)
    abstract fun bindCityDetailsViewModel(citiesListViewModel: CityDetailsViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelBase::class)
    abstract fun bindViewModelBase(viewModelBase: ViewModelBase): ViewModel




    @Binds
    @AppScope
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}