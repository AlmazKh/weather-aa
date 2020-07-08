package com.almaz.weather_aa.ui.weather

import com.almaz.itis_booking.ui.base.BaseViewModel
import com.almaz.weather_aa.core.interactors.WeatherInteractor
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class WeatherViewModel(private val weatherInteractor: WeatherInteractor) : BaseViewModel() {

    fun getDailyWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ) {
        weatherInteractor.getDailyWeather(lat, lon, apiKey)
            .doOnSubscribe {
                //loading show
            }
            .doAfterTerminate {
                //loading hide
            }
            .subscribeBy(onSuccess = {

            }, onError = {

            }).addTo(disposables)
    }

}