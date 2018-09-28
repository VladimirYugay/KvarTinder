package com.lounah.yarealty.domain.model

import com.lounah.yarealty.data.entity.Offer
import com.lounah.yarealty.data.entity.Point

data class FlatViewObject(
        val id: String,
        val cost: String?,
        val aboutAuthor: String?,
        val phoneNumber: String?,
        val images: List<String>,
        val address: String?,
        val description: String?,
        val roomsTotal: String?,
        val area: String?,
        val areaUnit: String?,
        val floors: String?,
        val totalFloors: String?,
        val location: Point?,
        val others: Map<String, String?>,
        val refToOffer: Offer
)