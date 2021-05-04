package com.example.weatherapp.screens.main

import androidx.lifecycle.ViewModel
import com.example.weatherapp.repo.ICitiesRepo
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(private val citiesRepo: ICitiesRepo): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    init {
        citiesRepo
            .getCitiesData()
            ?.subscribe({}, (Throwable::printStackTrace))
            ?.let {
                compositeDisposable.add(
                    it
                )
            }

    }

    override fun onCleared() {
        citiesRepo.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}