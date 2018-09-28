package com.lounah.yarealty.presentation.sharedoffers.swipeableadapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.lounah.yarealty.R
import com.lounah.yarealty.domain.model.FlatViewObject
import com.lounah.yarealty.presentation.flat.FlatsCvAdapter
import kotlinx.android.synthetic.main.item_shared_offer.view.*

class SharedOffersAdapter(private val itemClickCallback: SharedOffersAdapterItemClickCallback)
    : RecyclerView.Adapter<SharedOffersAdapter.ViewHolder>() {

    private val favouriteFlats = mutableListOf<FlatViewObject>()

    private var lastPosition = -1

    private var currentImageIndex: Int = 0

    lateinit var currentItem: FlatViewObject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shared_offer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFlat = favouriteFlats[position]
        currentItem = currentFlat

        holder.itemView.imageview_item_sharedoffer_image.setOnClickListener {
            itemClickCallback.onItemClicked(currentFlat)
        }

        holder.bind(currentFlat)
    }

    override fun getItemCount(): Int {
        return favouriteFlats.size
    }

    fun removeAt(position: Int) {
        favouriteFlats.removeAt(position)
        notifyItemRemoved(position)
    }

    fun remove(offer: FlatViewObject) {
        removeAt(favouriteFlats.indexOf(offer))
    }

    internal fun updateDataSet(offers: List<FlatViewObject>) {
        favouriteFlats.clear()
        favouriteFlats.addAll(offers)
        notifyDataSetChanged()
    }

    private fun removeItemAtPosition(position: Int) {
        if (position > favouriteFlats.size) {
            favouriteFlats.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    internal fun isCurrentItemInitialized() = ::currentItem.isInitialized

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(flat: FlatViewObject) {

            var imageUrl = flat.images[currentImageIndex]

            itemView.item_sharedoffer_view_tapHandler_left.setOnClickListener {
                itemView.tabindicator_item_sharedoffer.onPreviousPage()
                if (currentImageIndex == 0) {
                    currentImageIndex = FlatsCvAdapter.PHOTOS_ON_MAIN_PAGE_COUNT_LIMIT - 1
                } else {
                    currentImageIndex--
                }

                imageUrl = flat.images[currentImageIndex]
                loadImage(itemView, imageUrl)
            }

            itemView.item_sharedoffer_view_tapHandler_right.setOnClickListener {
                itemView.tabindicator_item_sharedoffer.onNextPage()
                if (currentImageIndex == FlatsCvAdapter.PHOTOS_ON_MAIN_PAGE_COUNT_LIMIT - 1) {
                    resetCurrentIndex()
                } else {
                    currentImageIndex++
                }

                imageUrl = flat.images[currentImageIndex]
                loadImage(itemView, imageUrl)
            }

            loadImage(itemView, imageUrl)

            itemView.textview_item_sharedoffer_location.text = flat.address

            val roomsInfo = if (flat.roomsTotal != null) "${flat.roomsTotal} комнаты" else ""
            val areaInfo = if (flat.area != null) "${flat.area} кв.м" else ""
            val floorInfo = if ((flat.floors != null) and (flat.totalFloors != null))
                "этаж ${flat.floors}/${flat.totalFloors}" else ""
            val info = "$roomsInfo $areaInfo $floorInfo"
            itemView.textview_item_sharedoffer_description.text = info

            itemView.tabindicator_item_sharedoffer.setItemsCount(FlatsCvAdapter.PHOTOS_ON_MAIN_PAGE_COUNT_LIMIT)
            itemView.textview_item_sharedoffer_price.text = flat.cost

            itemView.fab_item_sharedoffer_makeCall.setOnClickListener {
                itemClickCallback.onCallClicked(flat)
            }

            itemView.imageview_item_sharedoffer_like.setOnClickListener {
                itemClickCallback.onAddToFavClicked(flat, adapterPosition)
            }

            itemView.imageview_item_sharedoffer_dislike.setOnClickListener {
                itemClickCallback.onDislikeClicked(flat, adapterPosition)
            }

            setAnimation(itemView, position)
        }
    }

    internal fun resetCurrentIndex() {
        currentImageIndex = 0
    }

    private fun loadImage(itemView: View, imageURL: String) {
        Glide.with(itemView.context)
                .load(imageURL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(itemView.imageview_item_sharedoffer_image)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, android.R.anim.fade_in)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    interface SharedOffersAdapterItemClickCallback {
        fun onItemClicked(item: FlatViewObject)
        fun onCallClicked(item: FlatViewObject)
        fun onAddToFavClicked(item: FlatViewObject, position: Int)
        fun onDislikeClicked(item: FlatViewObject, position: Int)
    }

}
