package com.example.weatherapp.screens.home_page.view_holders.hourly

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.example.weatherapp.screens.home_page.models.hourly.hourly_details.IHourlyDetailsItem
import com.example.weatherapp.weather_app.base.ViewHolderBase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.home_page_item_hourly_details.view.*

class HourlyDetailsViewHolder(parent: ViewGroup, @LayoutRes layout: Int) :
    ViewHolderBase<IHourlyDetailsItem>(parent, layout) {



    override fun bindData(model: IHourlyDetailsItem) {

        itemView.forecastTimeTitle.text = model.getForecastTime()

        compositeDisposable.addAll(
            model.getCurrTempTitle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itemView.forecastWeatherTempTitle::setText, Throwable::printStackTrace),

                    model
                    .getWeatherStateIcon()
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(itemView.forecastWeatherIcon::setImageBitmap, Throwable::printStackTrace)
        )


    }

    override fun destroy() {
        compositeDisposable.clear()
    }


}