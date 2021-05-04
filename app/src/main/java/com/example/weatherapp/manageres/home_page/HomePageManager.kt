package com.example.weatherapp.manageres.home_page

import android.content.Context
import androidx.annotation.StringRes
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.R
import com.example.weatherapp.data.LocationDataType
import com.example.weatherapp.data.WeatherDataResponse
import com.example.weatherapp.manageres.settings.ISettingsManager
import com.example.weatherapp.network.api.WeatherApi
import com.example.weatherapp.screens.home_page.models.additional_details.AdditionalDetailsItemImpl
import com.example.weatherapp.screens.home_page.models.additional_details.IAdditionalDetailsItem
import com.example.weatherapp.screens.home_page.models.hourly.hourly.HourlyItemImpl
import com.example.weatherapp.screens.home_page.models.hourly.hourly.IHourlyItem
import com.example.weatherapp.screens.home_page.models.hourly.hourly_details.HourlyDetailsModelImpl
import com.example.weatherapp.screens.home_page.models.hourly.hourly_details.IHourlyDetailsItem
import com.example.weatherapp.screens.home_page.models.main.IMainItem
import com.example.weatherapp.screens.home_page.models.main.MainModelImpl
import com.example.weatherapp.screens.home_page.models.sunrise_sunset.ISunriseSunsetItem
import com.example.weatherapp.screens.home_page.models.sunrise_sunset.SunriseSunsetItemImpl
import com.example.weatherapp.util.image.IImageUtil
import com.example.weatherapp.util.location.ILocationUtil
import com.example.weatherapp.util.time.ITimeUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class HomePageManager @Inject constructor(
    private val settingsManager: ISettingsManager,
    private val context: Context,
    private val locationUtil: ILocationUtil,
    private val imageUtilImpl: IImageUtil,
    private val homePageAPi: WeatherApi.HomePage,
    private val timeUtil: ITimeUtil
) :
    IHomePageManager {

    private val hourlyDataNotifier = BehaviorSubject.create<List<IHourlyDetailsItem>>()

    private val resources = context.resources

    private val compositeDisposable = CompositeDisposable()

    private fun initForecast(locationData: LocationDataType?) {

        val url = if (locationData is LocationDataType.DefaultLocation) {
            String.format(WeatherApi.HomePage.DEFAULT_LOCATION_URL, WeatherApi.FORECAST_URL)
        } else {
            String.format(
                WeatherApi.HomePage.CURRENT_LOCATION_URL,
                WeatherApi.FORECAST_URL,
                locationData?.lat,
                locationData?.long
            )
        }

        compositeDisposable.add(
            homePageAPi
                .getForecast(url)
                .subscribeOn(Schedulers.io())
                .flatMapSingle {
                    return@flatMapSingle Observable.fromIterable(it.list)
                        .observeOn(AndroidSchedulers.mainThread())
                        .map { model -> convertDataModelToIHourlyDetails(model) }
                        .toList()
                }
                .doOnNext {
                    if (it is List<*>) {
                        hourlyDataNotifier.onNext(it as List<IHourlyDetailsItem>)
                    }
                }
                .subscribeEx()
        )


    }

    private fun convertDataModelToIHourlyDetails(model: WeatherDataResponse): IHourlyDetailsItem? {

        val generalWeather = model.weatherList.firstOrNull()

        val loadImage = imageUtilImpl.loadImage(
            "${BuildConfig.BASE_IMAGE_URL}${generalWeather?.icon}.png"
        )

        val timeFromDate = timeUtil.getTimeFromDate(model.time)

        return HourlyDetailsModelImpl(
            model.main?.temp?.toInt() ?: 0,
            timeFromDate,
            loadImage,
            isFahrenheitTemp = settingsManager.isFahrenheitTemp()
        )
    }

    override fun getHomePageData(): Observable<ArrayList<Any>>? {

        val locationData = locationUtil.getLocationData()

        initForecast(locationData)

        val url = if (locationData is LocationDataType.DefaultLocation) {
            String.format(WeatherApi.HomePage.DEFAULT_LOCATION_URL, WeatherApi.WEATHER_URL)
        } else {
            String.format(
                WeatherApi.HomePage.CURRENT_LOCATION_URL,
                WeatherApi.WEATHER_URL,
                locationData?.lat,
                locationData?.long
            )
        }

        return homePageAPi
            .getWeather(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data ->

                val list: ArrayList<Any> = arrayListOf()

                val mainItem = createMainItem(data, context, locationData)
                val hourlyItem = createHourlyItem()
                val sunriseSunsetItem = createSunriseSunsetItem(data)
                val additionalDetailsItem = createAdditionalDetailsItem(data)

                list.add(mainItem)
                list.add(hourlyItem)
                list.add(sunriseSunsetItem)
                list.add(additionalDetailsItem)

                return@map list
            }
    }

    private fun createMainItem(
        data: WeatherDataResponse,
        context: Context,
        locationData: LocationDataType?
    ): IMainItem {
        val feelsLikeTitle = "${data.main?.feels_like}"
        val generalWeather = data.weatherList.firstOrNull()

        val locationMsg = locationData?.title?.let { resources.getString(it) }

        return MainModelImpl(
            timeUtil,
            generalWeather?.description ?: "",
            data.main?.temp_min?.toInt() ?: 0,
            data.main?.temp_max?.toInt() ?: 0,
            data.main?.temp?.toInt() ?: 0,
            data.locationName,
            imageUtilImpl.loadImage(
                "${BuildConfig.BASE_IMAGE_URL}${generalWeather?.icon}.png"
            ),
            context.resources.getString(
                R.string.fragment_home_page_main_item_feels_like_title,
                feelsLikeTitle
            ),
            isFahrenheitTemp = settingsManager.isFahrenheitTemp(),
            locationMsg = locationMsg ?: "",
            feelsLikeValue = data.main?.feels_like ?: 0.0
        )
    }

    private fun createHourlyItem(): IHourlyItem {
        return HourlyItemImpl(
            hourlyDataNotifier
        )
    }

    private fun createAdditionalDetailsItem(data: WeatherDataResponse): IAdditionalDetailsItem {

        val speed = (data.wind?.speed ?: 0.0 * 3.6).toInt()
        val windTitle = resources?.getString(
            R.string.fragment_home_page_additional_details_item_wind_title_text,
            speed.toString()
        )

        val cloudinessTitle = resources?.getString(
            R.string.fragment_home_page_additional_details_item_cloudiness_title_text,
            data.weatherList.firstOrNull()?.description
        )

        val humidityTitle = resources?.getString(
            R.string.fragment_home_page_additional_details_item_humidity_title_text,
            "${data.main?.humidity}%"
        )

        val pressure = ((data.main?.pressure)?.toInt()).toString()
        val pressureTitle = resources?.getString(
            R.string.fragment_home_page_additional_details_item_pressure_title_text,
            pressure
        )

        val visibilityKm = ((data.visibility * 0.001).toInt()).toString()
        val visibilityTitle = resources?.getString(
            R.string.fragment_home_page_additional_details_item_visibility_title_text,
            visibilityKm
        )

        return AdditionalDetailsItemImpl(
            windTitle ?: "",
            cloudinessTitle ?: "",
            humidityTitle ?: "",
            pressureTitle ?: "",
            visibilityTitle ?: ""
        )
    }

    private fun createSunriseSunsetItem(data: WeatherDataResponse): ISunriseSunsetItem {
        val sys = data.sys

        val timezone = data.timezone

        val sunriseTime = (sys?.sunrise ?: 0L + timezone) * 1000

        val sunsetTime = (sys?.sunset ?: 0L + timezone) * 1000

        val dayTime = (sunsetTime - sunriseTime)

        val lengthDayText = timeUtil.createTimeByTwoLong(sunriseTime, sunsetTime)
        val lengthDayTitle = resources?.getString(
            R.string.fragment_home_page_sunrise_sunset_item_length_day_title_text,
            lengthDayText
        )

        return SunriseSunsetItemImpl(
            createSunriseSunsetTitle(
                sunriseTime,
                R.string.fragment_home_page_sunrise_sunset_item_sunrise_title_text
            ),
            createSunriseSunsetTitle(
                sunsetTime,
                R.string.fragment_home_page_sunrise_sunset_item_sunset_item_title_text
            ),
            lengthDayTitle ?: "",
            dayTime,
            sunriseTime
        )

    }

    private fun createSunriseSunsetTitle(time: Long, @StringRes resId: Int): String {
        val text = timeUtil.createTimeByLong(time)
        val title = resources?.getString(
            resId,
            text
        )

        return title ?: ""
    }

    override fun dispose() {
        compositeDisposable.clear()
    }

}

private fun <T> Observable<T>.subscribeEx(): Disposable {
    return subscribe({}, Throwable::printStackTrace)
}
