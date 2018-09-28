package com.lounah.yarealty.presentation.sharedoffers.swipeableadapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.lounah.yarealty.R

abstract class SwipeCallback(context: Context) : ItemTouchHelper.SimpleCallback(2, ItemTouchHelper.ACTION_STATE_SWIPE) {

    private val dislikeIcon = ContextCompat.getDrawable(context, R.drawable.ic_dislike_64dp)
    private val likeIcon = ContextCompat.getDrawable(context, R.drawable.ic_like_144dp)
    private val intrinsicWidth = likeIcon!!.intrinsicWidth
    private val intrinsicHeight = likeIcon!!.intrinsicHeight
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    override fun getMovementFlags(recyclerView: RecyclerView,
                                  viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = 0
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(
            c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
            dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        val iconBoundsTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val iconMargin = (itemHeight - intrinsicHeight) / 2
        val iconBoundsLeft = itemView.right - iconMargin - intrinsicWidth
        val iconBoundsRight = itemView.right - iconMargin
        val iconBoundsBottom = iconBoundsTop + intrinsicHeight

        if (dX < 0) {
            dislikeIcon!!.setBounds(iconBoundsLeft, iconBoundsTop, iconBoundsRight, iconBoundsBottom)
            dislikeIcon.draw(c)
        } else {
            likeIcon!!.setBounds(iconBoundsLeft, iconBoundsTop, iconBoundsRight, iconBoundsBottom)
            likeIcon.draw(c)
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }
}