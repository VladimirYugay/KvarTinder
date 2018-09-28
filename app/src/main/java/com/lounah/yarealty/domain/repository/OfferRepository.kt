package com.lounah.yarealty.domain.repository

import com.lounah.yarealty.data.entity.Offer
import com.lounah.yarealty.data.network.ComplainRequestBody
import com.lounah.yarealty.domain.model.ComplainViewObject
import com.lounah.yarealty.domain.model.FlatViewObject
import com.lounah.yarealty.domain.model.RealtySettings
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface OfferRepository {
    fun fetchOfferById(offerId: String): Single<Offer>
    fun fetchOffersById(ids: List<String>): List<Single<Offer>>
    fun fetchOffers(params: RealtySettings): Single<List<Offer>>
    fun fetchOffersNextPage(params: RealtySettings): Single<List<Offer>>

    fun saveOfferToFavorites(offer: Offer): Single<Boolean>
    fun saveOfferToDisliked(offerId: String): Single<Boolean>
    fun dislikeFlatsByIds(offerIds: List<String>): Completable

    fun fetchFavoriteOffers(): Flowable<List<Offer>>
    fun removeFavoriteOffer(offer: Offer): Single<Boolean>

    fun clearData(): Single<Boolean>

    fun performReverse(offer: Offer): Completable

    fun complainToOffer(complain: ComplainRequestBody): Single<Boolean>
}