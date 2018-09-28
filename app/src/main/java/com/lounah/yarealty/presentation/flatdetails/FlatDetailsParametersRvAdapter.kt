package com.lounah.yarealty.presentation.flatdetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lounah.yarealty.R
import kotlinx.android.synthetic.main.item_realty_details.view.*

class FlatDetailsParametersRvAdapter(val items: List<Pair<String, String>>)
    : RecyclerView.Adapter<FlatDetailsParametersRvAdapter.FlatDetailHolder>() {

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): FlatDetailHolder {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_realty_details, container, false)
        return FlatDetailHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FlatDetailHolder, position: Int) {
        holder.bind(items[position])
    }

    class FlatDetailHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pair: Pair<String, String>){
            itemView.textiview_itemrealtydetails_name.text = pair.first
            itemView.textiview_itemrealtydetails_value.text = pair.second
        }
    }
}