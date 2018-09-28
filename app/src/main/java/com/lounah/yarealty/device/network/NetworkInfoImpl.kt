package com.lounah.yarealty.device.network

import android.net.wifi.WifiManager
import com.lounah.yarealty.utils.Utils
import java.net.NetworkInterface
import javax.inject.Inject

class NetworkInfoImpl @Inject constructor(private val wifiManager: WifiManager) : NetworkInfo {

    override fun getDeviceIP(): String {
        return if (wifiManager.isWifiEnabled) {
            Utils.formatIntToIpAddress(wifiManager.connectionInfo.ipAddress)
        } else {
            "0.0.0.0"
        }
    }
}