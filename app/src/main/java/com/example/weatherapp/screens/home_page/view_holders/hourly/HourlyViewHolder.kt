package com.example.weatherapp.screens.home_page.view_holders.hourly

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.screens.home_page.adapters.HourlyDetailsAdapter
import com.example.weatherapp.screens.home_page.models.hourly.hourly.IHourlyItem
import com.example.weatherapp.weather_app.base.ViewHolderBase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.home_page_item_hourly.view.*

class HourlyViewHolder(parent: ViewGroup, @LayoutRes layout: Int) :
    ViewHolderBase<IHourlyItem>(parent, layout) {

    private val hourlyDetailsAdapter = HourlyDetailsAdapter(arrayListOf())

    override fun bindData(model: IHourlyItem) {

        compositeDisposable.add(
            model.getHourlyDetailsData()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { hourlyDetailsData ->
                    itemView.recyclerHourlyDetails.layoutManager =
                        LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                    itemView.recyclerHourlyDetails.adapter = hourlyDetailsAdapter
                    hourlyDetailsAdapter.updateList(hourlyDetailsData)
                    itemView.loadData.visibility = View.INVISIBLE
                }
                .subscribe({}, (Throwable::printStackTrace))

        )

    }

    override fun destroy() {
        hourlyDetailsAdapter.dispose()
        compositeDisposable.clear()

    }


}