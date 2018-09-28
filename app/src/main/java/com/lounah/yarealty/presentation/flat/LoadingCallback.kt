package com.lounah.yarealty.presentation.flat

import com.lounah.yarealty.domain.model.FlatViewObject

interface LoadingCallback {

    fun loadMore()

    fun likeFlat(offer: FlatViewObject)

    fun dislikeFlat(offer: FlatViewObject)
}