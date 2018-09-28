package com.lounah.yarealty.device.location

import android.location.LocationListener
import android.location.LocationManager
import android.support.v4.util.TimeUtils
import javax.inject.Inject

class LocationInfoImpl @Inject constructor(private val locationManager: LocationManager?) : LocationInfo {
    override fun registerLocationListener(listener: LocationListener) {
        locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0F, listener)

        if (locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) != null) {
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10_000L, 0F, listener)
        }
    }

    override fun removeLocationListener(listener: LocationListener) {
        locationManager?.removeUpdates(listener)
    }

    override fun isGpsProviderEnabled(): Boolean {
        return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
    }

    override fun isNetworkProviderEnabled(): Boolean {
        return locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ?: false
    }
}