package com.lounah.yarealty.presentation.settings.filters.delegateadapters.region

import android.view.View
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_filter_region.*

class RegionDelegateAdapter(private val onRegionAreaClicked: View.OnClickListener)
    : KDelegateAdapter<RegionViewModel>() {

    override fun onBind(item: RegionViewModel, viewHolder: KViewHolder)= with(viewHolder) {
        linearlayout_filter_region.setOnClickListener(onRegionAreaClicked)
    }

    override fun isForViewType(items: List<*>, position: Int) = items[position] is RegionViewModel

    override fun getLayoutId(): Int = R.layout.item_filter_region
}