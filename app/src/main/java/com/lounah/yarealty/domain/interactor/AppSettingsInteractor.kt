package com.lounah.yarealty.domain.interactor

import com.lounah.yarealty.domain.repository.OfferRepository
import com.lounah.yarealty.domain.repository.SettingsRepository
import com.yandex.mapkit.map.MapType
import io.reactivex.Single
import javax.inject.Inject

class AppSettingsInteractor @Inject constructor(
        private val offerRepository: OfferRepository,
        private val settingsRepository: SettingsRepository) {

    fun clearData(): Single<Boolean> {
        settingsRepository.clearSettings()
        return offerRepository.clearData()
    }

//    fun changeMapType(newMapType: MapType) {
//        var encodedType = 0
//        when(newMapType) {
//            MapType.NONE -> encodedType = 0
//            MapType.MAP -> encodedType = 0
//            MapType.SATELLITE -> encodedType = 1
//            MapType.HYBRID -> encodedType = 2
//            MapType.VECTOR_MAP -> encodedType = 3
//        }
//        settingsRepository.changeMapType(encodedType)
//    }
}