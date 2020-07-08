package com.almaz.weather_aa.data

import com.almaz.weather_aa.core.model.CurrentWeatherResponse
import com.almaz.weather_aa.core.model.DailyWeatherResponse
import com.almaz.weather_aa.core.model.HourlyWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("current?lat={lat}&lon={lon}")
    fun getCurrentWeather(
        @Path("lat") lat: Double,
        @Path("lon") lon: Double,
        @Query("key") apiKey: String
    ): Single<CurrentWeatherResponse>

    @GET("forecast/daily?lat={lat}&lon={lon}")
    fun getDailyWeather(
        @Path("lat") lat: Double,
        @Path("lon") lon: Double,
        @Query("key") apiKey: String
    ): Single<DailyWeatherResponse>

    @GET("forecast/hourly")
    fun getHourlyWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("key") apiKey: String
    ): Single<HourlyWeatherResponse>
}
