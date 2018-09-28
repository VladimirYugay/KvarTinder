package com.lounah.yarealty.presentation.settings.filters

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import com.lounah.yarealty.data.network.GeoObject
import com.lounah.yarealty.data.network.Region
import com.lounah.yarealty.device.location.LocationInfo
import com.lounah.yarealty.device.location.LocationListener
import com.lounah.yarealty.domain.interactor.SettingInteractor
import com.lounah.yarealty.utils.SchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SearchFiltersViewModel @Inject constructor(
        private val settingsInteractor: SettingInteractor,
        private val locationInfo: LocationInfo,
        private val schedulersProvider: SchedulersProvider) : ViewModel() {

    private val spinnerSelectedBuyRentPosition = MutableLiveData<Int>()
    private val spinnerSelectedTypePosition = MutableLiveData<Int>()
    private val dealTypeTrigger = DealTypeLiveData(spinnerSelectedBuyRentPosition, spinnerSelectedTypePosition)
    private val compositeDisposable = CompositeDisposable()

    private var locationListener: LocationListener? = null
    private var globalRegionId: Long = -1L

    internal val dealType: LiveData<DealType> = switchMap(dealTypeTrigger) {
        val newDealType = MutableLiveData<DealType>()
        newDealType.value = DealType.typeByCode("${it.first}_${it.second}")
        updateSettingsCase(newDealType.value!!)
        newDealType
    }

    internal val localRegions = MutableLiveData<List<GeoObject>>()
    internal val globalRegion = MutableLiveData<Region>()


    fun setBuyRentPosition(newPosition: Int) {
        spinnerSelectedBuyRentPosition.value = newPosition
        if (spinnerSelectedTypePosition.value != null)
            dealTypeTrigger.value = Pair(newPosition, spinnerSelectedTypePosition.value!!)
    }

    fun setTypePosition(newPosition: Int) {
        spinnerSelectedTypePosition.value = newPosition
        if (spinnerSelectedBuyRentPosition.value != null)
            dealTypeTrigger.value = Pair(spinnerSelectedBuyRentPosition.value!!, newPosition)
    }

    private fun updateSettingsCase(newDealType: DealType) = settingsInteractor.updateSettingCase(newDealType.toString())

    fun updateStringSetting(key: String, value: String) {
        settingsInteractor.updateStringSetting(key, value)
    }

    fun updateIntSetting(key: String, value: Int) {
        settingsInteractor.updateIntSetting(key, value)
    }

    fun updateLongSetting(key: String, value: Long) {
        settingsInteractor.updateLongSetting(key, value)
    }

    fun updateLocalRegionId(value: Long) {
        updateLongSetting("rgid", value)
    }

    fun updateGlobalRegionId(value: Long) {
        globalRegionId = value
        updateLongSetting("rgid", value)
    }

    fun restoreGlobalRegionId() {
        if (globalRegionId != -1L)
            updateLongSetting("rgid", globalRegionId)
        else
            settingsInteractor.removeKey("rgid")
    }

    fun updateBooleanSetting(key: String, value: Boolean) {
        settingsInteractor.updateBooleanSetting(key, value)
    }

    fun getLocalRegionId(latitude: Double, longitude: Double) {
        compositeDisposable.add(settingsInteractor.getGeoObjectsByLocation(latitude, longitude)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({localRegions::setValue}, {e -> }))
    }

    fun getGlobalRegion(latitude: Double, longitude: Double) {
        compositeDisposable.add(settingsInteractor.getGlobalRegion(latitude, longitude)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({globalRegion::setValue}, {e -> }))
    }

    fun updateLocation(listener: LocationListener) {
        stopLocationUpdate()
        locationListener = listener
        locationInfo.registerLocationListener(locationListener!!)
    }

    fun stopLocationUpdate() {
        locationListener?.let { locationInfo.removeLocationListener(it) }
    }

    fun isLocationProvidersEnabled(): Boolean {
        return locationInfo.isGpsProviderEnabled() || locationInfo.isNetworkProviderEnabled()
    }

    override fun onCleared() {
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
        stopLocationUpdate()
        locationListener = null
        super.onCleared()
    }

    private class DealTypeLiveData(buyRentPosition: LiveData<Int>, typePosition: LiveData<Int>)
        : MediatorLiveData<Pair<Int, Int>>() {
        init {
            if (typePosition.value != null && buyRentPosition.value != null) {
                addSource(buyRentPosition) { first -> value = Pair(first!!, typePosition.value!!) }
                addSource(typePosition) { second -> value = Pair(buyRentPosition.value!!, second!!) }
            }
        }
    }

    enum class DealType(private val code: String) {
        BUY_FLAT("0_0"), BUY_ROOM("0_1"), BUY_HOUSE("0_2"), BUY_AREA("0_3"),
        RENT_FLAT("1_0"), RENT_ROOM("1_1"), RENT_HOUSE("1_2");

        override fun toString() = code

        companion object {
            fun typeByCode(code: String) = DealType.values().firstOrNull { it.toString() == code }
                    ?: BUY_FLAT
        }
    }
}