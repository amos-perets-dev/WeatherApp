package com.example.weatherapp.screens.home_page.adapters

import android.view.ViewGroup
import com.example.weatherapp.R
import com.example.weatherapp.screens.home_page.view_holders.AdditionalDetailsViewHolder
import com.example.weatherapp.screens.home_page.view_holders.MainViewHolder
import com.example.weatherapp.screens.home_page.view_holders.SunriseSunsetViewHolder
import com.example.weatherapp.screens.home_page.view_holders.hourly.HourlyViewHolder
import com.example.weatherapp.weather_app.base.AdapterBase
import com.example.weatherapp.weather_app.base.ViewHolderBase

class HomePageAdapter(private var items: List<Any>) : AdapterBase<Any>(items) {

    companion object {
        enum class TYPES {
            MAIN,
            HOURLY,
            SUNRISE_SUNSET,
            ADDITIONAL_DETAILS
        }
    }

    override fun getItem(position: Int): Any = items[position]

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBase<Any> {

        return when (viewType) {
            TYPES.MAIN.ordinal -> MainViewHolder(parent, R.layout.home_page_item_main)
            TYPES.HOURLY.ordinal -> HourlyViewHolder(parent, R.layout.home_page_item_hourly)
            TYPES.SUNRISE_SUNSET.ordinal -> SunriseSunsetViewHolder(parent, R.layout.home_page_item_sunrise_sunset)
            else -> AdditionalDetailsViewHolder(parent, R.layout.home_page_item_additional_details)
        } as ViewHolderBase<Any>
    }

    override fun getItemViewType(position: Int): Int  =  position
}