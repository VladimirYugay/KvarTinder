package com.lounah.yarealty.data.entity

data class SearchOfferResponse(
        val items: List<Offer>,
        val total: Int)

data class Pager(
        val page: Int,
        val pageSize: Int,
        val sitesPageSize: Int,
        val totalItems: Int,
        val totalPages: Int)

data class SearchResponseContent(
        val offers: SearchOfferResponse,
        val pager: Pager
)