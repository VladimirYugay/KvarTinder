package com.lounah.yarealty.presentation.settings.filters.delegateadapters.buildtype

import com.example.delegateadapter.delegate.diff.IComparableItem

class BuildTypeViewModel(var checkedId: Int) : IComparableItem {

    private companion object {
        private const val ID = "BUILD_TYPE_VIEW_MODEL"
    }

    override fun id(): Any = ID
    override fun content(): Any = ID
}