package com.almaz.weather_aa.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.almaz.weather_aa.R
import com.almaz.weather_aa.ui.base.BaseFragment
import org.kodein.di.generic.instance

class WeatherFragment : BaseFragment() {

    private val viewModeFactory: ViewModelProvider.Factory by instance()
    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this, this.viewModeFactory)
            .get(WeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }
}
