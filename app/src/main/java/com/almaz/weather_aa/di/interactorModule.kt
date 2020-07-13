package com.almaz.weather_aa.di

import android.content.Context
import com.almaz.weather_aa.core.interactors.LocationsInteractor
import com.almaz.weather_aa.core.interactors.WeatherInteractor
import com.google.android.gms.location.FusedLocationProviderClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

fun interactorModule() = Kodein.Module("interactorModule") {
    bind<FusedLocationProviderClient>() with singleton {
        FusedLocationProviderClient(instance<Context>())
    }
    bind<WeatherInteractor>() with singleton {
        WeatherInteractor(instance(), instance())
    }
    bind<LocationsInteractor>() with singleton {
        LocationsInteractor(instance())
    }
}
