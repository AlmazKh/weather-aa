package com.almaz.weather_aa.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

class GPSUtils(private val context: Context) {
    private val mSettingsClient: SettingsClient
    private val mLocationSettingsRequest: LocationSettingsRequest
    private val locationManager: LocationManager
    private val locationRequest: LocationRequest
    private val settingsError = "Необходимо определить местоположение в настройках телефона"
    private val pendingIntentError = "PendingIntent не может обработать запрос"

    fun checkIsGpsOn(onGpsListener: OnGpsListener?) {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            onGpsListener?.gpsStatus(true)
        } else {
            mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(
                    (context as Activity)
                ) { //  GPS is already enable, callback GPS status through listener
                    onGpsListener?.gpsStatus(true)
                }
                .addOnFailureListener(
                    context
                ) { e ->
                    when ((e as ApiException).statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                            // Show the dialog
                            val rae = e as ResolvableApiException
                            rae.startResolutionForResult(
                                context,
                                AppConstants.GPS_REQUEST
                            )
                        } catch (sie: SendIntentException) {
                            Log.i(
                                ContentValues.TAG,
                                pendingIntentError
                            )
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            val errorMessage = settingsError
                            Log.e(ContentValues.TAG, errorMessage)
                            Toast.makeText(
                                context,
                                errorMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
        }
    }

    interface OnGpsListener {
        fun gpsStatus(isGPSEnable: Boolean)
    }

    init {
        locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mSettingsClient = LocationServices.getSettingsClient(context)
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10 * 1000.toLong()
        locationRequest.fastestInterval = 2 * 1000.toLong()
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        mLocationSettingsRequest = builder.build()
        builder.setAlwaysShow(true)
    }
}
