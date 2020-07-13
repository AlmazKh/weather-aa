package com.almaz.weather_aa.di

import com.almaz.weather_aa.core.WeatherDAO
import com.almaz.weather_aa.data.AppDatabase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

fun databaseModule() = Kodein.Module("databaseModule") {
    bind<WeatherDAO>() with singleton { AppDatabase.getInstance(instance()).weatherDao() }
}