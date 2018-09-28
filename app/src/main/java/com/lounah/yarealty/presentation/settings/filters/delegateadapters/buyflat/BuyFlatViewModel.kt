package com.lounah.yarealty.presentation.settings.filters.delegateadapters.buyflat

import com.example.delegateadapter.delegate.diff.IComparableItem

class BuyFlatViewModel(var totalAreaMin: Int, var totalAreaMax: Int,
                       var totalAreaKitchenMin: Int, var totalAreaKitchenMax: Int,
                       var freePlan: Boolean) : IComparableItem {

    private companion object {
        private const val ID = "BUY_FLAT_VIEW_MODEL"
    }

    override fun id(): Any = ID
    override fun content(): Any = ID
}