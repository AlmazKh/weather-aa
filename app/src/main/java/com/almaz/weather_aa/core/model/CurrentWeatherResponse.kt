package com.almaz.weather_aa.core.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("count")
    val count: String,
    @SerializedName("data")
    val data: List<CurrentWeather>
)