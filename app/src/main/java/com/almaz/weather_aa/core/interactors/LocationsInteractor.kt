package com.almaz.weather_aa.core.interactors

import com.almaz.weather_aa.core.interfaces.LocationRepository
import com.almaz.weather_aa.core.model.CurrentWeather
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocationsInteractor(private val locationsRepository: LocationRepository) {

    fun getLocations(): Single<List<CurrentWeather>> =
        locationsRepository.getLocations()
            .subscribeOn(Schedulers.io())
}
