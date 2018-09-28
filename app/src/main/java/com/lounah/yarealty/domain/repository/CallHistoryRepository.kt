package com.lounah.yarealty.domain.repository

import com.lounah.yarealty.data.entity.Call
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface CallHistoryRepository {
    fun getCallHistory(): Flowable<List<Call>>
    fun addNewCall(call: Call): Completable
}