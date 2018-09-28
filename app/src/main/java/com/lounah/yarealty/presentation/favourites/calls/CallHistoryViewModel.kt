package com.lounah.yarealty.presentation.favourites.calls

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.lounah.yarealty.data.entity.Call
import com.lounah.yarealty.domain.interactor.CallHistoryInteractor
import com.lounah.yarealty.utils.SchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CallHistoryViewModel @Inject constructor(private val callHistoryInteractor: CallHistoryInteractor,
                                               private val rxSchedulersProvider: SchedulersProvider) : ViewModel() {

    internal val loadingState = MutableLiveData<Boolean>()
    internal val calls = MutableLiveData<List<Call>>()

    private val disposables = CompositeDisposable()

    internal fun getCallHistory() {
        disposables.add(callHistoryInteractor.getCallHistory()
                .doOnSubscribe { loadingState.postValue(true) }
                .subscribeOn(rxSchedulersProvider.io())
                .observeOn(rxSchedulersProvider.ui())
                .defaultIfEmpty(emptyList())
                .subscribe({
                    calls.postValue(it.reversed())
                    loadingState.postValue(false)
                }, {e -> }))
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }
}