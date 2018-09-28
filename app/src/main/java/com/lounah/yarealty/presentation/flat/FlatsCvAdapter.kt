package com.lounah.yarealty.presentation.flat

import android.content.Context
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.lounah.yarealty.R
import com.lounah.yarealty.domain.model.FlatViewObject
import com.lounah.yarealty.presentation.views.pageindicator.LinePageIndicator


class FlatsCvAdapter(context: Context?, resource: Int,
                     private var loadingCallback: LoadingCallback,
                     private val onDetailsTappedCallback: OnDetailsTappedCallback)
    : ArrayAdapter<FlatViewObject>(context, resource) {

    companion object {
        const val PAGINATE_THRESHOLD = 0
        const val PHOTOS_ON_MAIN_PAGE_COUNT_LIMIT = 3
    }

    private var currentImageIndex: Int = 0
    lateinit var currentItem: FlatViewObject
    private var flats = mutableListOf<FlatViewObject>()
    var shouldPaginate = false

    override fun getView(position: Int, contentView: View?, parent: ViewGroup): View {
        var cv = contentView
        val holder: ViewHolder
        if (cv == null) {
            val inflater = LayoutInflater.from(context)
            cv = inflater.inflate(R.layout.item_realty, parent, false)
            holder = ViewHolder(cv!!)
            cv.tag = holder
        } else {
            holder = cv.tag as ViewHolder
        }

        val currentFlat = getItem(position)!!
        val imageCount = if (currentFlat.images.size < PHOTOS_ON_MAIN_PAGE_COUNT_LIMIT)
            currentFlat.images.size else PHOTOS_ON_MAIN_PAGE_COUNT_LIMIT

        if (position != 0)
            currentItem = getItem(position - 1)

        var imageUrl = currentFlat.images[currentImageIndex]

        holder.tapHandlerLeft.setOnClickListener {
            holder.indicator.onPreviousPage()
            if (currentImageIndex == 0) {
                currentImageIndex = imageCount - 1
            } else {
                currentImageIndex--
            }

            imageUrl = currentFlat.images[currentImageIndex]

            Glide.with(holder.icon.context)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.icon)
        }

        holder.tapHandlerRight.setOnClickListener {
            holder.indicator.onNextPage()
            if (currentImageIndex == imageCount - 1) {
                resetCurrentIndex()
            } else {
                currentImageIndex++
            }

            imageUrl = currentFlat.images[currentImageIndex]

            Glide.with(holder.icon.context)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.icon)
        }

        Glide.with(holder.icon.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.icon)

        holder.address.text = currentFlat.address
        holder.indicator.setItemsCount(imageCount)
        holder.price.text = currentFlat.cost

        holder.info.setOnClickListener {
            onDetailsTappedCallback.onDetailsTapped(currentFlat)
        }

        if (count == 1) {
            shouldPaginate = true
            loadingCallback.loadMore()
        }
        return cv
    }

    internal fun resetCurrentIndex() {
        currentImageIndex = 0
    }

    internal fun updateDataSet(offers: List<FlatViewObject>) {
        flats = offers as MutableList<FlatViewObject>
        shouldPaginate = false
        clear()
        addAll(offers)
        notifyDataSetChanged()
    }

    internal fun addItemToTop(item: FlatViewObject) {
        val newList = mutableListOf(item) + flats
        updateDataSet(newList)
    }

    override fun remove(`object`: FlatViewObject?) {
        super.remove(`object`)
        flats.remove(`object`)
    }

    internal fun currentItemInitialized() = ::currentItem.isInitialized

    inner class ViewHolder(view: View) {
        var tapHandlerLeft: View = view.findViewById(R.id.item_realty_view_tapHandler_left)
        var tapHandlerRight: View = view.findViewById(R.id.item_realty_view_tapHandler_right)
        var container: CardView = view.findViewById(R.id.item_realty_container)
        var indicator: LinePageIndicator = view.findViewById(R.id.tabindicator_realtyitem)
        var icon: ImageView = view.findViewById(R.id.imageview_itemrealty_flat)
        var price: TextView = view.findViewById(R.id.textview_itemrealty_price)
        var address: TextView = view.findViewById(R.id.textview_itemrealty_businessinfo)
        var info: View = view.findViewById(R.id.view_itemrealty_info)
    }

    interface OnDetailsTappedCallback {
        fun onDetailsTapped(currentItem: FlatViewObject)
    }
}
