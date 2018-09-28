package com.lounah.yarealty.presentation.flatdetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.lounah.yarealty.data.entity.Call
import com.lounah.yarealty.data.entity.Offer
import com.lounah.yarealty.domain.interactor.CallHistoryInteractor
import com.lounah.yarealty.domain.interactor.FlatInteractor
import com.lounah.yarealty.domain.model.ComplainViewObject
import com.lounah.yarealty.domain.model.FlatViewObject
import com.lounah.yarealty.utils.SchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FlatDetailsViewModel @Inject constructor(private val flatInteractor: FlatInteractor,
                                               private val callHistoryInteractor: CallHistoryInteractor,
                                               private val schedulersProvider: SchedulersProvider
) : ViewModel() {
    private val disposables = CompositeDisposable()

    internal val flat = MutableLiveData<FlatViewObject>()
    internal val loadingState = MutableLiveData<Boolean>()
    internal val actionsResult = MutableLiveData<Boolean>()

    internal fun loadFlat(offerId: String) {
        disposables.add(flatInteractor.getFlatById(offerId)
                .doOnSubscribe { loadingState.postValue(true) }
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe { res ->
                    flat.postValue(res)
                    loadingState.postValue(false)
                })
    }

    internal fun dislikeFlatById(flatId: String) {
        disposables.add(flatInteractor.saveFlatAsDisliked(flatId)
                .subscribeOn(schedulersProvider.io())
                .subscribe(actionsResult::postValue))
    }

    internal fun likeFlat(offer: Offer) {
        disposables.add(flatInteractor.saveFlatAsFavorite(offer)
                .subscribeOn(schedulersProvider.io())
                .subscribe(actionsResult::postValue))
    }

    internal fun complainToFlat(offer: ComplainViewObject) {
        disposables.add(flatInteractor.complainToOffer(offer)
                .subscribeOn(schedulersProvider.io())
                .subscribe({ actionsResult::postValue }, {e -> }))
    }


    internal fun addNewCall(call: Call) {
        disposables.add(callHistoryInteractor.addNewCall(call)
                .subscribeOn(schedulersProvider.io())
                .subscribe())
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }
}