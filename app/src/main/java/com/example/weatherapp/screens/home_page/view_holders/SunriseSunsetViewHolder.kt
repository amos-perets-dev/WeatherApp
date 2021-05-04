package com.example.weatherapp.screens.home_page.view_holders

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.example.weatherapp.screens.home_page.models.sunrise_sunset.ISunriseSunsetItem
import com.example.weatherapp.weather_app.base.ViewHolderBase
import kotlinx.android.synthetic.main.home_page_item_sunrise_sunset.view.*

class SunriseSunsetViewHolder(parent: ViewGroup, @LayoutRes layout: Int) :
    ViewHolderBase<ISunriseSunsetItem>(parent, layout) {

    override fun bindData(model: ISunriseSunsetItem) {

        itemView.sunriseTitle.text = model.getSunriseTitle()

        itemView.sunsetTitle.text = model.getSunsetTitle()

        itemView.lengthDayTitle.text = model.getLengthOfDayTitle()

        itemView.graph.setRangDay(model.getRangeDay())

        model.getNotifyTime()
            ?.subscribe (itemView.graph::setTime)?.let {
                compositeDisposable.add(
                    it
                )
            }


    }

    override fun destroy() {
        compositeDisposable.clear()
    }

}