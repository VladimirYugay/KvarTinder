package com.lounah.yarealty.presentation.settings.filters.delegateadapters.agencycommission

import android.view.View
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_filter_agency_commission.*

class AgencyCommissionDelegateAdapter(private val onSwitchClicked: View.OnClickListener)
    : KDelegateAdapter<AgencyCommissionViewModel>() {

    override fun onBind(item: AgencyCommissionViewModel, viewHolder: KViewHolder)= with(viewHolder) {
        switch_agency_no_commission.setOnClickListener(onSwitchClicked)
        switch_agency_no_commission.isChecked = item.checked
    }

    override fun isForViewType(items: List<*>, position: Int) = items[position] is AgencyCommissionViewModel

    override fun getLayoutId(): Int = R.layout.item_filter_agency_commission
}