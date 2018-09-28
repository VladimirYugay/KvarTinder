package com.lounah.yarealty.presentation.settings.filters.delegateadapters.buildinginfo

import com.example.delegateadapter.delegate.diff.IComparableItem

class BuildingInfoViewModel(var yearMin: Int, var yearMax: Int) : IComparableItem {

    private companion object {
        private const val ID = "BUILDING_INFO_VIEW_MODEL"
    }

    override fun id(): Any = ID
    override fun content(): Any = ID
}