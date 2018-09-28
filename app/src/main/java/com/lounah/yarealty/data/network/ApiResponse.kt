package com.lounah.yarealty.data.network

data class ApiResponse<T>(val response: T)

data class ComplainResponse(val status: String, val id: String)

data class GeoObject(
        val rgid: Long,
        val shortAddress: String,
        val address: String,
        val country: Int,
        val latitude: Double,
        val longitude: Double)

data class GeoObjectResponse(val geoObjects: List<GeoObject>)

data class Region(val rgid: Long, val name: String)

data class RegionResponse(val region: Region)