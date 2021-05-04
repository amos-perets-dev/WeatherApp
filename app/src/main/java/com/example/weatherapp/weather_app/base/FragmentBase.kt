package com.example.weatherapp.weather_app.base

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.weatherapp.screens.cities_list.CitiesListFragment
import com.example.weatherapp.screens.city_details.CityDetailsFragment
import com.example.weatherapp.screens.home_page.HomePageFragment
import com.example.weatherapp.screens.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer


abstract class FragmentBase<VM : ViewModelBase> : DaggerFragment() {

    protected val compositeDisposable = CompositeDisposable()
    protected var baseViewModel: VM? = null

    abstract fun getViewModel(): VM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseViewModel = getViewModel()
        // Inflate the layout for this fragment
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarVisibility(getCurrFragment())
        val activity = activity as MainActivity

        activity.onClickRefreshContentList(Consumer(this::onClickRefreshContentList))
        activity.onClickShowCitiesList(Consumer(this::onClickShowCitiesList))
        activity.onClickChangeUnitTemp(Consumer(this::onClickUnitTemp))
        activity.onClickRestoreList(Consumer(this::onClickRestore))

        baseViewModel?.showError?.observe(viewLifecycleOwner, Observer { errorId ->
            showError(view, errorId)
        })
    }

    private fun showError(view: View, @StringRes errorId: Int) {
        Snackbar.make(view, resources.getString(errorId), Snackbar.LENGTH_LONG)
            .setAction(getString(com.example.weatherapp.R.string.general_error_close_text)) {}
            .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            .show()
    }

    private fun toolbarVisibility(fragmentName: String) {
        val activity = activity as MainActivity

        when (fragmentName) {
            HomePageFragment::class.java.name -> activity.homePageToolBarVisibility()
            CitiesListFragment::class.java.name -> activity.citiesListToolBarVisibility()
            CityDetailsFragment::class.java.name -> activity.cityToolBarVisibility()
        }
    }

    fun setToolbarTitle(title: String) {
        val activity = activity as MainActivity
        activity.setToolBatTitle(title)
    }

    abstract fun onClickRefreshContentList(ignored: Any)

    abstract fun onClickShowCitiesList(ignored: Any)

    abstract fun onClickUnitTemp(ignored: Any)

    abstract fun onClickRestore(ignored: Any)

    abstract fun getCurrFragment(): String

    @LayoutRes
    abstract fun getLayoutRes(): Int

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }
}