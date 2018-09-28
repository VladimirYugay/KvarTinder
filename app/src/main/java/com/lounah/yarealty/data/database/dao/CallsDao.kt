package com.lounah.yarealty.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.lounah.yarealty.data.entity.Call
import io.reactivex.Flowable


@Dao
interface CallsDao : BaseDao<Call> {
    @Query("SELECT * FROM call_history")
    fun getCallHistory(): Flowable<List<Call>>

    @Query("DELETE FROM call_history")
    fun clearData()
}