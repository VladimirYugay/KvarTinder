package com.lounah.yarealty.presentation.favourites.myfavorites

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.lounah.yarealty.data.entity.Call
import com.lounah.yarealty.data.entity.Offer
import com.lounah.yarealty.domain.interactor.CallHistoryInteractor
import com.lounah.yarealty.domain.interactor.FlatInteractor
import com.lounah.yarealty.domain.model.FlatViewObject
import com.lounah.yarealty.utils.SchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(private val flatInteractor: FlatInteractor,
                                              private val callHistoryInteractor: CallHistoryInteractor,
                                              private val schedulersProvider: SchedulersProvider) : ViewModel() {

    internal val loadingState = MutableLiveData<Boolean>()
    internal val successDislikeActionResult = MutableLiveData<Boolean>()
    internal val favouriteFlats = MutableLiveData<List<FlatViewObject>>()

    private val disposables = CompositeDisposable()

    internal fun dislikeFlat(offer: Offer) {
        disposables.add(flatInteractor.removeFlatFromFavorite(offer)
                .doOnSubscribe { loadingState.postValue(true) }
                .doFinally { loadingState.postValue(false) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({ successDislikeActionResult.value = true },
                        { successDislikeActionResult.value = false }))
    }

    internal fun dislikeFlatsById(flatIds: List<String>) {
        disposables.add(flatInteractor.dislikeFlatsByIds(flatIds)
                .doOnSubscribe { loadingState.postValue(true) }
                .doFinally { loadingState.postValue(false) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe({ successDislikeActionResult.value = true },
                        { successDislikeActionResult.value = false }))
    }

    internal fun getFavouriteFlats() {
        disposables.add(flatInteractor.getFavoriteFlats()
                .doOnSubscribe { loadingState.postValue(true) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .defaultIfEmpty(emptyList())
                .subscribe({
                    favouriteFlats.postValue(it.reversed())
                    loadingState.postValue(false)
                }, {e -> }))
    }

    internal fun addNewCall(call: Call) {
        disposables.add(callHistoryInteractor.addNewCall(call)
                .subscribeOn(schedulersProvider.io())
                .subscribe({}, {e -> e.printStackTrace()}))
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }
}