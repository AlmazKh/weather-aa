package com.almaz.weather_aa.data

import com.almaz.weather_aa.core.WeatherRepository
import com.almaz.weather_aa.core.model.CurrentWeatherResponse
import com.almaz.weather_aa.core.model.DailyWeatherResponse
import com.almaz.weather_aa.core.model.HourlyWeatherResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WeatherRepositoryImpl(private val weatherService: WeatherService) : WeatherRepository {

    override fun getCurrentWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Single<CurrentWeatherResponse> {
        return weatherService.getCurrentWeather(lat, lon, apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getDailyWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Single<DailyWeatherResponse> {
        return weatherService.getDailyWeather(lat, lon, apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getHourlyWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Single<HourlyWeatherResponse> {
        return weatherService.getHourlyWeather(lat, lon, apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
