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
import com.almaz.weather_aa.core.model.HourlyWeather
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
        rv_daily_details.apply {
            layoutManager = LinearLayoutManager(rootView.context, LinearLayoutManager.HORIZONTAL, false)
        }
        initAdapter()

//        val dw = container_hourly_weather.layoutParams as CoordinatorLayout.LayoutParams
//        dw.behavior = HourlyWeatherBehavior()
        // TODO: fix with custom behavior
        rv_daily_weather.viewTreeObserver
            .addOnScrollChangedListener {
                if (rv_daily_weather != null) {
                    if (rv_daily_weather.getChildAt(0)
                            .bottom <= rv_daily_weather.height + rv_daily_weather.scrollY
                    ) {
                        container_hourly_weather.visibility = View.VISIBLE
                    } else {
                        container_hourly_weather.visibility = View.GONE
                    }
                }
            }

        //TODO get lat lon
        viewModel.getHourlyWeather(55.830433, 49.066082, BuildConfig.API_KEY)
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
                    setUpExtraWeatherOptions(it.data)
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

    private fun setUpExtraWeatherOptions(data: List<HourlyWeather>) {
        tv_wind.text = "${(data[0].windSpd * 3.6).toInt()} km/h, ${data[0].windCdir}"
        tv_pressure.text = "${data[0].pres.toInt()} hPa"
        tv_humidity.text = "${data[0].rh.toInt()} %"
    }
}
