package com.almaz.weather_aa.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.almaz.weather_aa.BuildConfig
import com.almaz.weather_aa.R
import com.almaz.weather_aa.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_weather.*
import org.kodein.di.generic.instance

class WeatherFragment : BaseFragment() {

    private val viewModelFactory: ViewModelProvider.Factory by instance()
    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this, this.viewModelFactory)
            .get(WeatherViewModel::class.java)
    }
    private lateinit var hourlyWeatherAdapter: HourlyWeatherAdapter
    private lateinit var dailyWeatherAdapter: DailyWeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        //TODO get lat lon
        viewModel.getHourlyWeather(35.7721, -78.63861, BuildConfig.API_KEY)
        viewModel.getDailyWeather(35.7721, -78.63861, BuildConfig.API_KEY)

        observeDailyWeather()
        observeHourlyWeatherLiveData()
    }

    private fun initAdapter() {
        rv_daily_details.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        rv_daily_weather.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        hourlyWeatherAdapter = HourlyWeatherAdapter()
        rv_daily_details.adapter = hourlyWeatherAdapter
        dailyWeatherAdapter = DailyWeatherAdapter()
        rv_daily_weather.adapter = dailyWeatherAdapter
        rv_daily_weather.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
    }

    private fun observeHourlyWeatherLiveData() =
        viewModel.hourlyWeatherLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    hourlyWeatherAdapter.submitList(it.data)
                    rv_daily_details.adapter = hourlyWeatherAdapter
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })

    private fun observeDailyWeather() =
        viewModel.dailyWeatherLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { response ->
                if (response.data != null) {
                    dailyWeatherAdapter.submitList(response.data)
                }
                if (response.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })
}
