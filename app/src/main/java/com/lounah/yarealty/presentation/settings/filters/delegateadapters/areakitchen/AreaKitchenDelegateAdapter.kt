package com.lounah.yarealty.presentation.settings.filters.delegateadapters.areakitchen

import android.view.View
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_filter_area_kitchen.*

class AreaKitchenDelegateAdapter(private val onTotalAreaClicked: View.OnClickListener,
                                 private val onKitchenAreaClicked: View.OnClickListener)
    : KDelegateAdapter<AreaKitchenViewModel>() {

    override fun onBind(item: AreaKitchenViewModel, viewHolder: KViewHolder)= with(viewHolder) {
        linearlayout_filter_areakitchen_kitchen.setOnClickListener(onKitchenAreaClicked)
        linearlayout_filter_areakitchen_total_area.setOnClickListener(onTotalAreaClicked)
    }

    override fun isForViewType(items: List<*>, position: Int) = items[position] is AreaKitchenViewModel

    override fun getLayoutId(): Int = R.layout.item_filter_area_kitchen
}