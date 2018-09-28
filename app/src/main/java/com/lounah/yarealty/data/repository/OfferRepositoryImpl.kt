package com.lounah.yarealty.data.repository

import com.lounah.yarealty.data.database.dao.DislikedOfferDao
import com.lounah.yarealty.data.database.dao.FavoriteOfferDao
import com.lounah.yarealty.data.entity.DislikedOffer
import com.lounah.yarealty.data.entity.Offer
import com.lounah.yarealty.data.entity.Pager
import com.lounah.yarealty.data.network.ComplainRequestBody
import com.lounah.yarealty.data.network.NetworkSource
import com.lounah.yarealty.data.prefs.SettingsPreferences
import com.lounah.yarealty.domain.model.RealtySettings
import com.lounah.yarealty.domain.repository.OfferRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(
        private val offerDao: FavoriteOfferDao,
        private val dislikedOfferDao: DislikedOfferDao,
        private val api: NetworkSource,
        private val settingsPreferences: SettingsPreferences) : OfferRepository {

    private var pager: Pager? = null

    override fun fetchOfferById(offerId: String): Single<Offer> {
        return api.getOfferById(offerId)
                .map { it.response }
    }

    override fun fetchOffers(params: RealtySettings): Single<List<Offer>> {
        return api.getAllOffers(params)
                .map { searchResult ->
                    val likedOffers = offerDao.getFavoriteOfferIds()
                    val dislikedOffers = dislikedOfferDao.getDislikedOffers().map { it.offerId }
                    pager = searchResult.response.pager

                    settingsPreferences.saveCasePage(params.settingsCase.settingCode, pager?.page
                            ?: 0)

                    val res = searchResult.response.offers.items.filter {
                        it.offerId !in likedOffers && it.offerId !in dislikedOffers
                    }
                    res
                }
    }

    override fun fetchOffersNextPage(params: RealtySettings): Single<List<Offer>> {
        if (pager!!.page > pager!!.totalPages) return Single.just(emptyList())
        val copy = params.copy(page = pager?.page?.plus(1) ?: 0)
        return fetchOffers(copy)
    }

    override fun saveOfferToFavorites(offer: Offer): Single<Boolean> {
        return Single.fromCallable {
            offerDao.insert(offer)
            true
        }
    }

    override fun saveOfferToDisliked(offerId: String): Single<Boolean> {
        return Single.fromCallable {
            dislikedOfferDao.insert(DislikedOffer(offerId))
            true
        }
    }

    override fun fetchFavoriteOffers(): Flowable<List<Offer>> {
        return offerDao.getOffers()
    }

    override fun fetchOffersById(ids: List<String>): List<Single<Offer>> {
        val res = mutableListOf<Single<Offer>>()
        ids.forEach { res.add(api.getOfferById(it).map { it.response }) }
        return res
    }

    override fun removeFavoriteOffer(offer: Offer): Single<Boolean> {
        return Single.fromCallable {
            offerDao.delete(offer)
            dislikedOfferDao.insert(DislikedOffer(offer.offerId))
            true
        }
    }

    override fun dislikeFlatsByIds(offerIds: List<String>): Completable {
        return Completable.fromAction {
            offerIds.forEach {
                offerDao.deleteById(it)
                dislikedOfferDao.insert(DislikedOffer(it))
            }
        }
    }


    override fun clearData(): Single<Boolean> {
        return Single.fromCallable {
            offerDao.clearData()
            dislikedOfferDao.clearData()
            true
        }
    }

    override fun performReverse(offer: Offer): Completable {
        return Completable.fromAction {
            offerDao.delete(offer)
            dislikedOfferDao.delete(DislikedOffer(offer.offerId))
        }
    }

    override fun complainToOffer(complain: ComplainRequestBody): Single<Boolean> {
        return api.complainToOffer(complain)
                .map { it.response.status == "OK" }
    }
}