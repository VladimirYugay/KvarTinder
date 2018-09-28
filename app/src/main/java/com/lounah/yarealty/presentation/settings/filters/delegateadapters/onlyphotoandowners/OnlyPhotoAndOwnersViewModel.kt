package com.lounah.yarealty.presentation.settings.filters.delegateadapters.onlyphotoandowners

import com.example.delegateadapter.delegate.diff.IComparableItem

class OnlyPhotoAndOwnersViewModel(var isOnlyFromOwnersClicked: Boolean,
                              var isOnlyWithPhotoClicked: Boolean) : IComparableItem {

    private companion object {
        private const val ID = "ONLY_FROM_OWNERS_WITH_PHOTO_VIEW_MODEL"
    }

    override fun id(): Any = ID
    override fun content(): Any = ID
}