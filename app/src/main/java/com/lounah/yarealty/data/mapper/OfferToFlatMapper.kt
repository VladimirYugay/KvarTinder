package com.lounah.yarealty.data.mapper

import com.lounah.yarealty.data.entity.Offer
import com.lounah.yarealty.data.entity.Price
import com.lounah.yarealty.domain.model.FlatViewObject
import com.lounah.yarealty.utils.Utils

object OfferToFlatMapper {
    fun map(offer: Offer): FlatViewObject {
        return FlatViewObject(
                offer.offerId,
                mapCost(offer.price),
                offer.author?.name,
                offer.author?.phones?.firstOrNull(),
                mapImages(offer.appLargeImages),
                offer.location?.address,
                offer.description,
                if (offer.roomsTotal == 0) null else offer.roomsTotal.toString(),
                offer.area?.value?.toString(),
                offer.area?.unit, // SQUARE_METER
                offer.floorsOffered?.joinToString(separator = ", "),
                offer.floorsTotal.toString(),
                offer.location?.point,
                mapOthers(offer),
                offer
        )
    }

    private fun mapImages(images: List<String>) = images.map { "https:$it" }

    private fun mapCost(price: Price?): String? {
        if (price == null) return null
        return Utils.formatIntToCostString(price.value, price.currency ?: "?")
    }

    private fun mapOthers(offer: Offer): Map<String, String?> {
        return mapOf(
                "ceilingHeight" to Utils.formatDoubleToString(offer.ceilingHeight),
                "siteDisplayName" to offer.building?.siteDisplayName,
                "builtQuarter" to offer.building?.builtQuarter?.toString(),
                "builtYear" to offer.building?.builtYear?.toString(),
                "metro" to offer.location?.metro?.name,
                "timeToMetro" to offer.location?.metro?.timeToMetro?.toString(),
                "views" to offer.views.toString()
        )
    }
}