package com.almaz.weather_aa.core.interactors

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.almaz.weather_aa.core.WeatherRepository
import com.almaz.weather_aa.core.model.CurrentWeatherResponse
import com.almaz.weather_aa.core.model.DailyWeather
import com.almaz.weather_aa.core.model.DailyWeatherResponse
import com.almaz.weather_aa.core.model.HourlyWeather
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
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

    @SuppressLint("MissingPermission")
    fun getGeoPosition(): Single<Location> {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        locationRequest.interval = 10 * 1000 // 10 seconds
        locationRequest.fastestInterval = 5 * 1000 // 5 seconds

        return Single.create<Location>
        { emitter ->
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) emitter.onSuccess(it)
                else {
                    fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult?) {
                                if (locationResult == null) return
                                else {
                                    emitter.onSuccess(locationResult.locations[0])
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
    }
}
