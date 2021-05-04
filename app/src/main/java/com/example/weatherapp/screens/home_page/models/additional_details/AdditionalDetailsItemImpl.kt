package com.example.weatherapp.screens.home_page.models.additional_details

class AdditionalDetailsItemImpl(
    private val windTitle: String,
    private val cloudinessTitle: String,
    private val humidityTitle: String,
    private val pressureTitle: String,
    private val visibilityTitle: String
) : IAdditionalDetailsItem {
    override fun getWindTitle(): String {
        return windTitle
    }

    override fun getCloudinessTitle(): String {
        return cloudinessTitle

    }

    override fun getHumidityTitle(): String {
        return humidityTitle

    }

    override fun getPressureTitle(): String {
        return pressureTitle

    }

    override fun getVisibilityTitle(): String {
        return visibilityTitle

    }
}