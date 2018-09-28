package com.lounah.yarealty.data.network

data class ComplainRequestBody(
        val entityId: String,
        val partnerId: String,
        val reason: String,
        val text: String,
        val uid: String)