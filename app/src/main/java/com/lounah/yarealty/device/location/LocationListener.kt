package com.lounah.yarealty.device.location

import android.location.LocationListener
import android.os.Bundle

abstract class LocationListener : LocationListener {

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }
}