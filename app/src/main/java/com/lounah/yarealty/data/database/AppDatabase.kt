package com.lounah.yarealty.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.lounah.yarealty.data.database.dao.CallsDao
import com.lounah.yarealty.data.database.dao.DislikedOfferDao
import com.lounah.yarealty.data.database.dao.FavoriteOfferDao
import com.lounah.yarealty.data.entity.Call
import com.lounah.yarealty.data.entity.DislikedOffer
import com.lounah.yarealty.data.entity.Offer

@Database(
        entities = [
            Offer::class,
            DislikedOffer::class,
            Call::class
        ],
        version = 2,
        exportSchema = false)
@TypeConverters(StringListConverter::class, IntegerListConverter::class, TimeStampConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun offerDao(): FavoriteOfferDao
    abstract fun dislikedOfferDao(): DislikedOfferDao
    abstract fun callsDao(): CallsDao
}