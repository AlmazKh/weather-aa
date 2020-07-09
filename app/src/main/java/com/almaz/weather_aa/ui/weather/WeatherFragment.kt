package com.almaz.weather_aa.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnScrollChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.almaz.weather_aa.BuildConfig
import com.almaz.weather_aa.R
import com.almaz.weather_aa.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_weather.*
import org.kodein.di.generic.instance


class WeatherFragment : BaseFragment() {

    private val viewModeFactory: ViewModelProvider.Factory by instance()
    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this, this.viewModeFactory)
            .get(WeatherViewModel::class.java)
    }
    private lateinit var hourlyWeatherAdapter: HourlyWeatherAdapter


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
                        container_hourly_weather.visibility = View.GONE
                    } else {
                        container_hourly_weather.visibility = View.VISIBLE
                    }
                }
            }

        viewModel.getHourlyWeather(35.7721, -78.63861, BuildConfig.API_KEY)

        observeHourlyWeatherLiveData()
    }

    private fun initAdapter() {
        hourlyWeatherAdapter = HourlyWeatherAdapter()
        rv_daily_details.adapter = hourlyWeatherAdapter
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
}
