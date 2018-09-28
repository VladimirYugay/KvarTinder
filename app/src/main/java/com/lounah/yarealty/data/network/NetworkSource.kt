package com.lounah.yarealty.data.network

import android.util.Log
import com.lounah.yarealty.data.entity.Favorites
import com.lounah.yarealty.data.entity.FavoritesPatchBody
import com.lounah.yarealty.data.entity.SearchResponseContent
import com.lounah.yarealty.domain.model.RealtySettings
import io.reactivex.Single
import javax.inject.Inject

class NetworkSource @Inject constructor(private val realtyApi: RealtyApi) {

    fun getAllOffers(settings: RealtySettings): Single<ApiResponse<SearchResponseContent>> {
        return realtyApi.getAllOffers(
                settings.agents,
                settings.areaMax,
                settings.areaMin,
                settings.balcony,
                settings.bathroomUnit,
                settings.buildingType,
                settings.builtYearMax,
                settings.builtYearMin,
                settings.category,
                settings.currency,
                settings.expectDemolition,
                settings.expectMetro,
                settings.floorExceptFirst,
                settings.floorMax,
                settings.floorMin,
                settings.hasAgentFee,
                settings.hasAircondition,
                settings.hasDishwasher,
                settings.hasElectricitySupply,
                settings.hasFurniture,
                settings.hasGasSupply,
                settings.hasHeatingSupply,
                settings.hasPark,
                settings.hasPhoto,
                settings.hasPond,
                settings.hasRefrigerator,
                settings.hasSewerageSupply,
                settings.hasTelevision,
                settings.hasWashingMachine,
                settings.hasWaterSupply,
                settings.houseType,
                settings.kitchenSpaceMax,
                settings.kitchenSpaceMin,
                settings.lastFloor,
                settings.livingSpaceMax,
                settings.livingSpaceMin,
                settings.lotAreaMax,
                settings.lotAreaMin,
                settings.lotType,
                settings.metroTransport,
                settings.minFloors,
                settings.priceMax,
                settings.priceMin,
                settings.priceType,
                settings.rentTime,
                settings.rgid,
                settings.roomsTotal,
                settings.showOnMobile,
                settings.showResale,
                settings.showSimilar,
                settings.sort,
                settings.timeToMetro,
                settings.type,
                settings.withChildren,
                settings.withPets,
                settings.page,
                settings.pageSize
        )
    }

    // todo: тут нужны только id, showOnMobile, currency
    fun getOfferById(id: String) = realtyApi.getOfferById(mapOf("id" to id))

    // todo: тут не нужны сеттинги, только тип и категория
    fun getAllFavoritesIds(): Single<Favorites> {
        return realtyApi.getFavorites(emptyMap())
                .map { it.response }
                .onErrorResumeNext { Single.just(Favorites.emptyFavorites()) }
    }

    // todo: тут не нужны сеттинги, только тип и категория
    fun addToFavorites(vararg ids: String) =
            realtyApi.patchFavorites(emptyMap(), FavoritesPatchBody(ids.toList(), null))

    // todo: тут не нужны сеттинги, только тип и категория
    fun removeFromFavorites(vararg ids: String) =
            realtyApi.patchFavorites(emptyMap(), FavoritesPatchBody(null, ids.toList()))

    fun complainToOffer(complain: ComplainRequestBody) = realtyApi.complain(complain)

    fun getGeoObjects(latitude: Double, longitude: Double) = realtyApi.addressGeocoder(latitude, longitude)

    fun getCurrentRegion(ip: String, rgid: Long, latitude: Double, longitude: Double) = realtyApi.regionAutoDetect(ip, rgid, latitude, longitude)
}