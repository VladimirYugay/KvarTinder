package com.lounah.yarealty.presentation.favourites.myfavorites

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.lounah.yarealty.R
import com.lounah.yarealty.domain.model.FlatViewObject
import kotlinx.android.synthetic.main.item_fav.view.*

class FavouritesAdapter(private val itemClickCallback: FavAdapterItemClickCallback)
    : RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {

    private val favouriteFlats = mutableListOf<FlatViewObject>()
    private val selectedFlats = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fav, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFlat = favouriteFlats[position]

            holder.itemView.fab_itemfav_makeCall.setOnClickListener {
                itemClickCallback.onMakeCallClicked(currentFlat)
            }

            holder.itemView.fab_itemfav_addToFav.setOnClickListener {
                itemClickCallback.onAddToFavClicked(currentFlat)
                removeItemAtPosition(position)
            }

            holder.itemView.imageview_item_fav_image.setOnClickListener {
                itemClickCallback.onItemClicked(currentFlat)
            }

            holder.itemView.fab_itemfav_share.setOnClickListener {
                itemClickCallback.shareOffer(currentFlat.id)
            }
        holder.bind(currentFlat)
    }

    override fun getItemCount(): Int {
        return favouriteFlats.size
    }

    internal fun updateDataSet(offers: List<FlatViewObject>) {
        favouriteFlats.clear()
        favouriteFlats.addAll(offers)
        notifyDataSetChanged()
    }

    internal fun setSelectedItemsIds(ids: List<String>) {
        selectedFlats.clear()
        selectedFlats.addAll(ids)
    }

    private fun removeItemAtPosition(position: Int) {
        if (position > favouriteFlats.size) {
            favouriteFlats.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    internal fun getItemAtPosition(position: Int) = favouriteFlats[position]

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(flat: FlatViewObject) {

            if (selectedFlats.contains(flat.id)){
                itemView.setBackgroundColor(itemView.resources.getColor(R.color.yellowSemiTransparent))

            }
            else {
                itemView.setBackgroundColor(itemView.resources.getColor(R.color.white))
            }

            Glide.with(itemView)
                    .load(flat.images[0])
                    .into(itemView.imageview_item_fav_image)
            itemView.textview_item_fav_price.text = flat.cost
            itemView.textview_item_fav_location.text = flat.address
            val roomsInfo = if (flat.roomsTotal != null) "${flat.roomsTotal} комнаты" else ""
            val areaInfo = if (flat.area != null) "${flat.area} кв.м" else ""
            val floorInfo = if ((flat.floors != null) and (flat.totalFloors != null))
                "этаж ${flat.floors}/${flat.totalFloors}" else ""
            val info = "$roomsInfo $areaInfo $floorInfo"
            itemView.textview_item_fav_description.text = info
        }
    }

    interface FavAdapterItemClickCallback {
        fun onItemClicked(item: FlatViewObject)
        fun onMakeCallClicked(item: FlatViewObject)
        fun onAddToFavClicked(item: FlatViewObject)
        fun shareOffer(offerId: String)
    }
}