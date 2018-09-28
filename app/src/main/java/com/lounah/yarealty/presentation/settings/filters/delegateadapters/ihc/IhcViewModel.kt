package com.lounah.yarealty.presentation.settings.filters.delegateadapters.ihc

import com.example.delegateadapter.delegate.diff.IComparableItem

class IhcViewModel(var ihcClicked: Boolean, var hnpClicked: Boolean) : IComparableItem {

    private companion object {
        private const val ID = "IHC_VIEW_MODEL"
    }

    override fun id(): Any = ID
    override fun content(): Any = ID
}