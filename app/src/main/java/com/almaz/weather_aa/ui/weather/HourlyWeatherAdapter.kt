package com.almaz.weather_aa.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.almaz.weather_aa.R
import com.almaz.weather_aa.core.model.HourlyWeather
import com.almaz.weather_aa.utils.DateTimeMapper
import com.almaz.weather_aa.utils.DegreesMapper
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_hourly_weather.view.*
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class HourlyWeatherAdapter :
    ListAdapter<HourlyWeather, HourlyWeatherAdapter.HourlyWeatherViewHolder>(
        HourlyWeatherDiffCallback()
    ) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HourlyWeatherViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return HourlyWeatherViewHolder(inflater.inflate(R.layout.item_hourly_weather, p0, false))
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HourlyWeatherViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(weather: HourlyWeather) {
            if (DateTime.now(DateTimeZone.forOffsetHours(3)).hourOfDay == DateTime(weather.timestampLocal).hourOfDay) {
                // TODO: this condition always false because no data about current weather (need one more request to API)
                itemView.tv_time.text = "Сейчас"
            } else {
                itemView.tv_time.text = "${DateTime(weather.timestampLocal).hourOfDay}:00"
            }
            itemView.iv_weather_state.setImageDrawable(
                containerView.resources.getDrawable(
                    R.drawable.mdi_weather_cloudy,
                    null
                )
            )
            itemView.tv_time_degrees.text = DegreesMapper.mapDegreesToFormWithMarkers(weather.temp)
        }
    }

    class HourlyWeatherDiffCallback : DiffUtil.ItemCallback<HourlyWeather>() {
        override fun areItemsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean =
            oldItem == newItem
    }
}
