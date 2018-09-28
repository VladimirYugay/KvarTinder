package com.lounah.yarealty.presentation.settings.filters.delegateadapters.roominfo

import com.example.delegateadapter.delegate.diff.IComparableItem

class RoomInfoViewModel(var roomAreaMin: Int, var roomAreaMax: Int,
                         var kitchenAreaMin: Int, var kitchenAreaMax: Int) : IComparableItem {

    private companion object {
        private const val ID = "HOUSE_INFO_VIEW_MODEL"
    }

    override fun id(): Any = ID
    override fun content(): Any = ID
}