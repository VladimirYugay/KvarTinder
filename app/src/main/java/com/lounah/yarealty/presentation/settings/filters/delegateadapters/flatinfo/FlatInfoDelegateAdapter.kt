package com.lounah.yarealty.presentation.settings.filters.delegateadapters.flatinfo

import android.view.View
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_filter_flatinfo.*

class FlatInfoDelegateAdapter(private val onRoomAreaClicked: View.OnClickListener,
                              private val onKitchenAreaClicked: View.OnClickListener)
    : KDelegateAdapter<FlatInfoViewModel>() {

    override fun onBind(item: FlatInfoViewModel, viewHolder: KViewHolder)= with(viewHolder) {
        linearlayout_filter_flatinfo_room_area.setOnClickListener(onRoomAreaClicked)
        linearlayout_filter_flatinfo_kitchen.setOnClickListener(onKitchenAreaClicked)
    }

    override fun isForViewType(items: List<*>, position: Int) = items[position] is FlatInfoViewModel

    override fun getLayoutId(): Int = R.layout.item_filter_flatinfo
}