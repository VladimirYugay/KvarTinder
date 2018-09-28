package com.lounah.yarealty

import android.app.Activity
import android.app.Application
import com.lounah.yarealty.di.common.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig



class YaRealtyApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        val configBuilder = YandexMetricaConfig.newConfigBuilder(BuildConfig.YANDEX_METRIKA_API_KEY)
        YandexMetrica.activate(applicationContext, configBuilder.build())
        YandexMetrica.enableActivityAutoTracking(this)
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}