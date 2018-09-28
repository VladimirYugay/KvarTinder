package com.lounah.yarealty.presentation.settings.filters.delegateadapters.timeperiod

import android.widget.RadioGroup
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_filter_timeperiod.*

class TimePeriodDelegateAdapter(private val onCheckedChangeListener: (RadioGroup, Int) -> Unit)
    : KDelegateAdapter<TimePeriodViewModel>() {

    override fun onBind(item: TimePeriodViewModel, viewHolder: KViewHolder)= with(viewHolder) {
        rg_searchfilters_renttime.setOnCheckedChangeListener(onCheckedChangeListener)
        rg_searchfilters_renttime.check(item.checkedId)
    }

    override fun isForViewType(items: List<*>, position: Int) = items[position] is TimePeriodViewModel

    override fun getLayoutId(): Int = R.layout.item_filter_timeperiod
}