package com.lounah.yarealty.presentation.settings.filters.delegateadapters.onlyfromowners

import android.view.View
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_filter_only_from_owners.*

class OnlyFromOwnersDelegateAdapter(private val onOnlyFromOwnersClicked: View.OnClickListener)
    : KDelegateAdapter<OnlyFromOwnersViewModel>() {

    override fun onBind(item: OnlyFromOwnersViewModel, viewHolder: KViewHolder)= with(viewHolder) {
        switch_only_from_owners.setOnClickListener(onOnlyFromOwnersClicked)
        switch_only_from_owners.isChecked = item.isChecked
    }

    override fun isForViewType(items: List<*>, position: Int) = items[position] is OnlyFromOwnersViewModel

    override fun getLayoutId(): Int = R.layout.item_filter_only_from_owners
}