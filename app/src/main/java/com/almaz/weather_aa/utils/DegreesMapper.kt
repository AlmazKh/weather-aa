package com.almaz.weather_aa.utils

class DegreesMapper {
    companion object {

        fun mapDegreesToFormWithMarkers(temp: Double): String {
            return if (temp > 0) {
                "+${temp.toInt()}°"
            } else {
                "${temp.toInt()}°"
            }
        }
    }
}
