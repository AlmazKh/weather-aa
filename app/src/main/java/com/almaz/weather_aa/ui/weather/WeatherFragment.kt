package com.almaz.weather_aa.ui.weather

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.almaz.weather_aa.BuildConfig
import com.almaz.weather_aa.R
import com.almaz.weather_aa.core.model.HourlyWeather
import com.almaz.weather_aa.ui.base.BaseFragment
import com.almaz.weather_aa.ui.weather.states.RainDropView
import com.almaz.weather_aa.utils.DegreesMapper
import com.almaz.weather_aa.utils.GPSUtils
import com.almaz.weather_aa.utils.StatusBarState
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
        rv_daily_weather.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        initAdapter()

        checkLocationPermissions()

        btn_locations.setOnClickListener {
            rootActivity.navController.navigate(
                R.id.action_weather_fragment_to_locations_fragment
            )
        }

        observeLoading()
        observeCurrentWeatherLiveData()
        observeDailyWeather()
        observeHourlyWeatherLiveData()
    }

    override fun onStart() {
        super.onStart()
        setUpStatusBar(StatusBarState.TRANSPARENT)
        setSunAnimating()
        animateDrop(drop1)
        animateDrop(drop2)
        animateDrop(drop3)
        animateDrop(drop4)
        animateDrop(drop5)
        animateDrop(drop6)
    }

    private fun setSunAnimating() {
        val valueAnimator = ValueAnimator.ofInt(1, 360)
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = 10000
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.addUpdateListener { animation ->
            Log.d("Anim", animation.animatedValue.toString() + "")
            animated_sun.setValue(animation.animatedValue as Int)
        }
        valueAnimator.start()
    }

    private fun animateDrop(drop: RainDropView) {
        ObjectAnimator.ofFloat(drop, "translationY", 300f).apply {
            duration = 1000
            interpolator = LinearInterpolator()
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            start()
        }
    }

    private fun initAdapter() {
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
        viewModel.getWeather(BuildConfig.API_KEY)
        observeShouldCheckGps()
    }

    private fun onLocationPermissionsDenied() {
        showSnackbar(getString(R.string.location_prompt_denied))
    }

    private fun checkIsGpsOn() {
        GPSUtils(rootActivity).checkIsGpsOn(object : GPSUtils.OnGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                if (isGPSEnable) rootActivity.mainViewModel.gpsState.postValue(true)
            }
        })
    }

    private fun observeCurrentWeatherLiveData() =
        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    tv_city_name.text = it.data.data[0].cityName
                    tv_degrees.text =
                        DegreesMapper.mapDegreesToFormWithMarkers(it.data.data[0].temp)
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })

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

    private fun observeShouldCheckGps() =
        viewModel.shouldCheckGps.observe(viewLifecycleOwner, Observer { shouldCheckGps ->
            if (shouldCheckGps) {
                checkIsGpsOn()
                observeGps()
            }
        })

    private fun setUpExtraWeatherOptions(data: List<HourlyWeather>) {
        if (data.isNotEmpty()) {
            tv_wind.text = "${(data[0].windSpd * 3.6).toInt()} km/h, ${data[0].windCdir}"
            tv_pressure.text = "${data[0].pres.toInt()} hPa"
            tv_humidity.text = "${data[0].rh.toInt()} %"
        }
    }

    companion object {
        private const val LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
        private const val PERMISSION_REQUEST_CODE = 324
    }
}
