package com.lounah.yarealty.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Query
import com.lounah.yarealty.data.entity.Offer
import io.reactivex.Flowable

@Dao
interface FavoriteOfferDao : BaseDao<Offer> {
    @Query("SELECT * FROM offer")
    fun getOffers(): Flowable<List<Offer>>

    @Query("SELECT * FROM offer where offerId = :offerId")
    fun getOffer(offerId: String): Flowable<Offer>

    @Query("SELECT offerId from offer")
    fun getFavoriteOfferIds(): List<String>

    @Query("DELETE FROM offer WHERE offerId=:offerId")
    fun deleteById(offerId: String)

    @Query("DELETE FROM offer")
    fun clearData()
}