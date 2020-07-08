package com.almaz.weather_aa.di

import com.almaz.weather_aa.core.interactors.WeatherInteractor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

fun interactorModule() = Kodein.Module("interactorModule") {
    bind<WeatherInteractor>() with singleton {
        WeatherInteractor(instance())
    }
}