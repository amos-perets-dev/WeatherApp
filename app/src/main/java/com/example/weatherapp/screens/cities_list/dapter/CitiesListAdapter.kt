package com.example.weatherapp.screens.cities_list.dapter

import android.view.ViewGroup
import com.example.weatherapp.R
import com.example.weatherapp.screens.cities_list.model.DataClick
import com.example.weatherapp.screens.cities_list.model.ICity
import com.example.weatherapp.screens.cities_list.view_holder.CityViewHolder
import com.example.weatherapp.weather_app.base.AdapterBase
import com.example.weatherapp.weather_app.base.ViewHolderBase
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class CitiesListAdapter (private var items: List<ICity>) : AdapterBase<ICity>(items) {

    private val onClickItem = PublishSubject.create<DataClick>()

    override fun getItem(position: Int): ICity = items[position]

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBase<ICity> {
        return CityViewHolder(parent, R.layout.city_item, onClickItem)
    }

    fun onLongClickItem(): Observable<DataClick> {
        return onClickItem
            .hide()
            .filter { it.isLongClick }
    }

    fun onClickItem(): Observable<DataClick> {
       return onClickItem
            .hide()
            .filter { it.isLongClick.not() }
    }
}