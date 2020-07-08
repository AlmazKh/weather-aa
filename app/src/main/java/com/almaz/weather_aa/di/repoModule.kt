package com.almaz.weather_aa.di

import com.almaz.weather_aa.core.WeatherRepository
import com.almaz.weather_aa.data.WeatherRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

fun repoModule() = Kodein.Module("repoModule") {
    bind<WeatherRepository>() with singleton {
        WeatherRepositoryImpl(weatherService = instance())
    }
}