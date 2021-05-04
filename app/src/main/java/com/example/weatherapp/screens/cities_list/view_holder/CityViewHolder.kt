package com.example.weatherapp.screens.cities_list.view_holder

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.example.weatherapp.screens.cities_list.model.DataClick
import com.example.weatherapp.screens.cities_list.model.ICity
import com.example.weatherapp.weather_app.base.ViewHolderBase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.city_item.view.*

class CityViewHolder(
    parent: ViewGroup,
    @LayoutRes layout: Int,
    private val onClickItem: PublishSubject<DataClick>
) :
    ViewHolderBase<ICity>(parent, layout) {

    override fun bindData(model: ICity) {

        itemView.setOnClickListener { onClick(model) }
        itemView.setOnLongClickListener {
            onClick(model, true)
            return@setOnLongClickListener true
        }

        itemView.cityGeneralWeatherTitle.text = model.getWeatherStateTitle()
        compositeDisposable
            .addAll(
                model
                    .getWeatherStateIcon()
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(itemView.cityWeatherIcon::setImageBitmap, Throwable::printStackTrace),

                model.getCurrTempTitle()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(itemView.cityMinMaxTempTitle::setText, Throwable::printStackTrace)
            )


        itemView.cityTitle.text = model.getLocationName()
    }

    private fun onClick(
        model: ICity,
        isLongClick: Boolean = false
    ) {
        onClickItem.onNext(model.onClick(isLongClick, adapterPosition))
    }

    override fun destroy() {
        compositeDisposable.clear()
    }

}