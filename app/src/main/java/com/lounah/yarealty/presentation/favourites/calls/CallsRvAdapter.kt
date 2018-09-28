package com.lounah.yarealty.presentation.favourites.calls

import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.lounah.yarealty.R
import com.lounah.yarealty.data.entity.Call
import kotlinx.android.synthetic.main.item_call.view.*

class CallsRvAdapter(private val itemClickCallback: CallAdapterItemClickCallback)
    : RecyclerView.Adapter<CallsRvAdapter.ViewHolder>() {

    private val calls = mutableListOf<Call>()

    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_call, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val call = calls[position]

        holder.itemView.layout_item_call.setOnClickListener {
            itemClickCallback.onItemClicked(call)
        }

        holder.bind(call)
    }

    override fun getItemCount(): Int {
        return calls.size
    }

    internal fun updateDataSet(calls: List<Call>) {
        this.calls.clear()
        this.calls.addAll(calls)
        notifyDataSetChanged()
    }

    private fun removeItemAtPosition(position: Int) {
        if (position > calls.size) {
            calls.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(call: Call) {
            Glide.with(itemView)
                    .load(call.offerImageURL)
                    .into(itemView.imageview_callhistoryitem_offer)

            itemView.textview_callhistoryitem_date.visibility = View.VISIBLE
            if (adapterPosition == 0) {
                itemView.textview_callhistoryitem_date.text = DateFormat.format("dd/MM", call.date)
            } else {
                if (call.date.day > calls[adapterPosition - 1].date.day) {
                    itemView.textview_callhistoryitem_date.text = DateFormat.format("dd/MM", call.date)
                } else {
                    itemView.textview_callhistoryitem_date.visibility = View.GONE
                }
            }
            itemView.textview_callhistoryitem_info.text = call.info

            itemView.textview_callhistoryitem_time.text = DateFormat.format("hh:mm", call.date)

            itemView.textview_callhistoryitem_phoneNumber.text = call.phoneNumber

            setAnimation(itemView, position)
        }
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, android.R.anim.fade_in)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    interface CallAdapterItemClickCallback {
        fun onItemClicked(item: Call)
    }

}