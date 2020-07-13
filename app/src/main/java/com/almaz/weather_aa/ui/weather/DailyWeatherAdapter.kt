package com.almaz.weather_aa.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.almaz.weather_aa.R
import com.almaz.weather_aa.core.model.DailyWeather
import com.almaz.weather_aa.utils.DateTimeMapper
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_weather_daily.view.*

class DailyWeatherAdapter :
    ListAdapter<DailyWeather, DailyWeatherAdapter.DailyWeatherHolder>(DailyWeatherDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): DailyWeatherHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_weather_daily, parent, false)
        return DailyWeatherHolder(view)
    }

    override fun onBindViewHolder(holder: DailyWeatherHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DailyWeatherDiffCallback : DiffUtil.ItemCallback<DailyWeather>() {
        override fun areItemsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean =
            oldItem.timestampUtc == newItem.timestampUtc

        override fun areContentsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean =
            oldItem == newItem
    }

    class DailyWeatherHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(weatherData: DailyWeather) {
            containerView.tv_day_by_week.text = DateTimeMapper.mapToDayOfWeek(weatherData.datetime)
            containerView.tv_day_by_month.text =
                DateTimeMapper.mapToDayOfMonth(weatherData.datetime)
            containerView.tv_max_temp.text = weatherData.appMaxTemp.toString()
            containerView.tv_min_temp.text = weatherData.appMinTemp.toString()

            //TODO разные иконки
            containerView.iv_weather_icon.setImageResource(R.drawable.ic_sunny)
        }
    }
}
