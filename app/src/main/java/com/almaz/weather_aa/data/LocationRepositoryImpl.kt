package com.almaz.weather_aa.data

import com.almaz.weather_aa.BuildConfig
import com.almaz.weather_aa.core.interfaces.LocationRepository
import com.almaz.weather_aa.core.model.CurrentWeather
import io.reactivex.Observable
import io.reactivex.Single

class LocationRepositoryImpl(private val weatherService: WeatherService) : LocationRepository {

    // TODO: get locations lat & lon from DB
    override fun getLocations(): Single<List<CurrentWeather>> {
        return  Observable.fromIterable(ITERABLE_ARRAY)
            .map {
                weatherService.getCurrentWeather(55.830433, 49.066082, BuildConfig.API_KEY)
            }
            .flatMapSingle {
                it.map { w ->
                    w.data[0]
                }
            }
            .toList()
    }

    companion object {
        private val ITERABLE_ARRAY = listOf(0, 1)
    }
}
