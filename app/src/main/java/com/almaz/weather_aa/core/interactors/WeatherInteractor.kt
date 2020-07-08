package com.almaz.weather_aa.core.interactors

import com.almaz.weather_aa.core.WeatherRepository
import com.almaz.weather_aa.core.model.CurrentWeatherResponse
import com.almaz.weather_aa.core.model.DailyWeatherResponse
import com.almaz.weather_aa.core.model.HourlyWeatherResponse
import io.reactivex.Single

class WeatherInteractor(private val repository: WeatherRepository) {

    fun getCurrentWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Single<CurrentWeatherResponse> {
       return repository.getCurrentWeather(lat, lon, apiKey)
    }

    fun getDailyWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Single<DailyWeatherResponse> {
        return repository.getDailyWeather(lat, lon, apiKey)
    }

    fun getHourlyWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Single<HourlyWeatherResponse> {
        return repository.getHourlyWeather(lat, lon, apiKey)
    }
}
