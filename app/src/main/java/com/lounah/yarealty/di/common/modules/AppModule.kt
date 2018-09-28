package com.lounah.yarealty.di.common.modules

import android.app.Application
import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import com.lounah.yarealty.device.location.LocationInfo
import com.lounah.yarealty.device.location.LocationInfoImpl
import com.lounah.yarealty.device.network.NetworkInfo
import com.lounah.yarealty.device.network.NetworkInfoImpl
import com.lounah.yarealty.utils.AppSchedulersProvider
import com.lounah.yarealty.utils.SchedulersProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Provides
    @Singleton
    fun provideAppScheduler(): SchedulersProvider = AppSchedulersProvider()

    @Provides
    @Singleton
    fun provideLocationInfo(context: Application): LocationInfo {
        val m = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationInfoImpl(m)
    }

    @Provides
    @Singleton
    fun provideNetworkInfo(context: Application): NetworkInfo {
        val m = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return NetworkInfoImpl(m)
    }
}
