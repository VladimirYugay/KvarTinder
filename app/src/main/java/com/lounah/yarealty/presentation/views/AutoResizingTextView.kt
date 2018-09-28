package com.lounah.yarealty.presentation.views

import android.content.Context
import android.text.Layout.Alignment
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView


class AutoResizingTextView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : TextView(context, attrs, defStyle) {

    private var mTextResizeListener: OnTextResizeListener? = null
    private var mNeedsResize = false
    private var mTextSize: Float = 0f
    private var mMaxTextSize = 0f

    var minTextSize = MIN_TEXT_SIZE
        set(minTextSize) {
            field = minTextSize
            requestLayout()
            invalidate()
        }

    private var mSpacingMult = 1.0f
    private var mSpacingAdd = 0.0f

    var addEllipsis = true

    var maxTextSize: Float
        get() = mMaxTextSize
        set(maxTextSize) {
            mMaxTextSize = maxTextSize
            requestLayout()
            invalidate()
        }

    interface OnTextResizeListener {
        fun onTextResize(textView: TextView, oldSize: Float, newSize: Float)
    }

    init {
        mTextSize = textSize
    }

    override fun onTextChanged(text: CharSequence, start: Int, before: Int, after: Int) {
        mNeedsResize = true
        resetTextSize()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (w != oldw || h != oldh) {
            mNeedsResize = true
        }
    }

    fun setOnResizeListener(listener: OnTextResizeListener) {
        mTextResizeListener = listener
    }

    override fun setTextSize(size: Float) {
        super.setTextSize(size)
        mTextSize = textSize
    }

    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size)
        mTextSize = textSize
    }

    override fun setLineSpacing(add: Float, mult: Float) {
        super.setLineSpacing(add, mult)
        mSpacingMult = mult
        mSpacingAdd = add
    }

    fun resetTextSize() {
        if (mTextSize > 0) {
            super.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
            mMaxTextSize = mTextSize
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (changed || mNeedsResize) {
            val widthLimit = right - left - compoundPaddingLeft - compoundPaddingRight
            val heightLimit = bottom - top - compoundPaddingBottom - compoundPaddingTop
            resizeText(widthLimit, heightLimit)
        }
        super.onLayout(changed, left, top, right, bottom)
    }

    fun resizeText() {
        val heightLimit = height - paddingBottom - paddingTop
        val widthLimit = width - paddingLeft - paddingRight
        resizeText(widthLimit, heightLimit)
    }

    fun resizeText(width: Int, height: Int) {
        var text: CharSequence? = text
        if (text == null || text.length == 0 || height <= 0 || width <= 0 || mTextSize == 0f) {
            return
        }

        if (transformationMethod != null) {
            text = transformationMethod.getTransformation(text, this)
        }

        val textPaint = paint
        val oldTextSize = textPaint.textSize
        var targetTextSize = if (mMaxTextSize > 0) Math.min(mTextSize, mMaxTextSize) else mTextSize

        var textHeight = getTextHeight(text!!, textPaint, width, targetTextSize)

        while (textHeight > height && targetTextSize > minTextSize) {
            targetTextSize = Math.max(targetTextSize - 2, minTextSize)
            textHeight = getTextHeight(text!!, textPaint, width, targetTextSize)
        }

        if (addEllipsis && targetTextSize == minTextSize && textHeight > height) {
            val paint = TextPaint(textPaint)
            val layout = StaticLayout(text, paint, width, Alignment.ALIGN_NORMAL, mSpacingMult, mSpacingAdd, false)
            if (layout.lineCount > 0) {
                val lastLine = layout.getLineForVertical(height) - 1
                if (lastLine < 0) {
                    setText("")
                } else {
                    val start = layout.getLineStart(lastLine)
                    var end = layout.getLineEnd(lastLine)
                    var lineWidth = layout.getLineWidth(lastLine)
                    val ellipseWidth = textPaint.measureText(mEllipsis)

                    while (width < lineWidth + ellipseWidth) {
                        lineWidth = textPaint.measureText(text!!.subSequence(start, --end + 1).toString())
                    }
                    setText(text!!.subSequence(0, end).toString() + mEllipsis)
                }
            }
        }

        setTextSize(TypedValue.COMPLEX_UNIT_PX, targetTextSize)
        setLineSpacing(mSpacingAdd, mSpacingMult)

        if (mTextResizeListener != null) {
            mTextResizeListener!!.onTextResize(this, oldTextSize, targetTextSize)
        }

        mNeedsResize = false
    }

    private fun getTextHeight(source: CharSequence, paint: TextPaint, width: Int, textSize: Float): Int {
        val paintCopy = TextPaint(paint)
        paintCopy.textSize = textSize
        val layout = StaticLayout(source, paintCopy, width, Alignment.ALIGN_NORMAL, mSpacingMult, mSpacingAdd, true)
        return layout.height
    }

    companion object {
        val MIN_TEXT_SIZE = 20f
        private val mEllipsis = "..."
    }

}