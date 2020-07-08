package com.almaz.weather_aa.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.almaz.weather_aa.ui.main.MainViewModel
import com.almaz.weather_aa.ui.weather.WeatherViewModel
import com.almaz.weather_aa.utils.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

fun viewModelModule() = Kodein.Module(name = "viewModelModule") {
    bind<ViewModelProvider.Factory>() with singleton {
        ViewModelFactory(
            kodein.direct
        )
    }

    bind<ViewModel>(tag = MainViewModel::class.java.simpleName) with provider {
        MainViewModel()
    }
    bind<ViewModel>(tag = WeatherViewModel::class.java.simpleName) with provider {
        WeatherViewModel(weatherInteractor = instance())
    }
}
