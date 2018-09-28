package com.lounah.yarealty.presentation.settings.filters.delegateadapters.roomspec

import android.view.View
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.lounah.yarealty.R
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.houseinfo.HouseInfoViewModel
import kotlinx.android.synthetic.main.item_filter_room_spec.*

class RoomSpecDelegateAdapter(private val onRoomAreaClicked: View.OnClickListener)
    : KDelegateAdapter<RoomSpecViewModel>() {

    override fun onBind(item: RoomSpecViewModel, viewHolder: KViewHolder)= with(viewHolder) {
        linearlayout_filter_roomspec_room_area.setOnClickListener(onRoomAreaClicked)
    }

    override fun isForViewType(items: List<*>, position: Int) = items[position] is RoomSpecViewModel

    override fun getLayoutId(): Int = R.layout.item_filter_room_spec
}