package com.lounah.yarealty.device.location

import android.location.LocationListener

interface LocationInfo {
    fun registerLocationListener(listener: LocationListener)
    fun removeLocationListener(listener: LocationListener)
    fun isGpsProviderEnabled(): Boolean
    fun isNetworkProviderEnabled(): Boolean
}