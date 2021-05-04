package com.example.weatherapp.screens.city_details

import android.view.View
import com.example.weatherapp.screens.cities_list.model.ICity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_city_details.view.*

class ViewHolder(
    private val view: View,
    private val compositeDisposable: CompositeDisposable) {

    fun bindData(model: ICity?) {

        if(model == null) return

        compositeDisposable.addAll(
            model
                .getWeatherStateIcon()
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(view.cityDetailsWeatherIcon::setImageBitmap, Throwable::printStackTrace),

            model
                .getCurrTempTitle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((view.cityDetailsCurrentTempTitle::setText), (Throwable::printStackTrace))
        )

        view.cityDetailsLocationTitle.text = model.getLocationName()

        view.cityDetailsGeneralWeatherTitle.text = model.getWeatherStateTitle()


    }


}