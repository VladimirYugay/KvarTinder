package com.lounah.yarealty.data.database

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

val gson = Gson()

class StringListConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return if (listType != null) gson.fromJson(value, listType)
        else emptyList()
    }

    @TypeConverter
    fun fromList(list: List<String>?): String {
        return if (list != null) gson.toJson(list)
        else emptyList<String>().toString()
    }
}

class IntegerListConverter {
    @TypeConverter
    fun fromString(value: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return if (listType != null) gson.fromJson(value, listType)
        else emptyList()
    }

    @TypeConverter
    fun fromList(list: List<Int>?): String {
        return if (list != null) gson.toJson(list)
        else emptyList<String>().toString()
    }
}

class TimeStampConverter {
    @TypeConverter
    fun fromDate(date: Date) = date.time

    @TypeConverter
    fun toDate(millisSinceEpoch: Long) = Date(millisSinceEpoch)
}