package com.lounah.yarealty.di.common

import com.lounah.yarealty.YaRealtyApplication

object AppInjector {

    fun init(app: YaRealtyApplication) {
        DaggerAppComponent
                .builder()
                .application(app)
                .appContext(app)
                .build()
                .inject(app)
    }
}