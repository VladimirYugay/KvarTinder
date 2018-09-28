package com.lounah.yarealty.presentation.settings.filters.delegateadapters.floorinfo

import com.example.delegateadapter.delegate.diff.IComparableItem

class FloorInfoViewModel(var floorMin: Int, var floorMax: Int,
                            var excludeFirst: Boolean) : IComparableItem {

    private companion object {
        private const val ID = "FLOOR_INFO_VIEW_MODEL"
    }

    override fun id(): Any = ID
    override fun content(): Any = ID
}