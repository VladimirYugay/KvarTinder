package com.lounah.yarealty.presentation.favourites.myfavorites

import android.arch.lifecycle.Observer
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lounah.yarealty.R
import com.lounah.yarealty.data.entity.Call
import com.lounah.yarealty.domain.model.FlatViewObject
import com.lounah.yarealty.presentation.common.BaseFragment
import com.lounah.yarealty.presentation.flat.Constants
import com.lounah.yarealty.presentation.flatdetails.FlatDetailsActivity
import java.util.*
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_my_favs.*




class MyFavouritesFragment : BaseFragment() {

    override val TAG = "MY_FAVS_FRAGMENT"

    override val layoutRes = R.layout.fragment_my_favs

    @Inject
    lateinit var viewModel: FavouritesViewModel

    private var adapterItemClickListener: ItemClickListener? = ItemClickListener()

    private lateinit var adapter: FavouritesAdapter

    private var isInMultiSelectionMode = false
    private val selectedItemsIds = mutableListOf<String>()

    override fun initComponents() {
        button_favs_share.hide()
        button_favs_remove.hide()
        adapter = FavouritesAdapter(adapterItemClickListener!!)
        recycler_favfragment_favs.adapter = adapter
        recycler_favfragment_favs.layoutManager = LinearLayoutManager(context)

        recycler_favfragment_favs.addOnItemTouchListener(MutliSelectableRecyclerItemClickListener(context!!, recycler_favfragment_favs,
                object : MutliSelectableRecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                if (isInMultiSelectionMode) {
                    multiSelect(position)
                }
            }
            override fun onItemLongClick(view: View?, position: Int) {
                if (!isInMultiSelectionMode) {
                    selectedItemsIds.clear()
                    isInMultiSelectionMode = true
                    renderSelectionStateViews(1)
                }
                multiSelect(position)
            }
        }))

        viewModel.getFavouriteFlats()
    }

    private fun multiSelect(position: Int) {
        val selectedFlat = adapter.getItemAtPosition(position)
            if (selectedItemsIds.contains(selectedFlat.id)) {
                selectedItemsIds.remove(selectedFlat.id)
                adapter.notifyDataSetChanged()
            }
            else {
                selectedItemsIds.add(selectedFlat.id)
                adapter.notifyDataSetChanged()
            }

            if (selectedItemsIds.size > 0)
                renderSelectionStateViews(selectedItemsIds.size)
            else {
                isInMultiSelectionMode = false
                hideSelectionStateViews()
            }
            adapter.setSelectedItemsIds(selectedItemsIds)
    }

    override fun initComponentsCallbacks() {
        initViewModelObservers()
        initFabListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapterItemClickListener = null
    }

    private fun initViewModelObservers() {
        viewModel.favouriteFlats.observe(this, Observer {
            if (it!!.isEmpty()) processEmptyState()
            adapter.updateDataSet(it)
        })

        viewModel.loadingState.observe(this, Observer {
            processLoadingState(it!!)
        })

        viewModel.successDislikeActionResult.observe(this, Observer {
            showToast(R.string.successfully_deleted)
        })
    }

    private fun initFabListeners() {
        button_favs_share.setOnClickListener {
            var deeplinkShareUrl = ""

            selectedItemsIds.forEach { deeplinkShareUrl += "$it&" }

            shareOffer(deeplinkShareUrl.substring(0, deeplinkShareUrl.length - 1))
        }

        button_favs_remove.setOnClickListener {
            viewModel.dislikeFlatsById(selectedItemsIds)
            hideSelectionStateViews()
            isInMultiSelectionMode = false

        }

        button_fragmentfavs_clear.setOnClickListener {
            selectedItemsIds.clear()
            adapter.setSelectedItemsIds(selectedItemsIds)
            adapter.notifyDataSetChanged()
            hideSelectionStateViews()
            isInMultiSelectionMode = false
        }
    }

    private fun processLoadingState(isLoading: Boolean) {
        if (isLoading) onShowLoadingView() else onHideLoadingView()
    }

    private fun onShowLoadingView() {
        container_fragmentfavs_pb.visibility = View.VISIBLE
        progressbar_fragmentfavs.visibility = View.VISIBLE
        textview_fragmentfavs_error.visibility = View.GONE
    }

    private fun onHideLoadingView() {
        if (adapter.itemCount != 0) {
            container_fragmentfavs_pb.visibility = View.GONE
            textview_fragmentfavs_error.visibility = View.GONE
            progressbar_fragmentfavs.visibility = View.GONE
        }
    }

    private fun processEmptyState() {
        container_fragmentfavs_pb.visibility = View.VISIBLE
        progressbar_fragmentfavs.visibility = View.GONE
        textview_fragmentfavs_error.visibility = View.VISIBLE
    }

    private fun makeCall(phoneNumber: String) {
        val call = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))
        call.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(call)
    }

    private fun shareOffer(offerId: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://${getString(R.string.app_deeplink)}/$offerId")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, getString(R.string.share_with)))
    }

    inner class ItemClickListener : FavouritesAdapter.FavAdapterItemClickCallback {
        override fun onItemClicked(item: FlatViewObject) {
            val args = Bundle().apply {
                putString(Constants.INITIATOR_VIEW, TAG)
                putString(Constants.OFFER_ID, item.id)
            }
            loadFragmentCallback.startNewActivity(FlatDetailsActivity(), args)
        }

        override fun onMakeCallClicked(item: FlatViewObject) {
            item.phoneNumber?.let {
                val roomsInfo = if (item.roomsTotal != null) "${item.roomsTotal} комнаты" else ""
                val areaInfo = if (item.area != null) "${item.area} кв.м" else ""
                val floorInfo = if ((item.floors != null) and (item.totalFloors != null))
                    "этаж ${item.floors}/${item.totalFloors}" else ""
                val info = "$roomsInfo $areaInfo $floorInfo"
                viewModel.addNewCall(Call(item.id, item.images[0], it, info, Date()))
                makeCall(it)
            }
        }

        override fun onAddToFavClicked(item: FlatViewObject) {
            viewModel.dislikeFlat(item.refToOffer)
        }

        override fun shareOffer(offerId: String) {
            this@MyFavouritesFragment.shareOffer(offerId)
        }
    }

    private fun renderSelectionStateViews(selectedItemsCount: Int) {
        relativeLayout_fragmentfavs_picker.visibility = View.VISIBLE
        textview_fragmentfavs_chooser_count.text = selectedItemsCount.toString()
        button_favs_share.show()
        button_favs_remove.show()
    }

    private fun hideSelectionStateViews() {
        relativeLayout_fragmentfavs_picker.visibility = View.GONE
        button_favs_share.hide()
        button_favs_remove.hide()
    }
}