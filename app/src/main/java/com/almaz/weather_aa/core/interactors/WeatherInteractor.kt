package com.almaz.weather_aa.core.interactors

import com.almaz.weather_aa.core.WeatherRepository
import com.almaz.weather_aa.core.model.CurrentWeatherResponse
import com.almaz.weather_aa.core.model.DailyWeather
import com.almaz.weather_aa.core.model.DailyWeatherResponse
import com.almaz.weather_aa.core.model.HourlyWeather
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime

class WeatherInteractor(
    private val weatherRepository: WeatherRepository
) {

    // TODO: fix getting data. Return weather for every minute
    fun getHourlyWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Single<List<HourlyWeather>> =
        weatherRepository.getHourlyWeather(lat, lon, apiKey)
            .map {
                it.data.filter { weather ->
                    DateTime(weather.timestampLocal).dayOfMonth() == DateTime.now().dayOfMonth()
                }
            }
            .subscribeOn(Schedulers.io())

    fun getCurrentWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Single<CurrentWeatherResponse> {
        return weatherRepository.getCurrentWeather(lat, lon, apiKey)
    }

    fun getDailyWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Single<List<DailyWeather>> {
        return weatherRepository.getDailyWeather(lat, lon, apiKey)
            .map { it.data }
    }
}
