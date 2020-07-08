package com.almaz.weather_aa.di

import com.almaz.weather_aa.data.WeatherService
import com.google.gson.GsonBuilder
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.kodein.di.generic.with
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun netModule() = Kodein.Module("netModule") {
    constant("baseUrl") with "http://api.weatherbit.io/v2.0/"
    bind<Retrofit>() with singleton { provideRetrofit(instance("baseUrl")) }
    bind<WeatherService>() with singleton { instance<Retrofit>().create(WeatherService::class.java) }
}

private fun provideRetrofit(
    baseUrl: String
): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()
