package com.almaz.weather_aa.ui.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.almaz.weather_aa.R
import com.almaz.weather_aa.core.model.CurrentWeather
import com.almaz.weather_aa.utils.DegreesMapper
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_locations.view.*

class LocationsAdapter : ListAdapter<CurrentWeather, LocationsAdapter.LocationsViewHolder>(
    LocationsDiffCallback()
) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LocationsViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return LocationsViewHolder(inflater.inflate(R.layout.item_locations, p0, false))
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class LocationsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(locationWeather: CurrentWeather) {
            itemView.tv_city_name.text = locationWeather.cityName
            // TODO: replace with country name
            itemView.tv_country.text = locationWeather.countryCode
            itemView.tv_location_degrees.text =
                DegreesMapper.mapDegreesToFormWithMarkers(locationWeather.temp)
            itemView.tv_extra_weather_options.text =
                "Humidity ${locationWeather.rh}% | " +
                        "${locationWeather.windCdirFull.capitalize()} | " +
                        "${(locationWeather.windSpeed * 3.6).toInt()} km/h"
            itemView.tv_location_extra_degrees.text =
                DegreesMapper.mapDegreesToFormWithMarkers(locationWeather.appTemp)
            itemView.iv_location_weather_state.setImageDrawable(
                containerView.resources.getDrawable(
                    R.drawable.ic_sunny,
                    null
                )
            )
        }
    }

    class LocationsDiffCallback : DiffUtil.ItemCallback<CurrentWeather>() {
        override fun areItemsTheSame(oldItem: CurrentWeather, newItem: CurrentWeather): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: CurrentWeather, newItem: CurrentWeather): Boolean =
            oldItem == newItem
    }
}
