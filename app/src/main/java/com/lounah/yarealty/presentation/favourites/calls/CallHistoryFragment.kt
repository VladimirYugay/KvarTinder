package com.lounah.yarealty.presentation.favourites.calls

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lounah.yarealty.R
import com.lounah.yarealty.data.entity.Call
import com.lounah.yarealty.presentation.common.BaseFragment
import com.lounah.yarealty.presentation.flat.Constants
import com.lounah.yarealty.presentation.flatdetails.FlatDetailsActivity
import kotlinx.android.synthetic.main.fragment_call_history.*
import javax.inject.Inject

class CallHistoryFragment : BaseFragment() {

    override val TAG = "CALL_HISTORY_FRAGMENT"

    override val layoutRes: Int = R.layout.fragment_call_history

    @Inject
    lateinit var viewModel: CallHistoryViewModel

    private var adapterItemClickListener: ItemClickListener? = ItemClickListener()

    private lateinit var adapter: CallsRvAdapter

    override fun initComponents() {
        adapter = CallsRvAdapter(adapterItemClickListener!!)
        recyclerview_callhistory.adapter = adapter
        recyclerview_callhistory.layoutManager = LinearLayoutManager(context)
        viewModel.getCallHistory()
    }

    override fun initComponentsCallbacks() {
        initViewModelObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapterItemClickListener = null
    }

    private fun initViewModelObservers() {
        viewModel.calls.observe(this, Observer {
            if (it!!.isEmpty()) processEmptyState()
            adapter.updateDataSet(it)
        })

        viewModel.loadingState.observe(this, Observer {
            processLoadingState(it!!)
        })
    }

    private fun processLoadingState(isLoading: Boolean) {
        if (isLoading) onShowLoadingView() else onHideLoadingView()
    }

    private fun onShowLoadingView() {
        container_fragmentcallhistory_pb.visibility = View.VISIBLE
        progressbar_fragmentcallhistory.visibility = View.VISIBLE
        textview_fragmentcallhistory_error.visibility = View.GONE
    }

    private fun onHideLoadingView() {
        if (adapter.itemCount != 0) {
            container_fragmentcallhistory_pb.visibility = View.GONE
            textview_fragmentcallhistory_error.visibility = View.GONE
            progressbar_fragmentcallhistory.visibility = View.GONE
        }
    }

    private fun processEmptyState() {
        container_fragmentcallhistory_pb.visibility = View.VISIBLE
        progressbar_fragmentcallhistory.visibility = View.GONE
        textview_fragmentcallhistory_error.visibility = View.VISIBLE
    }

    inner class ItemClickListener : CallsRvAdapter.CallAdapterItemClickCallback {
        override fun onItemClicked(item: Call) {
            val args = Bundle().apply {
                putString(Constants.INITIATOR_VIEW, TAG)
                putString(Constants.OFFER_ID, item.offerId)
            }
            loadFragmentCallback.startNewActivity(FlatDetailsActivity(), args)
            loadFragmentCallback.startNewActivity(FlatDetailsActivity(), args)
        }
    }
}