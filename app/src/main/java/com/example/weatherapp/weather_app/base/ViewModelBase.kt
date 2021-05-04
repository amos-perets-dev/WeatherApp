package com.example.weatherapp.weather_app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.network.error.HandleNetworkError
import com.example.weatherapp.network.error.IHandleNetworkError
import javax.inject.Inject

open class ViewModelBase @Inject constructor(private val handleNetworkError: IHandleNetworkError) :
    ViewModel() {

    val showError = MutableLiveData<Int>()

    fun setError(error: Throwable) {
        val errorID = handleNetworkError.generateErrorID(error)
        showError.postValue(errorID)
    }

}