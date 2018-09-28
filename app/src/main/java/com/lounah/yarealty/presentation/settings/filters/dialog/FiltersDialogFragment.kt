package com.lounah.yarealty.presentation.settings.filters.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lounah.yarealty.R
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_filter_from_to.*
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.TextView


class FiltersDialogFragment : DialogFragment() {

    private lateinit var clickListener: OnClickListener
    private var initiatorViewId: Int = 0
    private lateinit var dialogTitle: String
    private var hint: String = ""
    private var valueFrom: Long = 0
    private var valueTo: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initiatorViewId = arguments?.getInt(INITIATOR_VIEW_ID)!!
        valueFrom = arguments?.getLong(CURRENT_VALUE_FROM)!!
        valueTo = arguments?.getLong(CURRENT_VALUE_TO)!!
        clickListener = parentFragment as OnClickListener
        setUpDialogFragment()
    }

    override fun onStart() {
        super.onStart()
        dialog.window!!
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_filter_from_to, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView_dialogfilter_title.text = dialogTitle
        if (til_dialogfilter_from.requestFocus()) {
            dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
        til_dialogfilter_from.hint = hint
        til_dialogfilter_to.hint = hint
        initViewListeners()
        if (valueFrom != 0L)
            til_dialogfilter_from.setText(valueFrom.toString(), TextView.BufferType.EDITABLE)
        if (valueTo != 0L)
            til_dialogfilter_to.setText(valueTo.toString(), TextView.BufferType.EDITABLE)
    }

    private fun setUpDialogFragment() {

        when (initiatorViewId) {
            R.id.linearlayout_filter_areakitchen_total_area -> {
                dialogTitle = resources.getString(R.string.kitchen_area)
                hint = resources.getString(R.string.square_metre)
            }
            R.id.linearlayout_filter_areakitchen_kitchen -> {
                dialogTitle = resources.getString(R.string.kitchen_area)
                hint = resources.getString(R.string.square_metre)
            }
            R.id.linearlayout_filter_buildinginfo_year -> {
                dialogTitle = resources.getString(R.string.build_year)
            }
            R.id.linearlayout_filter_buyflat_total_area -> {
                dialogTitle = resources.getString(R.string.total_area)
                hint = resources.getString(R.string.square_metre)
            }
            R.id.linearlayout_filter_buyflat_kitchen -> {
                dialogTitle = resources.getString(R.string.kitchen_area)
                hint = resources.getString(R.string.square_metre)
            }
            R.id.linearlayout_filter_flatinfo_room_area -> {
                dialogTitle = resources.getString(R.string.room_area)
                hint = resources.getString(R.string.square_metre)
            }
            R.id.linearlayout_filter_roomspec_room_area -> {
                dialogTitle = resources.getString(R.string.room_area)
                hint = resources.getString(R.string.square_metre)
            }
            R.id.linearlayout_filter_flatinfo_kitchen -> {
                dialogTitle = resources.getString(R.string.kitchen_area)
                hint = resources.getString(R.string.square_metre)
            }
            R.id.linearlayout_filter_floorinfo_floor -> {
                dialogTitle = resources.getString(R.string.floor)
            }
            R.id.linearlayout_filter_houseinfo_housearea -> {
                dialogTitle = resources.getString(R.string.house_area)
                hint = resources.getString(R.string.square_metre)
            }
            R.id.linearlayout_filter_houseinfo_region -> {
                dialogTitle = resources.getString(R.string.house)
                hint = resources.getString(R.string.weave)
            }
            R.id.linearlayout_filter_region -> {
                dialogTitle = resources.getString(R.string.region)
                hint = resources.getString(R.string.weave)
            }
            R.id.linearlayout_filter_roominfo_room_area -> {
                dialogTitle = resources.getString(R.string.room_area)
                hint = resources.getString(R.string.square_metre)
            }
            R.id.linearlayout_filter_roominfo_kitchen -> {
                dialogTitle = resources.getString(R.string.kitchen_area)
                hint = resources.getString(R.string.square_metre)
            }
            else -> {
                dialogTitle = resources.getString(R.string.price)
                hint = resources.getString(R.string.rubble_sign)
            }
        }
    }

    private fun initViewListeners() {
        button_dialogfilter_ok.setOnClickListener {
            //            if (
//                    initiatorViewId == R.id.linearlayout_filter_main_price ||
//                    (til_dialogfilter_from.text.isNotEmpty() && til_dialogfilter_to.text.isNotEmpty())
//            ) {
            try {
                val fieldLimit = 1_000_000_000L

                val valueFromAsString = til_dialogfilter_from.text.toString()
                val valueToAsString = til_dialogfilter_to.text.toString()

                val valueFrom = if (valueFromAsString.isBlank()) 0 else valueFromAsString.toLong()
                val valueTo = if (valueToAsString.isBlank()) 0 else valueToAsString.toLong()

                if (valueFrom == 0L && valueTo == 0L) {
                    clickListener.onOkClicked(0, 0, initiatorViewId)
                    return@setOnClickListener
                }

                if (valueFrom == 0L && valueTo > 0) {
                    clickListener.onOkClicked(0, valueTo, initiatorViewId)
                    return@setOnClickListener
                }
                if (valueFrom > 0 && valueTo == 0L) {
                    clickListener.onOkClicked(valueFrom, fieldLimit, initiatorViewId)
                    return@setOnClickListener
                }

                val valueMin = if (valueFrom < valueTo) valueFrom else valueTo
                val valueMax = if (valueFrom < valueTo) valueTo else valueFrom
                if (valueMin > fieldLimit || valueMax > fieldLimit) {
                    alertValueTooHigh()
                    return@setOnClickListener
                }

                clickListener.onOkClicked(valueMin, valueMax, initiatorViewId)
            } catch (nfe: NumberFormatException) {
                alertValueTooHigh()
            }
            // }
        }
        button_dialogfilter_cancel.setOnClickListener { clickListener.onCancelClicked() }

        til_dialogfilter_from.setOnTouchListener(OnTouchListener { v, event ->
            val drawableRightIndex = 2

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= til_dialogfilter_from.right - til_dialogfilter_from.compoundDrawables[drawableRightIndex].bounds.width()) {
                    til_dialogfilter_from.text.clear()
                    return@OnTouchListener true
                }
            }
            false
        })

        til_dialogfilter_to.setOnTouchListener(OnTouchListener { v, event ->
            val drawableRightIndex = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= til_dialogfilter_to.right - til_dialogfilter_to.compoundDrawables[drawableRightIndex].bounds.width()) {
                    til_dialogfilter_to.text.clear()
                    return@OnTouchListener true
                }
            }
            false
        })

    }

    private fun alertValueTooHigh() {
        Toast.makeText(context, R.string.price_is_too_high, Toast.LENGTH_LONG).show()
    }

    interface OnClickListener {
        fun onCancelClicked()
        fun onOkClicked(resultFrom: Long, resultTo: Long, initiatorViewId: Int)
    }

    companion object {
        private val INITIATOR_VIEW_ID = "INITIATOR_VIEW_ID"
        private val CURRENT_VALUE_FROM = "CURRENT_VALUE_FROM"
        private val CURRENT_VALUE_TO = "CURRENT_VALUE_TO"
        fun newInstance(initiatorViewId: Int, currentPriceFrom: Long, currentPriceTo: Long): FiltersDialogFragment {
            val frag = FiltersDialogFragment()
            val args = Bundle()
            with(args) {
                putInt(INITIATOR_VIEW_ID, initiatorViewId)
                putLong(CURRENT_VALUE_FROM, currentPriceFrom)
                putLong(CURRENT_VALUE_TO, currentPriceTo)
            }
            frag.arguments = args
            return frag
        }
    }

}
