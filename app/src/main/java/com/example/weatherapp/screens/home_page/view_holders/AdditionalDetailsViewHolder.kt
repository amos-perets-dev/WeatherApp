package com.example.weatherapp.screens.home_page.view_holders

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.example.weatherapp.screens.home_page.models.additional_details.IAdditionalDetailsItem
import com.example.weatherapp.screens.home_page.models.main.IMainItem
import com.example.weatherapp.weather_app.base.ViewHolderBase
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.home_page_item_additional_details.view.*
import kotlinx.android.synthetic.main.home_page_item_main.view.*

class AdditionalDetailsViewHolder(parent: ViewGroup, @LayoutRes layout: Int) :
    ViewHolderBase<IAdditionalDetailsItem>(parent, layout) {

    override fun bindData(model: IAdditionalDetailsItem) {

        itemView.windTitle.text = model.getWindTitle()
        itemView.cloudinessTitle.text = model.getCloudinessTitle()
        itemView.humidityTitle.text = model.getHumidityTitle()
        itemView.pressureTitle.text = model.getPressureTitle()
        itemView.visibilityTitle.text = model.getVisibilityTitle()
    }

    override fun destroy() {

    }

}