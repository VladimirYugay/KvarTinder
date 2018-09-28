package com.lounah.yarealty.presentation.settings.filters.delegateadapters.buyflat

import android.view.View
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_filter_buyflat.*

class BuyFlatDelegateAdapter(private val onTotalAreaClicked: View.OnClickListener,
                             private val onKitchenAreaClicked: View.OnClickListener,
                             private val onFreePlanClicked: View.OnClickListener)
    : KDelegateAdapter<BuyFlatViewModel>() {

    override fun onBind(item: BuyFlatViewModel, viewHolder: KViewHolder)= with(viewHolder) {
        linearlayout_filter_buyflat_total_area.setOnClickListener(onTotalAreaClicked)
        linearlayout_filter_buyflat_kitchen.setOnClickListener(onKitchenAreaClicked)
        switch_buyflat.setOnClickListener(onFreePlanClicked)

        switch_buyflat.isChecked = item.freePlan
    }

    override fun isForViewType(items: List<*>, position: Int) = items[position] is BuyFlatViewModel

    override fun getLayoutId(): Int = R.layout.item_filter_buyflat
}