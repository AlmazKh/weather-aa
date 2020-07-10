package com.almaz.weather_aa.ui.locations

import androidx.lifecycle.MutableLiveData
import com.almaz.itis_booking.ui.base.BaseViewModel
import com.almaz.weather_aa.core.interactors.LocationsInteractor
import com.almaz.weather_aa.core.model.CurrentWeather
import com.almaz.weather_aa.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers

class LocationsViewModel(private val locationsInteractor: LocationsInteractor) : BaseViewModel() {

    val locationsWeatherLiveData = MutableLiveData<Response<List<CurrentWeather>>>()

    fun getLocations() {
        disposables.addAll(
            locationsInteractor.getLocations()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    locationsWeatherLiveData.value = Response.success(it)
                }, { error ->
                    locationsWeatherLiveData.value = Response.error(error)
                    error.printStackTrace()
                })
        )
    }
}
