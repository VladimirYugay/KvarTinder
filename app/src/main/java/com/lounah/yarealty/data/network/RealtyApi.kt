package com.lounah.yarealty.data.network

import com.lounah.yarealty.data.entity.Favorites
import com.lounah.yarealty.data.entity.FavoritesPatchBody
import com.lounah.yarealty.data.entity.Offer
import com.lounah.yarealty.data.entity.SearchResponseContent
import io.reactivex.Single
import retrofit2.http.*

interface RealtyApi {

    @GET("offerWithSiteSearch.json")
    fun getAllOffers(
            @Query("agents") agents: String?,
            @Query("areaMax") areaMax: Int?,
            @Query("areaMin") areaMin: Int?,
            @Query("balcony") balcony: String?,
            @Query("bathroomUnit") bathroomUnit: String?,
            @Query("buildingType") buildingType: List<String>?,
            @Query("builtYearMax") builtYearMax: Int?,
            @Query("builtYearMin") builtYearMin: Int?,
            @Query("category") category: String?,
            @Query("currency") currency: String?,
            @Query("expectDemolition") expectDemolition: String?,
            @Query("expectMetro") expectMetro: String?,
            @Query("floorExceptFirst") floorExceptFirst: String?,
            @Query("floorMax") floorMax: Int?,
            @Query("floorMin") floorMin: Int?,
            @Query("hasAgentFee") hasAgentFee: String?,
            @Query("hasAircondition") hasAircondition: String?,
            @Query("hasDishwasher") hasDishwasher: String?,
            @Query("hasElectricitySupply") hasElectricitySupply: String?,
            @Query("hasFurniture") hasFurniture: String?,
            @Query("hasGasSupply") hasGasSupply: String?,
            @Query("hasHeatingSupply") hasHeatingSupply: String?,
            @Query("hasPark") hasPark: String?,
            @Query("hasPhoto") hasPhoto: String?,
            @Query("hasPond") hasPond: String?,
            @Query("hasRefrigerator") hasRefrigerator: String?,
            @Query("hasSewerageSupply") hasSewerageSupply: String?,
            @Query("hasTelevision") hasTelevision: String?,
            @Query("hasWashingMachine") hasWashingMachine: String?,
            @Query("hasWaterSupply") hasWaterSupply: String?,
            @Query("houseType") houseType: List<String>?,
            @Query("kitchenSpaceMax") kitchenSpaceMax: Int?,
            @Query("kitchenSpaceMin") kitchenSpaceMin: Int?,
            @Query("lastFloor") lastFloor: String?,
            @Query("livingSpaceMax") livingSpaceMax: Int?,
            @Query("livingSpaceMin") livingSpaceMin: Int?,
            @Query("lotAreaMax") lotAreaMax: Int?,
            @Query("lotAreaMin") lotAreaMin: Int?,
            @Query("lotType") lotType: String?,
            @Query("metroTransport") metroTransport: String?,
            @Query("minFloors") minFloors: Int?,
            @Query("priceMax") priceMax: Int?,
            @Query("priceMin") priceMin: Int?,
            @Query("priceType") priceType: String?,
            @Query("rentTime") rentTime: String?,
            @Query("rgid") rgid: Long?,
            @Query("roomsTotal") roomsTotal: List<Int>?,
            @Query("showOnMobile") showOnMobile: String?,
            @Query("showResale") showResale: String?,
            @Query("showSimilar") showSimilar: String?,
            @Query("sort") sort: String?,
            @Query("timeToMetro") timeToMetro: Int?,
            @Query("type") type: String?,
            @Query("withChildren") withChildren: String?,
            @Query("withPets") withPets: String?,
            @Query("page") page: Int?,
            @Query("pageSize") pageSize: Int?
    ): Single<ApiResponse<SearchResponseContent>>


    @GET("cardWithViews.json")
    fun getOfferById(@QueryMap map: Map<String, String>): Single<ApiResponse<Offer>>

    @GET("favorites.json")
    fun getFavorites(@QueryMap map: Map<String, List<String>>): Single<ApiResponse<Favorites>>

    @PATCH("favorites.json")
    fun patchFavorites(@QueryMap map: Map<String, String>, @Body requestBody: FavoritesPatchBody): Single<ApiResponse<Favorites>>

    @POST("complain.json")
    fun complain(@Body complain: ComplainRequestBody): Single<ApiResponse<ComplainResponse>>

    @GET("regionAutodetect.json")
    fun regionAutoDetect(
            @Query("ip") ip: String,
            @Query("rgid") rgid: Long,
            @Query("latitude") latitude: Double,
            @Query("longitude") longitude: Double
    ): Single<ApiResponse<RegionResponse>>

    @GET("addressGeocoder.json")
    fun addressGeocoder(
            @Query("latitude") lat: Double,
            @Query("longitude") lon: Double
    ): Single<ApiResponse<GeoObjectResponse>>
}