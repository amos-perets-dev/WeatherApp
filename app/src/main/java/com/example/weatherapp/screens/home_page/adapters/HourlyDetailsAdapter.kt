package com.example.weatherapp.screens.home_page.adapters

import android.view.ViewGroup
import com.example.weatherapp.R
import com.example.weatherapp.screens.home_page.view_holders.hourly.HourlyDetailsViewHolder
import com.example.weatherapp.screens.home_page.models.hourly.hourly_details.IHourlyDetailsItem
import com.example.weatherapp.weather_app.base.AdapterBase
import com.example.weatherapp.weather_app.base.ViewHolderBase

class HourlyDetailsAdapter(private var items: List<IHourlyDetailsItem>) : AdapterBase<IHourlyDetailsItem>(items) {

    override fun getItem(position: Int): IHourlyDetailsItem = items[position]

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBase<IHourlyDetailsItem> {

        return HourlyDetailsViewHolder(parent, R.layout.home_page_item_hourly_details)
    }

    override fun onBindViewHolder(holder: ViewHolderBase<IHourlyDetailsItem>, position: Int) {
        super.onBindViewHolder(holder, position)
        changeItemSize(holder)
    }

    private fun changeItemSize(holder: ViewHolderBase<IHourlyDetailsItem>) {

        val itemView = holder.itemView
        val layoutParams = itemView.layoutParams

        val screenWidth = itemView.resources.displayMetrics.widthPixels

        layoutParams.width = ((screenWidth / 2.5).toInt())

        itemView.layoutParams = layoutParams
        itemView.requestLayout()
    }

}