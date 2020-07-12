package com.almaz.weather_aa.ui.weather

//import com.almaz.weather_aa.utils.GpsUtils
//import com.google.android.gms.location.*

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.almaz.weather_aa.BuildConfig
import com.almaz.weather_aa.R
import com.almaz.weather_aa.ui.base.BaseFragment
import com.almaz.weather_aa.utils.GPSUtils
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
            layoutManager =
                LinearLayoutManager(rootView.context, LinearLayoutManager.HORIZONTAL, false)
        }
        initAdapter()

        checkLocationPermissions()

//        val dw = container_hourly_weather.layoutParams as CoordinatorLayout.LayoutParams
//        dw.behavior = HourlyWeatherBehavior()
        // TODO: fix with custom behavior
//        rv_daily_weather.viewTreeObserver
//            .addOnScrollChangedListener {
//                if (rv_daily_weather != null) {
//                    if (rv_daily_weather.getChildAt(0)
//                            .bottom <= rv_daily_weather.height + rv_daily_weather.scrollY
//                    ) {
//                        container_hourly_weather.visibility = View.GONE
//                    } else {
//                        container_hourly_weather.visibility = View.VISIBLE
//                    }
//                }
//            }

        observeGps()
        observeLoading()
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
        rv_daily_weather.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && permissions[0] == LOCATION_PERMISSION
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            onLocationPermissionsGranted()
        } else {
            onLocationPermissionsDenied()
        }
    }

    private fun checkLocationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context?.let { context ->
                if (ContextCompat.checkSelfPermission(context, LOCATION_PERMISSION) !=
                    PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(
                        arrayOf(LOCATION_PERMISSION),
                        PERMISSION_REQUEST_CODE
                    )
                } else {
                    onLocationPermissionsGranted()
                }
            }
        } else {
            onLocationPermissionsGranted()
        }
    }

    private fun onLocationPermissionsGranted() {
        GPSUtils(rootActivity).checkIsGpsOn(object : GPSUtils.OnGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                if (isGPSEnable) rootActivity.mainViewModel.gpsState.postValue(true)
            }
        })
    }

    private fun onLocationPermissionsDenied() {
        showSnackbar(getString(R.string.location_prompt_denied))
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

    private fun observeLoading() =
        viewModel.showLoadingLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { isLoading ->
                if (isLoading) showLoading()
                else hideLoading()
            }
        })

    private fun observeGps() =
        rootActivity.mainViewModel.gpsState.observe(viewLifecycleOwner, Observer { gpsEnable ->
            if (gpsEnable) viewModel.getLocation(BuildConfig.API_KEY)
            else {
                hideLoading()
                showSnackbar(getString(R.string.location_prompt_denied))
            }
        })

    companion object {
        private const val LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
        private const val PERMISSION_REQUEST_CODE = 324
    }
}
