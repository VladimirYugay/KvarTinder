package com.lounah.yarealty.data.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "offer")
data class Offer(
        @PrimaryKey val offerId: String,
        @Embedded(prefix = "area") val area: Area?,
        @Embedded(prefix = "price") val price: Price?,
        @Embedded(prefix = "author") val author: Author?,
        @Embedded(prefix = "location") val location: Location?,
        @Embedded(prefix = "house") val house: House?,
        @Embedded(prefix = "building") val building: Building?,

        val offerType: String?,
        val offerCategory: String?,
        val creationDate: String?,
        val updateDate: String?,
        val roomsTotal: Int,
        val floorsTotal: Int,
        val floorsOffered: List<Int>?,
        val flatType: String?,
        val apartmentNumber: String?,
        val ceilingHeight: Double,
        val totalImages: Int,
        var appLargeImages: List<String>,
        val description: String?,
        val relevance: Double,
        val commissioningDateIndexValue: Int,
        val shareURL: String?,
        val uid: String?,
        val partnerId: String?,
        val views: Int
)

@Entity(tableName = "disliked_offer")
data class DislikedOffer(@PrimaryKey val offerId: String)

data class Area(
        val value: Double,
        val unit: String?
)

data class Price(
        val currency: String?,
        val value: Int,
        val period: String?,
        val unit: String?,
        val trend: String?,
        val valuePerPart: Int,
        val unitPerPart: String?,
        val valueForWhole: Int,
        val unitForWhole: String?
)

data class House(
        val housePart: Boolean
)

data class Author(
        val id: String?,
        val organization: String?,
        val phones: List<String>?,
        val name: String?
)

data class Location(
        val rgid: Long,
        @Embedded(prefix = "point") val point: Point?,
        @Embedded(prefix = "metro") val metro: Metro?,
        @Embedded(prefix = "highway") val highway: Highway?,
        @Embedded(prefix = "station") val station: Station?,
        val geoId: Int?,
        val subjectFederationId: Int?,
        val settlementRgid: Int?,
        val settlementGeoId: Int?,
        val address: String?,
        val geocoderAddress: String?,
        val streetAddress: String?
)

data class Highway(
        val name: String?,
        val distanceKm: Double?
)

data class Metro(
        val metroGeoId: Int?,
        val name: String?,
        val metroTransport: String?,
        val timeToMetro: Int?,
        val latitude: Double?,
        val longitude: Double?,
        val minTimeToMetro: Int?,
        val rgbColor: String?
)

data class Point(
        val latitude: Double?,
        val longitude: Double?,
        val precision: String?
)

data class Station(
        val name: String?,
        val distanceKm: Double?
)

data class Building(
        @Embedded(prefix = "improvements") val improvements: Improvements?,
        @Embedded(prefix = "building_improvements") val buildingImprovementsMap: BuildingImprovementsMap?,
        val builtYear: Int?,
        val builtQuarter: Int?,
        val buildingState: String?,
        val buildingType: String?,
        val siteId: Int?,
        val siteName: String?,
        val siteDisplayName: String?,
        val heatingType: String?
)

data class Improvements(
        val LIFT: Boolean?
)

data class BuildingImprovementsMap(
        val LIFT: Boolean?
)