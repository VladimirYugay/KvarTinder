package com.lounah.yarealty.presentation.settings.appsettings

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.bumptech.glide.Glide
import com.lounah.yarealty.domain.interactor.AppSettingsInteractor
import com.lounah.yarealty.utils.SchedulersProvider
import com.yandex.mapkit.map.MapType
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AppSettingsViewModel @Inject constructor(
        private val interactor: AppSettingsInteractor,
        private val schedulersProvider: SchedulersProvider
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    internal val isDataClearing = MutableLiveData<Boolean>()
    internal val isImagesCacheClearing = MutableLiveData<Boolean>()

    internal fun clearImagesCache(ctx: Context?) {
        compositeDisposable.add(
                Observable.fromCallable { ctx?.let { Glide.get(it).clearDiskCache() } }
                        .subscribeOn(schedulersProvider.computation())
                        .observeOn(schedulersProvider.ui())
                        .doOnSubscribe { isImagesCacheClearing.postValue(true) }
                        .subscribe { isImagesCacheClearing.postValue(false) })
    }

    internal fun clearData() {
        compositeDisposable.add(interactor.clearData()
                .subscribeOn(schedulersProvider.computation())
                .observeOn(schedulersProvider.ui())
                .doOnSubscribe { isDataClearing.postValue(true) }
                .subscribe { _ -> isDataClearing.postValue(false) })
    }

//    internal fun changeMapType(newMapType: MapType) {
//        interactor.changeMapType(newMapType)
//    }

    override fun onCleared() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
        super.onCleared()
    }
}