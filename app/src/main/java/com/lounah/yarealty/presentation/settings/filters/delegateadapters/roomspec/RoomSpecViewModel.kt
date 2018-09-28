package com.lounah.yarealty.presentation.settings.filters.delegateadapters.roomspec

import com.example.delegateadapter.delegate.diff.IComparableItem

class RoomSpecViewModel(var roomAreaMin: Int, var roomAreaMax: Int) : IComparableItem {

    private companion object {
        private const val ID = "HOUSE_INFO_VIEW_MODEL"
    }

    override fun id(): Any = ID
    override fun content(): Any = ID
}