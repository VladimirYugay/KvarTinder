package com.lounah.yarealty.utils

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulersProvider {
    fun io() = Schedulers.io()
    fun ui() = AndroidSchedulers.mainThread()
    fun single() = Schedulers.single()
    fun computation() = Schedulers.computation()
}

class AppSchedulersProvider : SchedulersProvider