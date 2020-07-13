package com.almaz.weather_aa.ui.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.almaz.itis_booking.ui.base.BaseViewModel
import com.almaz.weather_aa.core.interactors.WeatherInteractor
import com.almaz.weather_aa.core.model.DailyWeather
import com.almaz.weather_aa.core.model.DailyWeatherResponse
import com.almaz.weather_aa.core.model.HourlyWeather
import com.almaz.weather_aa.core.model.SavedLocation
import com.almaz.weather_aa.utils.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class WeatherViewModel(
    private val weatherInteractor: WeatherInteractor
) : BaseViewModel() {
    val hourlyWeatherLiveData = MutableLiveData<Response<List<HourlyWeather>>>()
    val dailyWeatherLiveData = MutableLiveData<Response<List<DailyWeather>>>()
    val shouldCheckGps = MutableLiveData<Boolean>()

    private fun getHourlyWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ) {
        disposables.add(
            weatherInteractor.getHourlyWeather(lat, lon, apiKey)
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    showLoadingLiveData.value = false
                }
                .subscribe({
                    hourlyWeatherLiveData.value = Response.success(it)
                }, { error ->
                    hourlyWeatherLiveData.value = Response.error(error)
                    error.printStackTrace()
                })
        )
    }

    private fun getDailyWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ) {
        weatherInteractor.getDailyWeather(lat, lon, apiKey)
            .doOnSubscribe {
                showLoadingLiveData.value = true
            }
            .doAfterTerminate {
                showLoadingLiveData.value = false
            }
            .subscribeBy(onSuccess = {
                dailyWeatherLiveData.postValue(Response.success(it))
            }, onError = {
                dailyWeatherLiveData.postValue(Response.error(it))
                it.printStackTrace()
            })
            .addTo(disposables)
    }

    fun getWeather(apiKey: String) {
        weatherInteractor.getWeather()
            .doOnSubscribe {
                showLoadingLiveData.value = true
            }
            .doAfterTerminate {
                showLoadingLiveData.value = false
            }
            .subscribeBy(onSuccess = {
                if (it.isNotEmpty()) {
                    val loc = it[0]
                    shouldCheckGps.postValue(false)
                    getHourlyWeather(loc.lat.toDouble(), loc.lon.toDouble(), apiKey)
                    getDailyWeather(loc.lat.toDouble(), loc.lon.toDouble(), apiKey)
                } else {
                    shouldCheckGps.postValue(true)
                }
            }, onError = {
                it.printStackTrace()
            }).addTo(disposables)
    }

    fun getLocation(apiKey: String) {
        weatherInteractor.getGeoPosition()
            .doOnSubscribe {
                showLoadingLiveData.value = true
            }
            .doAfterTerminate {
                showLoadingLiveData.value = false
            }
            .subscribeBy(onSuccess = {
                getHourlyWeather(it.latitude, it.longitude, apiKey)
                getDailyWeather(it.latitude, it.longitude, apiKey)
                saveLocation(it.latitude.toString(), it.longitude.toString())
            }, onError = {
                it.printStackTrace()
            }).addTo(disposables)
    }

    private fun saveLocation(lat: String, lon: String) {
        weatherInteractor.saveLocation(lat, lon)
            .doOnSubscribe {
                Log.d("saveLocation", "doOnSubscribe")
            }
            .doAfterTerminate {
                Log.d("saveLocation", "doAfterTerminate")
            }
            .subscribeBy({
                Log.d("saveLocation", "" + it.localizedMessage)
            }, {
                Log.d("saveLocation", "subscribeBy")
            }).addTo(disposables)

    }
}
