package com.almaz.weather_aa.core.interactors

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import com.almaz.weather_aa.core.WeatherRepository
import com.almaz.weather_aa.core.model.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime

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

    @SuppressLint("CheckResult")
    fun getWeather(): Single<List<SavedLocation>> {
        return getSavedLocations()
    }

    @SuppressLint("MissingPermission")
    fun getGeoPosition(): Single<Location> {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10 * 1000 // 10 seconds
        locationRequest.fastestInterval = 5 * 1000 // 5 seconds

        val savedLocation = Single.create<Location> { emitter ->
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    emitter.onSuccess(it)
                } else {
                    fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult?) {
                                if (locationResult == null) return
                                else {
                                    emitter.onSuccess(locationResult.locations[0])
                                    //TODO
//                                    fusedLocationClient.removeLocationUpdates(this)
                                }
                            }
                        },
                        null
                    );
                }
            }.addOnFailureListener {
                emitter.onError(it)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        return savedLocation
    }

    @SuppressLint("CheckResult")
    fun saveLocation(lat: String, lon: String): Completable {
        return weatherRepository.saveLocation(
            createSavedLocation(lat, lon)
        )
    }

    fun getSavedLocations(): Single<List<SavedLocation>> {
        return weatherRepository.getSavedLocations()
    }

    private fun createSavedLocation(lat: String, lon: String) = SavedLocation(0, lat, lon)
}
