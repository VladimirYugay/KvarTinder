package com.lounah.yarealty.presentation.settings.filters.delegateadapters.buildtype

import android.widget.RadioGroup
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_filter_buildingtype.*

class BuildTypeDelegateAdapter(private val onCheckedChangeListener: (RadioGroup, Int) -> Unit)
    : KDelegateAdapter<BuildTypeViewModel>() {

    override fun onBind(item: BuildTypeViewModel, viewHolder: KViewHolder)= with(viewHolder) {
        rg_searchfilters_flattypes.setOnCheckedChangeListener(onCheckedChangeListener)
        rg_searchfilters_flattypes.check(item.checkedId)
    }

    override fun isForViewType(items: List<*>, position: Int) = items[position] is BuildTypeViewModel

    override fun getLayoutId(): Int = R.layout.item_filter_buildingtype
}