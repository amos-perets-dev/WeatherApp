package com.example.weatherapp.network.api

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.WeatherDataResponse
import com.example.weatherapp.data.WeatherListDataResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface WeatherApi {
    companion object {
        const val SUFFIX_URL = "units=imperial&appid=${BuildConfig.WEATHER_API_KEY}"
        const val SUFFIX_ID_URL = "id=294778"
        const val SUFFIX_LAT_LONG_URL = "lat=%2\$s&lon=%3\$s"
        const val WEATHER_URL = "weather"
        const val FORECAST_URL = "forecast"

    }

    interface HomePage {

        companion object {
            const val DEFAULT_LOCATION_URL = "%1\$s?$SUFFIX_ID_URL&$SUFFIX_URL"
            const val CURRENT_LOCATION_URL = "%1\$s?$SUFFIX_LAT_LONG_URL&$SUFFIX_URL"
        }

        /**
         * Get the weather from the server
         *
         * @param url - by the location or default
         * @return [Observable][WeatherDataResponse]
         */
        @GET
        fun getWeather(@Url url: String): Observable<WeatherDataResponse>

        /**
         * Get the forecast from the server
         *
         * @param url - by the location or default
         * @return [Observable][WeatherListDataResponse]
         */
        @GET
        fun getForecast(@Url url: String): Observable<WeatherListDataResponse>

    }

    interface Cities {

        companion object {
            const val CITY_BASE_URL = "group?id=%1\$s&${SUFFIX_URL}"
        }

        /**
         * Get the cities list from the server
         *
         * @return [Observable][WeatherListDataResponse]
         */
        @GET
        fun getCities(@Url url: String): Observable<WeatherListDataResponse>

        /**
         * Get the details city from the server
         *
         * @return [Single][WeatherListDataResponse]
         */
        @GET
        fun getCity(@Url url: String): Single<WeatherListDataResponse>

    }

}