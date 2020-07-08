package com.almaz.weather_aa.di

import androidx.lifecycle.ViewModelProvider
import com.almaz.weather_aa.ui.weather.WeatherViewModel
import com.almaz.weather_aa.utils.ViewModelFactory
import com.almaz.weather_aa.utils.bindViewModel
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import org.kodein.di.generic.instance

fun viewModelModule() = Kodein.Module(name = "viewModelModule") {
    bind<ViewModelProvider.Factory>() with singleton {
        ViewModelFactory(
            kodein.direct
        )
    }
    bindViewModel<WeatherViewModel>() with provider {
        WeatherViewModel(instance())
    }
}
