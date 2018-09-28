package com.lounah.yarealty.presentation.flat

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.lounah.yarealty.data.entity.Offer
import com.lounah.yarealty.domain.interactor.FlatInteractor
import com.lounah.yarealty.domain.interactor.SettingInteractor
import com.lounah.yarealty.domain.model.FlatViewObject
import com.lounah.yarealty.utils.SchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FlatsViewModel @Inject constructor(private val flatInteractor: FlatInteractor,
                                         private val settingsInteractor: SettingInteractor,
                                         private val schedulersProvider: SchedulersProvider) : ViewModel() {

    internal val loadingState = MutableLiveData<Boolean>()
    internal val shouldRefreshFlats = MutableLiveData<Boolean>()
    internal val flats = MutableLiveData<List<FlatViewObject>>()
    internal val paginatedFlats = MutableLiveData<List<FlatViewObject>>()

    private val disposables = CompositeDisposable()

    private lateinit var currentSettings: Map<String, Any?>

    internal fun getNextFlats() {
        currentSettings = settingsInteractor.getCurrentSettings().toNullableMap()
        disposables.add(flatInteractor.getFlatsNextPage()
                .doOnSubscribe { loadingState.postValue(true) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({ result ->
                    paginatedFlats.postValue(result)
                    loadingState.postValue(false)
                }, { e -> }))
    }

    internal fun getFlats() {
        currentSettings = settingsInteractor.getCurrentSettings().toNullableMap()
        disposables.add(flatInteractor.getFlats()
                .doOnSubscribe { loadingState.postValue(true) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({ result ->
                    flats.postValue(result)
                    loadingState.postValue(false)
                }, { e -> Log.e("ERROR IN GET FLATS", e.message) }))
    }

    internal fun dislikeFlat(offer: Offer) {
        disposables.add(flatInteractor.saveFlatAsDisliked(offer.offerId)
                .subscribeOn(schedulersProvider.io())
                .subscribe())
    }

    internal fun likeFlat(offer: Offer) {
        disposables.add(flatInteractor.saveFlatAsFavorite(offer)
                .subscribeOn(schedulersProvider.io())
                .subscribe())
    }

    internal fun performReverse(offer: FlatViewObject) {
        disposables.add(flatInteractor.performReverse(offer.refToOffer)
                .subscribeOn(schedulersProvider.io())
                .subscribe())
    }

    internal fun checkSettingsAndRefreshFeedIfShould() {
        if (currentSettings != settingsInteractor.getCurrentSettings().toNullableMap())
            shouldRefreshFlats.postValue(true)
    }

    internal fun restoreSettings() {
        settingsInteractor.updateSettingCase("0_0")
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }
}