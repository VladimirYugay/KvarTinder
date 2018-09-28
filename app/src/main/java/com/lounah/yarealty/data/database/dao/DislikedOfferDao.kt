package com.lounah.yarealty.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.lounah.yarealty.data.entity.DislikedOffer

@Dao
interface DislikedOfferDao : BaseDao<DislikedOffer> {
    @Query("SELECT * FROM disliked_offer")
    fun getDislikedOffers(): List<DislikedOffer>

    @Query("DELETE FROM disliked_offer")
    fun clearData()
}