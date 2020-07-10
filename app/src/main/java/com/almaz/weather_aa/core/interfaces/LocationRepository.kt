package com.almaz.weather_aa.core.interfaces

import com.almaz.weather_aa.core.model.CurrentWeather
import io.reactivex.Single

interface LocationRepository {
    fun getLocations(): Single<List<CurrentWeather>>
}
