package com.almaz.weather_aa.data

import com.almaz.weather_aa.core.model.CurrentWeatherResponse
import com.almaz.weather_aa.core.model.DailyWeatherResponse
import com.almaz.weather_aa.core.model.HourlyWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("current")
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("key") apiKey: String
    ): Single<CurrentWeatherResponse>

    @GET("forecast/daily")
    fun getDailyWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("key") apiKey: String
    ): Single<DailyWeatherResponse>

    @GET("forecast/hourly")
    fun getHourlyWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("key") apiKey: String
    ): Single<HourlyWeatherResponse>
}
