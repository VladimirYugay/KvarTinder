package com.lounah.yarealty.presentation.sharedoffers

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.lounah.yarealty.R
import com.lounah.yarealty.data.entity.Call
import com.lounah.yarealty.domain.model.FlatViewObject
import com.lounah.yarealty.presentation.common.BaseActivity
import com.lounah.yarealty.presentation.flat.Constants
import com.lounah.yarealty.presentation.sharedoffers.swipeableadapter.SharedOffersAdapter
import com.lounah.yarealty.presentation.sharedoffers.swipeableadapter.SwipeCallback
import com.lounah.yarealty.utils.Utils
import kotlinx.android.synthetic.main.activity_offers_fromdeeplink.*
import java.util.*
import javax.inject.Inject


class SharedOffersActivity : BaseActivity() {

    private lateinit var offerIds: List<String>
    private lateinit var offersAdapter: SharedOffersAdapter

    @Inject
    lateinit var viewModel: SharedOffersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offers_fromdeeplink)
        val intent = intent
        offerIds = Utils.parseDeeplinkOffers(intent.dataString)
        initUI()
    }

    private fun initUI() {
        toolbar_activity_deeplinks.title = resources.getString(R.string.shared_offers)
        initRecyclerView()
        viewModel.getSharedOffers(offerIds)
        observeViewModel()
    }

    private fun initRecyclerView() {
        offersAdapter = SharedOffersAdapter(ItemClickCallback())
        recyclerview_sharedoffers.adapter = offersAdapter
        recyclerview_sharedoffers.layoutManager = LinearLayoutManager(this)
        val swipeHandler = object : SwipeCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerview_sharedoffers.adapter as SharedOffersAdapter
                when (direction) {
                    16 -> {
                        if (adapter.isCurrentItemInitialized()) {
                            val itemToRemove = offersAdapter.currentItem
                            adapter.removeAt(viewHolder.adapterPosition)
                            dislikeFlat(itemToRemove, adapter)
                        }
                    }
                    32 -> {
                        if (adapter.isCurrentItemInitialized()) {
                            val itemToRemove = offersAdapter.currentItem
                            adapter.removeAt(viewHolder.adapterPosition)
                            likeFlat(itemToRemove, adapter)
                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerview_sharedoffers)
    }

    private fun dislikeFlat(offer: FlatViewObject, adapter: SharedOffersAdapter) {
        viewModel.dislikeFlat(offer.refToOffer)
        if (adapter.itemCount == 0) finish()
    }

    private fun likeFlat(offer: FlatViewObject, adapter: SharedOffersAdapter) {
        viewModel.markFlatAsFavourite(offer.refToOffer)
        if (adapter.itemCount == 0) finish()
    }

    private fun observeViewModel() {
        viewModel.loadingState.observe(this, Observer {

        })

        viewModel.favouriteFlats.observe(this, Observer {
            it?.let { offersAdapter.updateDataSet(it) }
            viewModel.unsubscribeFromFlats()
        })
    }

    private fun makeCall(item: FlatViewObject) {
        val roomsInfo = if (item.roomsTotal != null) "${item.roomsTotal} комнаты" else ""
        val areaInfo = if (item.area != null) "${item.area} кв.м" else ""
        val floorInfo = if ((item.floors != null) and (item.totalFloors != null))
            "этаж ${item.floors}/${item.totalFloors}" else ""
        val info = "$roomsInfo $areaInfo $floorInfo"
        viewModel.addNewCall(Call(item.id, item.images[0], item.phoneNumber!!, info, Date()))

        val call = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", item.phoneNumber, null))
        call.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(call)
    }


    inner class ItemClickCallback : SharedOffersAdapter.SharedOffersAdapterItemClickCallback {
        override fun onAddToFavClicked(item: FlatViewObject, position: Int) {
            likeFlat(item, offersAdapter)
            offersAdapter.removeAt(position)
        }

        override fun onDislikeClicked(item: FlatViewObject, position: Int) {
            dislikeFlat(item, offersAdapter)
            offersAdapter.removeAt(position)
        }

        override fun onCallClicked(item: FlatViewObject) {
            makeCall(item)
        }

        override fun onItemClicked(item: FlatViewObject) {
        }

    }
}