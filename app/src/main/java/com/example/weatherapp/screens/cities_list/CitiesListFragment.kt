package com.example.weatherapp.screens.cities_list

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.screens.cities_list.CitiesListFragmentDirections.Companion.actionCitiesListFragmentToCityDetailsFragment
import com.example.weatherapp.screens.cities_list.dapter.CitiesListAdapter
import com.example.weatherapp.screens.cities_list.model.DataClick
import com.example.weatherapp.screens.cities_list.model.ICity
import com.example.weatherapp.util.views.GeneralDialog
import com.example.weatherapp.weather_app.base.FragmentBase
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cities_list.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CitiesListFragment () :
    FragmentBase<CitiesListViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var citiesListAdapter : CitiesListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerCities.adapter = citiesListAdapter

        val cities = baseViewModel
            ?.getCities()

        baseViewModel?.nextPage?.observe(
            viewLifecycleOwner,
            Observer { id ->
                findNavController().navigate(
                    actionCitiesListFragmentToCityDetailsFragment(id)
                )
            }
        )
        compositeDisposable.addAll(
            baseViewModel
                ?.getCityName()
                ?.subscribe({
                    setToolbarTitle(it)
                }, Throwable::printStackTrace),

            cities
                ?.subscribe((citiesListAdapter::updateList), (Throwable::printStackTrace)),

            cities
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.switchMap { citiesList ->
                    if (citiesList.isEmpty()) return@switchMap Observable.just("")
                    return@switchMap RxRecyclerView.scrollEvents(recyclerCities)
                        .observeOn(Schedulers.io())
                        .map { (recyclerCities.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() }
                        .filter { it != -1 }
                        .map(citiesList::get)
                        .map(ICity::getLocationName)
                }
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe((this::setToolbarTitle), Throwable::printStackTrace),

            citiesListAdapter
                .onClickItem()
                .subscribe({ baseViewModel?.onClickItem(it.cityId) }, (Throwable::printStackTrace)),

            citiesListAdapter
                .onLongClickItem()
                .subscribe({ showDialog(it) }, (Throwable::printStackTrace)),

            )

        searchCities.setQuery("", true)
        searchCities.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                baseViewModel?.searchCity(newText ?: "")
                return false
            }

        })

    }

    /**
     * Call when the user click on the delete item
     */
    private fun showDialog(data: DataClick) {
        val dialog = GeneralDialog
            .Builder()
            .setContext(requireActivity())
            .setTitle(getString(R.string.general_dialog_main_title_text, data.cityName))
            .build()
        dialog?.show()

        dialog?.getOkClick()
            ?.flatMapCompletable {
                baseViewModel?.deleteItem(data)
                    ?.doOnEvent {
                        citiesListAdapter.removeAt(data.position)
                        dialog.closeDialog()
                    }
            }
            ?.subscribe({}, (Throwable::printStackTrace))
            ?.let {
                compositeDisposable.add(
                    it
                )
            }

    }

    override fun onClickRefreshContentList(ignored: Any) {
        baseViewModel?.onClickRefreshContentList()
    }

    override fun onClickShowCitiesList(ignored: Any) {}

    override fun onClickUnitTemp(ignored: Any) {
        baseViewModel?.onClickUnitTemp()
    }

    override fun onClickRestore(ignored: Any) {
        baseViewModel
            ?.onClickRestore()
            ?.subscribe({citiesListAdapter.notifyDataSetChanged()}, (Throwable::printStackTrace))
            ?.let {
                compositeDisposable.add(
                    it
                )
            }
    }

    override fun getCurrFragment(): String = this::class.java.name

    override fun getLayoutRes(): Int = R.layout.fragment_cities_list


    override fun getViewModel(): CitiesListViewModel {
        return ViewModelProvider(this, viewModelFactory).get(CitiesListViewModel::class.java)
    }

    override fun onDestroyView() {
        searchCities?.setQuery("", true)
        citiesListAdapter.dispose()
        super.onDestroyView()
    }
}