package com.lounah.yarealty.domain.interactor

import android.util.Log
import com.lounah.yarealty.data.entity.Offer
import com.lounah.yarealty.data.mapper.OfferToFlatMapper
import com.lounah.yarealty.data.network.ComplainRequestBody
import com.lounah.yarealty.domain.model.ComplainViewObject
import com.lounah.yarealty.domain.model.FlatViewObject
import com.lounah.yarealty.domain.repository.OfferRepository
import com.lounah.yarealty.domain.repository.SettingsRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class FlatInteractor @Inject constructor(private val offerRepository: OfferRepository,
                                         private val settingsRepository: SettingsRepository) {

    fun getFlatById(id: String): Single<FlatViewObject> {
        return offerRepository.fetchOfferById(id)
                .map { OfferToFlatMapper.map(it) }
    }

    fun getFlats(): Single<List<FlatViewObject>> {
        return offerRepository.fetchOffers(settingsRepository.getSettings())
                .map { mapToFlatViewObjectList(it) }
    }

    fun getFlatsNextPage(): Single<List<FlatViewObject>> {
        return offerRepository.fetchOffersNextPage(settingsRepository.getSettings())
                .map { mapToFlatViewObjectList(it) }
    }

    fun getFavoriteFlats(): Flowable<List<FlatViewObject>> {
        return offerRepository.fetchFavoriteOffers()
                .map { mapToFlatViewObjectList(it) }
    }

    fun getSharedOffers(offerIds: List<String>): List<Single<FlatViewObject>> {
        return offerRepository.fetchOffersById(offerIds)
                .map { it.map { mapOfferToFlatViewObject(it) } }
    }

    fun dislikeFlatsByIds(flatIds: List<String>): Completable {
        return offerRepository.dislikeFlatsByIds(flatIds)
    }

    fun removeFlatFromFavorite(offer: Offer): Single<Boolean> {
        return offerRepository.removeFavoriteOffer(offer)
    }

    fun saveFlatAsFavorite(offer: Offer): Single<Boolean> {
        return offerRepository.saveOfferToFavorites(offer)
    }

    fun saveFlatAsDisliked(offerId: String): Single<Boolean> {
        return offerRepository.saveOfferToDisliked(offerId)
    }

    fun performReverse(offer: Offer): Completable {
        return offerRepository.performReverse(offer)
    }

    fun complainToOffer(complain: ComplainViewObject): Single<Boolean> {
        val complainRequestBody = ComplainRequestBody(
                complain.offerId,
                complain.partnerId,
                complain.reason,
                complain.text,
                complain.uid)
        return offerRepository.complainToOffer(complainRequestBody)
    }

    private fun mapOffersToFlatViewObjectList(offers: List<Offer>): List<FlatViewObject> {
        val res = mutableListOf<FlatViewObject>()
        offers.forEach { res.add(OfferToFlatMapper.map(it)) }
        return res
    }

    private fun mapOfferToFlatViewObject(offer: Offer): FlatViewObject {
        return OfferToFlatMapper.map(offer)
    }



    private fun mapToFlatViewObjectList(offers: List<Offer>): List<FlatViewObject> {
        val res = mutableListOf<FlatViewObject>()
        offers.forEach { res.add(OfferToFlatMapper.map(it)) }
        return res
    }
}