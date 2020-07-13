package com.almaz.weather_aa.data

import com.almaz.weather_aa.BuildConfig
import com.almaz.weather_aa.core.interfaces.LocationRepository
import com.almaz.weather_aa.core.model.CurrentWeather
import io.reactivex.Observable
import io.reactivex.Single

class LocationRepositoryImpl(private val weatherService: WeatherService) : LocationRepository {

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
        /*return Single.create { emitter ->
            val lst = mutableListOf<CurrentWeather>()

            weatherService.getCurrentWeather(55.830433, 49.066082, BuildConfig.API_KEY)
                .map {
                    lst.add(it.data[0])
                }
            weatherService.getCurrentWeather(35.7721, -78.63861, BuildConfig.API_KEY)
                .map {
                    lst.add(it.data[0])
                }
            emitter.onSuccess(lst)
        }*/
    }

    companion object {
        private val ITERABLE_ARRAY = listOf(0, 1)
    }
}
