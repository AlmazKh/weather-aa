package com.almaz.weather_aa.utils

import com.almaz.weather_aa.R
import org.joda.time.DateTime
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class DateTimeMapper {

    companion object {
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
                    dayOfWeek.append("Сегодня")
                }
                DateTime.now().plusDays(1).dayOfMonth -> {
                    dayOfWeek.append("Завтра")
                }
                else -> {
                    dayOfWeek.append(getDateRus(year, month - 1, day, "EEEE"))
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
            dayOfMonth.append(getDateRus(year, month - 1, day, "MMMM"))
            return dayOfMonth.toString()
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