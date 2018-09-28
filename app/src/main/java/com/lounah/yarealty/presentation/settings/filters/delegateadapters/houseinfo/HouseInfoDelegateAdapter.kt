package com.lounah.yarealty.presentation.settings.filters.delegateadapters.houseinfo

import android.view.View
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_filter_houseinfo.*

class HouseInfoDelegateAdapter(private val onHouseAreaClicked: View.OnClickListener,
                               private val onRegionClicked: View.OnClickListener)
    : KDelegateAdapter<HouseInfoViewModel>() {

    override fun onBind(item: HouseInfoViewModel, viewHolder: KViewHolder)= with(viewHolder) {
        linearlayout_filter_houseinfo_housearea.setOnClickListener(onHouseAreaClicked)
        linearlayout_filter_houseinfo_region.setOnClickListener(onRegionClicked)
    }

    override fun isForViewType(items: List<*>, position: Int) = items[position] is HouseInfoViewModel

    override fun getLayoutId(): Int = R.layout.item_filter_houseinfo
}