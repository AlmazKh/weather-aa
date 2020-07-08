package com.almaz.weather_aa.core

import com.almaz.weather_aa.core.model.CurrentWeatherResponse
import com.almaz.weather_aa.core.model.DailyWeatherResponse
import com.almaz.weather_aa.core.model.HourlyWeatherResponse
import io.reactivex.Single

interface WeatherRepository {

    fun getCurrentWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Single<CurrentWeatherResponse>

    fun getDailyWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Single<DailyWeatherResponse>

    fun getHourlyWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Single<HourlyWeatherResponse>
}