package com.lounah.yarealty.presentation.settings.filters.delegateadapters.buildinginfo

import android.view.View
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_filter_buildinginfo.*

class BuildingInfoDelegateAdapter(private val onYearClicked: View.OnClickListener)
    : KDelegateAdapter<BuildingInfoViewModel>() {

    override fun onBind(item: BuildingInfoViewModel, viewHolder: KViewHolder)= with(viewHolder) {
        linearlayout_filter_buildinginfo_year.setOnClickListener(onYearClicked)
    }

    override fun isForViewType(items: List<*>, position: Int) = items[position] is BuildingInfoViewModel

    override fun getLayoutId(): Int = R.layout.item_filter_buildinginfo
}