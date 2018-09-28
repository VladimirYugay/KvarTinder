package com.lounah.yarealty.domain.interactor

import com.lounah.yarealty.data.entity.Call
import com.lounah.yarealty.domain.repository.CallHistoryRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class CallHistoryInteractor @Inject constructor(private val callHistoryRepository: CallHistoryRepository) {
    fun getCallHistory(): Flowable<List<Call>> = callHistoryRepository.getCallHistory()

    fun addNewCall(call: Call): Completable = callHistoryRepository.addNewCall(call)
}