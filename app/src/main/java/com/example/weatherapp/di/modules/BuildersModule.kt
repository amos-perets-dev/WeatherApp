package com.example.weatherapp.di.modules

import com.example.weatherapp.screens.cities_list.CitiesListFragment
import com.example.weatherapp.screens.city_details.CityDetailsFragment
import com.example.weatherapp.screens.home_page.HomePageFragment
import com.example.weatherapp.screens.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun bindsMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindsHomePageFragment(): HomePageFragment

    @ContributesAndroidInjector
    abstract fun bindsCitiesFragment(): CitiesListFragment

    @ContributesAndroidInjector
    abstract fun bindsCityDetailsFragment(): CityDetailsFragment

}