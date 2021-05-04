package com.example.weatherapp.screens.home_page

import android.Manifest
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.screens.home_page.adapters.HomePageAdapter
import com.example.weatherapp.screens.main.MainViewModel
import com.example.weatherapp.util.permission.IPermissionsUtil
import com.example.weatherapp.util.permission.PermissionsUtil
import com.example.weatherapp.weather_app.base.FragmentBase
import kotlinx.android.synthetic.main.fragment_home_page.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomePageFragment : FragmentBase<HomePageViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun getViewModel(): HomePageViewModel {
        return ViewModelProvider(this, viewModelFactory).get(HomePageViewModel::class.java)
    }

    @Inject lateinit var homePageAdapter : HomePageAdapter

    @Inject lateinit var permissionDialog: IPermissionsUtil

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseViewModel?.itemsList?.observe(viewLifecycleOwner, Observer { list ->

            recyclerHomePage.adapter = homePageAdapter
            homePageAdapter.updateList(list)
            loadItemsData.visibility = View.GONE
        })

        baseViewModel
            ?.getTempUnitName()
            ?.doOnNext { resId ->
                setToolbarTitle(getString(resId))
            }
            ?.subscribe({}, (Throwable::printStackTrace))
            ?.let {
                compositeDisposable.add(
                    it
                )
            }

        baseViewModel?.nextPage?.observe(
            viewLifecycleOwner,
            Observer(findNavController()::navigate)
        )

        showPermissionDialog()
    }

    private fun showPermissionDialog() {
        compositeDisposable.add(
            permissionDialog.isPermissionGranted(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
                .subscribe({ isGranted ->
                    baseViewModel?.permissionResult(isGranted)
                }, (Throwable::printStackTrace))
        )
    }

    override fun onClickRefreshContentList(ignored: Any) {
        baseViewModel?.onClickRefreshContentList()
    }

    override fun onClickShowCitiesList(ignored: Any) {
        baseViewModel?.onClickShowCitiesListList()
    }

    override fun onClickUnitTemp(ignored: Any) {
        baseViewModel?.onClickUnitTemp()
    }

    override fun onClickRestore(ignored: Any) {}

    override fun getCurrFragment(): String = this::class.java.name

    override fun getLayoutRes(): Int = R.layout.fragment_home_page


    override fun onDestroyView() {
        homePageAdapter.dispose()
        super.onDestroyView()
    }
}