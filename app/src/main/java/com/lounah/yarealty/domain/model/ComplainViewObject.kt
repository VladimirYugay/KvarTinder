package com.lounah.yarealty.domain.model


data class ComplainViewObject(
        val offerId: String,
        val partnerId: String,
        val text: String,
        val uid: String,
        val reason: String = "SPECIFIC_REASON")