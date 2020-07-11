package com.almaz.weather_aa.core.interactors

import android.annotation.SuppressLint
import android.location.Location
import com.almaz.weather_aa.core.WeatherRepository
import com.almaz.weather_aa.core.model.CurrentWeatherResponse
import com.almaz.weather_aa.core.model.DailyWeather
import com.almaz.weather_aa.core.model.DailyWeatherResponse
import com.almaz.weather_aa.core.model.HourlyWeather
import com.google.android.gms.location.FusedLocationProviderClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WeatherInteractor(
    private val weatherRepository: WeatherRepository,
    private val fusedLocationClient: FusedLocationProviderClient
) {

    // TODO: fix getting data. Return weather for every minute
    fun getHourlyWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Single<List<HourlyWeather>> =
        weatherRepository.getHourlyWeather(lat, lon, apiKey)
            .map {
                it.data.take(10)
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

    @SuppressLint("MissingPermission")
    fun getGeoPosition(): Single<Location> =
        Single.create<Location> { emitter ->
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) emitter.onSuccess(it)
            }.addOnFailureListener {
                emitter.onError(it)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
