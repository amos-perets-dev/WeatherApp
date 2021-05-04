package com.example.weatherapp.data

import androidx.annotation.StringRes
import com.example.weatherapp.R

sealed class LocationDataType() {

    @StringRes open val title: Int = 0
    open var lat: Double = 0.0
    open var long: Double = 0.0

    object ApproximateLocation : LocationDataType() {
        override val title: Int get() = R.string.approximate_location
    }

    object DefaultLocation : LocationDataType() {
        override val title: Int get() = R.string.default_location
    }

    object CurrentLocation : LocationDataType() {
        override val title: Int get() = R.string.current_location

    }
}