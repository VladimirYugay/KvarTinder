package com.lounah.yarealty.presentation.settings.filters.delegateadapters.areakitchen

import com.example.delegateadapter.delegate.diff.IComparableItem

class AreaKitchenViewModel(var minAreaTotal: Int, var maxAreaTotal: Int,
                           var minAreaKitchen: Int, var maxAreaKitchen: Int) : IComparableItem {

    private companion object {
        private const val ID = "AREA_KITCHEN_VIEW_MODEL"
    }

    override fun id(): Any = ID
    override fun content(): Any = ID
}