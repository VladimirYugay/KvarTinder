package com.lounah.yarealty.presentation.settings.filters.delegateadapters.ihc

import android.view.View
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_filter_ihc.*

class IhcDelegateAdapter(private val onIhcClicked: View.OnClickListener,
                         private val onHnpClicked: View.OnClickListener)
    : KDelegateAdapter<IhcViewModel>() {

    override fun onBind(item: IhcViewModel, viewHolder: KViewHolder)= with(viewHolder) {
        switch_ihc_ihc.setOnClickListener(onIhcClicked)
        switch_ihc_hnp.setOnClickListener(onHnpClicked)
        switch_ihc_ihc.isChecked = item.ihcClicked
        switch_ihc_hnp.isChecked = item.hnpClicked
    }

    override fun isForViewType(items: List<*>, position: Int) = items[position] is IhcViewModel

    override fun getLayoutId(): Int = R.layout.item_filter_ihc
}