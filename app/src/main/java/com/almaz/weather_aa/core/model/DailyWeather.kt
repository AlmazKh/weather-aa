package com.almaz.weather_aa.core.model


import com.google.gson.annotations.SerializedName

data class DailyWeather(
    @SerializedName("app_max_temp")
    val appMaxTemp: Int,
    @SerializedName("app_min_temp")
    val appMinTemp: Int,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("datetime")
    val datetime: String,
    @SerializedName("dewpt")
    val dewpt: Int,
    @SerializedName("max_dhi")
    val maxDhi: String,
    @SerializedName("max_temp")
    val maxTemp: Double,
    @SerializedName("min_temp")
    val minTemp: Double,
    @SerializedName("moon_phase")
    val moonPhase: Double,
    @SerializedName("moonrise_ts")
    val moonriseTs: Int,
    @SerializedName("moonset_ts")
    val moonsetTs: Int,
    @SerializedName("pod")
    val pod: String,
    @SerializedName("pop")
    val pop: Int,
    @SerializedName("precip")
    val precip: Double,
    @SerializedName("pres")
    val pres: Int,
    @SerializedName("rh")
    val rh: Int,
    @SerializedName("slp")
    val slp: Double,
    @SerializedName("snow")
    val snow: Double,
    @SerializedName("snow_depth")
    val snowDepth: Int,
    @SerializedName("sunrise_ts")
    val sunriseTs: Int,
    @SerializedName("sunset_ts")
    val sunsetTs: Int,
    @SerializedName("temp")
    val temp: Int,
    @SerializedName("timestamp_local")
    val timestampLocal: String,
    @SerializedName("timestamp_utc")
    val timestampUtc: String,
    @SerializedName("ts")
    val ts: String,
    @SerializedName("uv")
    val uv: Double,
    @SerializedName("vis")
    val vis: Int,
    @SerializedName("weather")
    val weather: WeatherDetails,
    @SerializedName("wind_cdir")
    val windCdir: String,
    @SerializedName("wind_cdir_full")
    val windCdirFull: String,
    @SerializedName("wind_dir")
    val windDir: Int,
    @SerializedName("wind_spd")
    val windSpd: Double
)