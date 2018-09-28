package com.lounah.yarealty.presentation.settings.filters.delegateadapters.roomfacilities

import android.view.View
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_filter_room_facilities.*

class RoomFacilitiesDelegateAdapter(private val onHasFridgeClicked: View.OnClickListener,
                                    private val onHasDishwasherClicked: View.OnClickListener,
                                    private val onHasAirConditioningClicked: View.OnClickListener,
                                    private val onHasWasherClicked: View.OnClickListener,
                                    private val onHasFurnitureClicked: View.OnClickListener,
                                    private val onPetsAllowedClicked: View.OnClickListener,
                                    private val onKidsAllowedClicked: View.OnClickListener)
    : KDelegateAdapter<RoomFacilitiesViewModel>() {

    override fun onBind(item: RoomFacilitiesViewModel, viewHolder: KViewHolder)= with(viewHolder) {
        switch_roomfacilities_fridge.setOnClickListener(onHasFridgeClicked)
        switch_roomfacilities_fridge.isChecked = item.hasFridgeChecked

        switch_roomfacilities_dishwasher.setOnClickListener(onHasDishwasherClicked)
        switch_roomfacilities_dishwasher.isChecked = item.hasDishWasherChecked

        switch_roomfacilities_air_conditioning.setOnClickListener(onHasAirConditioningClicked)
        switch_roomfacilities_air_conditioning.isChecked = item.hasAirConditioningChecked

        switch_roomfacilities_washer.setOnClickListener(onHasWasherClicked)
        switch_roomfacilities_washer.isChecked = item.hasWasherChecked

        switch_roomfacilities_furniture.setOnClickListener(onHasFurnitureClicked)
        switch_roomfacilities_furniture.isChecked = item.hasFurnitureChecked

        switch_roomfacilities_with_pets.setOnClickListener(onPetsAllowedClicked)
        switch_roomfacilities_with_pets.isChecked = item.petsAllowedChecked

        switch_roomfacilities_with_kids.setOnClickListener(onKidsAllowedClicked)
        switch_roomfacilities_with_kids.isChecked = item.kidsAllowedChecked
    }

    override fun isForViewType(items: List<*>, position: Int) = items[position] is RoomFacilitiesViewModel

    override fun getLayoutId(): Int = R.layout.item_filter_room_facilities
}