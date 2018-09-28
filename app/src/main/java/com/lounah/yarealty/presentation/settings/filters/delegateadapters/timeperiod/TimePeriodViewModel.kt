package com.lounah.yarealty.presentation.settings.filters.delegateadapters.timeperiod

import com.example.delegateadapter.delegate.diff.IComparableItem

class TimePeriodViewModel(var checkedId: Int) : IComparableItem {

    private companion object {
        private const val ID = "TIME_PERIOD_VIEW_MODEL"
    }

    override fun id(): Any = ID
    override fun content(): Any = ID
}