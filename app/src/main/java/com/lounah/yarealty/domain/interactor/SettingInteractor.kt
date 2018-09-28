package com.lounah.yarealty.domain.interactor

import com.lounah.yarealty.data.network.Region
import com.lounah.yarealty.domain.repository.SettingsRepository
import io.reactivex.Single
import javax.inject.Inject

class SettingInteractor @Inject constructor(private val settingsRepository: SettingsRepository) {

    fun getCurrentSettings() = settingsRepository.getSettings()

    fun updateSettingCase(newCase: String) = settingsRepository.updateSettingCase(newCase)

    fun updateStringSetting(key: String, value: String) = settingsRepository.updateStringSetting(key, value)

    fun updateIntSetting(key: String, value: Int) = settingsRepository.updateIntSetting(key, value)

    fun updateLongSetting(key: String, value: Long) = settingsRepository.updateLongSetting(key, value)

    fun updateBooleanSetting(key: String, value: Boolean) = settingsRepository.updateBooleanSetting(key, value)

    fun removeKey(key: String) = settingsRepository.removeKey(key)

    fun getGeoObjectsByLocation(latitude: Double, longitude: Double) = settingsRepository.getRegionsFromLocation(latitude, longitude)

    fun getGlobalRegion(latitude: Double, longitude: Double): Single<Region> {
        return settingsRepository.getRegionsFromLocation(latitude, longitude)
                .flatMap { regions ->
                    val region = regions.firstOrNull()
                    region?.let {
                        settingsRepository.regionAutoDetect(it.rgid, it.latitude, it.longitude)
                    }
                }
    }
}