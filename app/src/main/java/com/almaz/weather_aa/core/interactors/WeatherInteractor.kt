package com.almaz.weather_aa.core.interactors

import com.almaz.weather_aa.BuildConfig
import com.almaz.weather_aa.core.WeatherRepository
import com.almaz.weather_aa.core.model.HourlyWeather
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class WeatherInteractor(
    private val weatherRepository: WeatherRepository
) {

    // TODO: fix getting data. Return weather for every minute
    fun getHourlyWeather(): Single<List<HourlyWeather>> =
        weatherRepository.getHourlyWeather(35.7721,-78.63861, BuildConfig.API_KEY)
            .map {
                it.data.take(10)
            }
            .subscribeOn(Schedulers.io())
}
