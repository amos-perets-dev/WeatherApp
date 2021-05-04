package com.example.weatherapp.screens.city_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.weather_app.base.FragmentBase
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CityDetailsFragment : FragmentBase<CityDetailsViewModel>() {

    private val args: CityDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun getViewModel(): CityDetailsViewModel {
        return ViewModelProvider(this, viewModelFactory)
            .get(CityDetailsViewModel::class.java)
            .apply {
                cityId = args.cityId
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewHolder = ViewHolder(view, compositeDisposable)
        baseViewModel?.createCityDetails()
        baseViewModel?.cityData?.observe(viewLifecycleOwner, Observer { data ->
            viewHolder.bindData(data)
        })
        baseViewModel?.cityName?.observe(viewLifecycleOwner, Observer { name ->
            setToolbarTitle(name)
        })
    }

    override fun onClickRefreshContentList(ignored: Any) {
        baseViewModel?.onClickRefreshContentList()
    }

    override fun onClickShowCitiesList(ignored: Any) {}

    override fun onClickUnitTemp(ignored: Any) {}

    override fun onClickRestore(ignored: Any) {}

    override fun getCurrFragment(): String = this::class.java.name

    override fun getLayoutRes(): Int = R.layout.fragment_city_details

}