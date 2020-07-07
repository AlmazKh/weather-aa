package com.almaz.weather_aa.core.model


import com.google.gson.annotations.SerializedName

data class WeatherDetails(
    @SerializedName("code")
    val code: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)