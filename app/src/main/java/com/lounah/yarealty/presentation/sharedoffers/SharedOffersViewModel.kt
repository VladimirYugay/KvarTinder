package com.lounah.yarealty.presentation.sharedoffers

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.lounah.yarealty.data.entity.Call
import com.lounah.yarealty.data.entity.Offer
import com.lounah.yarealty.domain.interactor.CallHistoryInteractor
import com.lounah.yarealty.domain.interactor.FlatInteractor
import com.lounah.yarealty.domain.model.FlatViewObject
import com.lounah.yarealty.utils.SchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SharedOffersViewModel @Inject constructor(private val flatInteractor: FlatInteractor,
                                                private val callHistoryInteractor: CallHistoryInteractor,
                                                private val schedulersProvider: SchedulersProvider) : ViewModel() {

    internal val loadingState = MutableLiveData<Boolean>()
    internal val favouriteFlats = MutableLiveData<List<FlatViewObject>>()

    private val disposables = CompositeDisposable()

    private lateinit var flatsDisposable: Disposable

    internal fun dislikeFlat(offer: Offer) {
        disposables.add(flatInteractor.removeFlatFromFavorite(offer)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe())
    }

    internal fun markFlatAsFavourite(offer: Offer) {
        disposables.add(flatInteractor.saveFlatAsFavorite(offer)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe())
    }

    internal fun getSharedOffers(offerIds: List<String>) {
        val sharedOffers = mutableListOf<FlatViewObject>()
        flatInteractor.getSharedOffers(offerIds)
                .map {
                    disposables.add(it.subscribeOn(schedulersProvider.io())
                            .observeOn(schedulersProvider.ui())
                            .doFinally { favouriteFlats.postValue(sharedOffers) }
                            .subscribe({flat -> sharedOffers.add(flat)}, { e -> }))
                }
    }

    internal fun addNewCall(call: Call) {
        disposables.add(callHistoryInteractor.addNewCall(call)
                .subscribeOn(schedulersProvider.io())
                .subscribe())
    }

    internal fun unsubscribeFromFlats() {
        if (::flatsDisposable.isInitialized)
            flatsDisposable.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

}