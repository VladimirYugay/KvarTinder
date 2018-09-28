package com.lounah.yarealty.presentation.settings.filters.delegateadapters.roominfo

import android.view.View
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_filter_roominfo.*

class RoomInfoDelegateAdapter(private val onRoomAreaClicked: View.OnClickListener,
                              private val onKitchenAreaClicked: View.OnClickListener)
    : KDelegateAdapter<RoomInfoViewModel>() {

    override fun onBind(item: RoomInfoViewModel, viewHolder: KViewHolder)= with(viewHolder) {
        linearlayout_filter_roominfo_room_area.setOnClickListener(onRoomAreaClicked)
        linearlayout_filter_roominfo_kitchen.setOnClickListener(onKitchenAreaClicked)
    }

    override fun isForViewType(items: List<*>, position: Int) = items[position] is RoomInfoViewModel

    override fun getLayoutId(): Int = R.layout.item_filter_roominfo
}