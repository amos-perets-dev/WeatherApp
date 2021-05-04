package com.example.weatherapp.screens.home_page.view_holders

import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.example.weatherapp.screens.home_page.models.main.IMainItem
import com.example.weatherapp.weather_app.base.ViewHolderBase
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.home_page_item_main.view.*
import kotlinx.android.synthetic.main.home_page_item_sunrise_sunset.view.*

class MainViewHolder(parent: ViewGroup, @LayoutRes layout: Int) :
    ViewHolderBase<IMainItem>(parent, layout) {

    override fun bindData(model: IMainItem) {

        compositeDisposable.addAll(
            model
                .getWeatherStateIcon()
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(itemView.weatherIcon::setImageBitmap, Throwable::printStackTrace),

            model
                .getDateTitle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itemView.dateTitle::setText, Throwable::printStackTrace),

            model
                .getTimeTitle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itemView.timeTitle::setText, Throwable::printStackTrace),

            model
                .getMinMaxTempTitle(itemView.context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itemView.minMaxTempTitle::setText, Throwable::printStackTrace),

            model
                .getCurrTempTitle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itemView.currentTempTitle::setText, Throwable::printStackTrace),

            )

        itemView.generalWeatherTitle.text = model.getWeatherStateTitle()

        itemView.feelLikeTitle.text = model.getFeelsLikeTitle()

        itemView.locationTitle.text = model.getLocationName()

        itemView.locationMsgTitle.text = model.getLocationMsgTitle()
    }

    override fun destroy() {
        compositeDisposable.clear()
    }

}