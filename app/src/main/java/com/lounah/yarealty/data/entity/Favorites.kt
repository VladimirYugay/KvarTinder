package com.lounah.yarealty.data.entity

data class Favorites(val actual: List<String>,
                     val outdated: List<String>,
                     val relevant: List<String>) {
    companion object {
        fun emptyFavorites() = Favorites(emptyList(), emptyList(), emptyList())
    }
}

data class FavoritesPatchBody(val add: List<String>?,
                              val remove: List<String>?)