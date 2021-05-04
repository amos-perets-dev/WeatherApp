package com.example.weatherapp.util.location

import com.example.weatherapp.data.LocationDataType

interface ILocationUtil {

    /**
     * Get the location data from the location service
     *
     * @return [LocationDataType]
     */
    fun getLocationData() : LocationDataType?

}