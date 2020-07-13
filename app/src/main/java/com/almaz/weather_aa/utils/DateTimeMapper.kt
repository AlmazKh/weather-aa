package com.almaz.weather_aa.utils

import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class DateTimeMapper {

    companion object {
        private val today = "Сегодня"
        private val tomorrow = "Завтра"
        private val pattern_month = "MMMM"
        private val pattern_week = "EEEE"

        private val locale = Locale("ru")
        fun mapToDayOfWeek(date: String): String {
            val dateParsed = parseDate(date)
            val year = getYear(dateParsed)
            val month = getMonth(dateParsed)
            val day = getDay(dateParsed)
            val dateTime = DateTime(year, month, day, 0, 0)
            val dayOfWeek = StringBuilder()
            when (dateTime.dayOfMonth) {
                DateTime.now().dayOfMonth -> {
                    dayOfWeek.append(today)
                }
                DateTime.now().plusDays(1).dayOfMonth -> {
                    dayOfWeek.append(tomorrow)
                }
                else -> {
                    dayOfWeek.append(getDateRus(year, month - 1, day, pattern_week))
                }
            }
            return dayOfWeek.toString()
        }

        fun mapToDayOfMonth(date: String): String {
            val dateParsed = date.split(Pattern.compile("-"), 0)
            val year = getYear(dateParsed)
            val month = getMonth(dateParsed)
            val day = getDay(dateParsed)

            val dayOfMonth = StringBuilder()
            dayOfMonth.append(day)
            dayOfMonth.append(" ")
            dayOfMonth.append(getDateRus(year, month - 1, day, pattern_month))
            return dayOfMonth.toString()
        }

        fun getDegreesInCorrectForm(temp: Double): String {
            return if (temp > 0) {
                "+${temp.toInt()}°"
            } else {
                "${temp.toInt()}°"
            }
        }

        private fun getDateRus(year: Int, month: Int, day: Int, pattern: String): String {
            val calendar = Calendar.getInstance(locale)
            calendar.set(year, month, day)
            val dateRus = SimpleDateFormat(pattern, locale).format(
                calendar.time
            )
            return dateRus.substring(0, 1).toUpperCase(locale) + dateRus.substring(1)
        }

        private fun parseDate(date: String) = date.split(Pattern.compile("-"), 0)

        private fun getYear(dateParsed: List<String>) = dateParsed[0].toInt()

        private fun getMonth(dateParsed: List<String>) = dateParsed[1].toInt()

        private fun getDay(dateParsed: List<String>) = dateParsed[2].toInt()
    }
}