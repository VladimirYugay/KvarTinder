package com.lounah.yarealty.presentation.settings.filters.delegateadapters.houseinfo

import com.example.delegateadapter.delegate.diff.IComparableItem

class HouseInfoViewModel(var houseAreaMin: Int, var houseAreaMax: Int,
                        var areaMin: Int, var areaMax: Int) : IComparableItem {

    private companion object {
        private const val ID = "HOUSE_INFO_VIEW_MODEL"
    }

    override fun id(): Any = ID
    override fun content(): Any = ID
}