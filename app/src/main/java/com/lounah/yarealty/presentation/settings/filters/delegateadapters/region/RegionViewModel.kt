package com.lounah.yarealty.presentation.settings.filters.delegateadapters.region

import com.example.delegateadapter.delegate.diff.IComparableItem

class RegionViewModel(var areaMin: Int, var areaMax: Int) : IComparableItem {

    private companion object {
        private const val ID = "REGION_VIEW_MODEL"
    }

    override fun id(): Any = ID
    override fun content(): Any = ID
}