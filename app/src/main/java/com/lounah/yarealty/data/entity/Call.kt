package com.lounah.yarealty.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.Date

@Entity(tableName = "call_history")
data class Call(val offerId: String,
                val offerImageURL: String,
                val phoneNumber: String,
                val info: String,
                @PrimaryKey val date: Date)