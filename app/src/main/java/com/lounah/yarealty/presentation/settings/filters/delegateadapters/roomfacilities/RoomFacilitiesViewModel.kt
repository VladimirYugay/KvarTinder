package com.lounah.yarealty.presentation.settings.filters.delegateadapters.roomfacilities

import com.example.delegateadapter.delegate.diff.IComparableItem
import java.nio.ByteBuffer
import java.util.*

class RoomFacilitiesViewModel(var hasFridgeChecked: Boolean,
                              var hasDishWasherChecked: Boolean,
                              var hasAirConditioningChecked: Boolean,
                              var hasWasherChecked: Boolean,
                              var hasFurnitureChecked: Boolean,
                              var petsAllowedChecked: Boolean,
                              var kidsAllowedChecked: Boolean) : IComparableItem {

    private companion object {
        private const val ID = "ROOM_FACILITIES_VIEW_MODEL"
    }

    override fun id(): Any = ID
    override fun content(): Any = ID
}