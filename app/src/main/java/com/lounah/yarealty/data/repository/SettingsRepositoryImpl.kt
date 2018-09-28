package com.lounah.yarealty.data.repository

import com.lounah.yarealty.data.network.GeoObject
import com.lounah.yarealty.data.network.NetworkSource
import com.lounah.yarealty.data.network.Region
import com.lounah.yarealty.data.prefs.SettingsPreferences
import com.lounah.yarealty.device.network.NetworkInfo
import com.lounah.yarealty.domain.model.RealtySettings
import com.lounah.yarealty.domain.repository.SettingsRepository
import io.reactivex.Single
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
        private val settingsPreferences: SettingsPreferences,
        private val api: NetworkSource,
        private val networkInfo: NetworkInfo
) : SettingsRepository {

    private companion object {
        const val DEFAULT_SETTINGS_CASE = "0_0"
    }

    override fun getSettings(): RealtySettings {
        return settingsPreferences.getSettings()
    }

    override fun updateSettingCase(newCase: String): RealtySettings {
        settingsPreferences.updateCase(newCase)
        return getSettings()
    }

    override fun updateStringSetting(key: String, value: String) {
        settingsPreferences.updateString(key, value)
    }

    override fun updateIntSetting(key: String, value: Int) {
        settingsPreferences.updateInteger(key, value)
    }

    override fun updateLongSetting(key: String, value: Long) {
        settingsPreferences.updateLong(key, value)
    }

    override fun updateBooleanSetting(key: String, value: Boolean) {
        updateStringSetting(key, if (value) "YES" else "NO")
    }

    override fun removeKey(key: String) {
        settingsPreferences.removeKey(key)
    }

    override fun clearSettings() {
        updateSettingCase(DEFAULT_SETTINGS_CASE)
        settingsPreferences.clear()
    }

    override fun getRegionsFromLocation(lat: Double, lon: Double): Single<List<GeoObject>> {
        return api.getGeoObjects(lat, lon)
                .map { it.response.geoObjects }
    }

    override fun regionAutoDetect(rgid: Long, lat: Double, lon: Double): Single<Region> {
        val ip = networkInfo.getDeviceIP()
        return api.getCurrentRegion(ip, rgid, lat, lon)
                .map { it.response.region }
    }
}