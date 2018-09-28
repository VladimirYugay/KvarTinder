package com.lounah.yarealty.presentation.settings.filters.delegateadapters.onlyfromowners

import com.example.delegateadapter.delegate.diff.IComparableItem

class OnlyFromOwnersViewModel(var isChecked: Boolean) : IComparableItem {

    private companion object {
        private const val ID = "ONLY_FROM_OWNERS_VIEW_MODEL"
    }

    override fun id(): Any = ID
    override fun content(): Any = ID
}