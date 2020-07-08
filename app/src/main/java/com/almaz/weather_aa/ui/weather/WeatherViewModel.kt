package com.almaz.weather_aa.ui.weather

import androidx.lifecycle.MutableLiveData
import com.almaz.itis_booking.ui.base.BaseViewModel
import com.almaz.weather_aa.core.interactors.WeatherInteractor
import com.almaz.weather_aa.core.model.HourlyWeather
import com.almaz.weather_aa.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers

class WeatherViewModel(
    private val weatherInteractor: WeatherInteractor
) : BaseViewModel() {
    val hourlyWeatherLiveData = MutableLiveData<Response<List<HourlyWeather>>>()

    fun getHourlyWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ) {
        disposables.add(
            weatherInteractor.getHourlyWeather(lat, lon, apiKey)
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate{
                    showLoadingLiveData.value = false
                }
                .subscribe({
                    hourlyWeatherLiveData.value = Response.success(it)
                }, { error ->
                    hourlyWeatherLiveData.value = Response.error(error)
                    error.printStackTrace()
                })
        )
    }
}
