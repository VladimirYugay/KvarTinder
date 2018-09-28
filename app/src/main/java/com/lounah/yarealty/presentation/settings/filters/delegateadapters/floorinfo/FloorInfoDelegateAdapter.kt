package com.lounah.yarealty.presentation.settings.filters.delegateadapters.floorinfo

import android.view.View
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_filter_floorinfo.*

class FloorInfoDelegateAdapter(private val onFloorClicked: View.OnClickListener,
                               private val onExcludeFirstClicked: View.OnClickListener)
    : KDelegateAdapter<FloorInfoViewModel>() {

    override fun onBind(item: FloorInfoViewModel, viewHolder: KViewHolder)= with(viewHolder) {
        linearlayout_filter_floorinfo_floor.setOnClickListener(onFloorClicked)
        switch_floorinfo_excludefirst.setOnClickListener(onExcludeFirstClicked)

        switch_floorinfo_excludefirst.isChecked = item.excludeFirst
    }

    override fun isForViewType(items: List<*>, position: Int) = items[position] is FloorInfoViewModel

    override fun getLayoutId(): Int = R.layout.item_filter_floorinfo
}