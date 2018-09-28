package com.lounah.yarealty.domain.repository

import com.lounah.yarealty.data.network.GeoObject
import com.lounah.yarealty.data.network.Region
import com.lounah.yarealty.domain.model.RealtySettings
import io.reactivex.Single

interface SettingsRepository {
    fun getSettings(): RealtySettings
    fun updateSettingCase(newCase: String): RealtySettings
    fun updateStringSetting(key: String, value: String)
    fun updateIntSetting(key: String, value: Int)
    fun updateLongSetting(key: String, value: Long)
    fun updateBooleanSetting(key: String, value: Boolean)
    fun removeKey(key: String)
    fun clearSettings()
    fun getRegionsFromLocation(lat: Double, lon: Double): Single<List<GeoObject>>
    fun regionAutoDetect(rgid: Long, lat: Double, lon: Double): Single<Region>
}