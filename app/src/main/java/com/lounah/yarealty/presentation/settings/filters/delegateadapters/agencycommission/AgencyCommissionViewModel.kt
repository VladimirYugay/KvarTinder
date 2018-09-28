package com.lounah.yarealty.presentation.settings.filters.delegateadapters.agencycommission

import com.example.delegateadapter.delegate.diff.IComparableItem

class AgencyCommissionViewModel(var checked: Boolean) : IComparableItem {

    private companion object {
        private const val ID = "AGENCY_COMMISSION_VM"
    }

    override fun id(): Any = ID
    override fun content(): Any = ID
}