package com.lounah.yarealty.presentation.settings.filters.delegateadapters.onlyphotoandowners

import android.view.View
import com.example.delegateadapter.delegate.KDelegateAdapter
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_filter_onlyphoto_and_owners.*

class OnlyPhotoAndOwnersDelegateAdapter(private val onOnlyFromOwnersClicked: View.OnClickListener,
                                        private val onOnlyWithPhotoClicked: View.OnClickListener)
    : KDelegateAdapter<OnlyPhotoAndOwnersViewModel>() {

    override fun onBind(item: OnlyPhotoAndOwnersViewModel, viewHolder: KViewHolder)= with(viewHolder) {
        switch_only_from_owners.setOnClickListener(onOnlyFromOwnersClicked)
        switch_only_with_photo.setOnClickListener(onOnlyWithPhotoClicked)
        switch_only_from_owners.isChecked = item.isOnlyFromOwnersClicked
        switch_only_with_photo.isChecked = item.isOnlyWithPhotoClicked
    }

    override fun isForViewType(items: List<*>, position: Int) = items[position] is OnlyPhotoAndOwnersViewModel

    override fun getLayoutId(): Int = R.layout.item_filter_onlyphoto_and_owners
}