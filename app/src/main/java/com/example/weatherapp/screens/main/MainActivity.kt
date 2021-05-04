package com.example.weatherapp.screens.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.repo.ICitiesRepo
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: MainViewModel

    private var refreshListButton: AppCompatButton? = null
    private var citiesListButton: AppCompatButton? = null
    private var tempUnitButton: AppCompatButton? = null
    private var restoreListButton: AppCompatButton? = null
    private var toolBarTitle: AppCompatTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        refreshListButton = toolbar.refreshListButton
        citiesListButton = toolbar.citiesListButton
        tempUnitButton = toolbar.tempUnitButton
        restoreListButton = toolbar.restoreListButton
        toolBarTitle = toolbar.toolbar_title

    }

    fun setToolBatTitle(title: String) {
        toolBarTitle?.text = title
    }

    fun onClickRefreshContentList(consumer: io.reactivex.functions.Consumer<Any>) {
        refreshListButton?.setOnClickListener { consumer.accept(Any()) }
    }

    fun onClickShowCitiesList(consumer: io.reactivex.functions.Consumer<Any>) {
        citiesListButton?.setOnClickListener { consumer.accept(Any()) }
    }

    fun onClickChangeUnitTemp(consumer: io.reactivex.functions.Consumer<Any>) {
        tempUnitButton?.setOnClickListener { consumer.accept(Any()) }
    }

    fun onClickRestoreList(consumer: io.reactivex.functions.Consumer<Any>) {
        restoreListButton?.setOnClickListener { consumer.accept(Any()) }
    }

    fun homePageToolBarVisibility() {
        restoreListButton?.visibility = View.GONE
        citiesListButton?.visibility = View.VISIBLE
        refreshListButton?.visibility = View.VISIBLE
        tempUnitButton?.visibility = View.VISIBLE
    }

    fun citiesListToolBarVisibility() {
        restoreListButton?.visibility = View.VISIBLE
        refreshListButton?.visibility = View.VISIBLE
        tempUnitButton?.visibility = View.VISIBLE

        citiesListButton?.visibility = View.GONE
    }

    fun cityToolBarVisibility() {
        restoreListButton?.visibility = View.GONE
        citiesListButton?.visibility = View.GONE
        tempUnitButton?.visibility = View.GONE
    }
}