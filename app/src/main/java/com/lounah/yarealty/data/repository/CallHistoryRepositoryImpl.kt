package com.lounah.yarealty.data.repository

import com.lounah.yarealty.data.database.dao.CallsDao
import com.lounah.yarealty.data.entity.Call
import com.lounah.yarealty.domain.repository.CallHistoryRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class CallHistoryRepositoryImpl @Inject constructor(private val callsDao: CallsDao) : CallHistoryRepository {

    override fun getCallHistory(): Flowable<List<Call>> = callsDao.getCallHistory()

    override fun addNewCall(call: Call) = Completable.fromAction { callsDao.insert(call) }
}