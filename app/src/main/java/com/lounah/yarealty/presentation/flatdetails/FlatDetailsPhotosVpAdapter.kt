package com.lounah.yarealty.presentation.flatdetails

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_zoomedflat_pager.view.*

class FlatDetailsPhotosVpAdapter : PagerAdapter() {

    private var photosURLs = mutableListOf<String>()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_zoomedflat_pager, container, false)
        Glide.with(container.context)
                .load(photosURLs[position])
                .apply(RequestOptions().placeholder(R.drawable.all_image_placeholder))
                .into(view.imageview_itemzoomedflat_image)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return photosURLs.size
    }

    internal fun updateDataSet(photosURLs: List<String>) {
        this.photosURLs.clear()
        this.photosURLs.addAll(photosURLs)
        notifyDataSetChanged()
    }
}
