package com.lounah.yarealty.presentation.flat

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity.RESULT_OK
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.lounah.yarealty.R
import com.lounah.yarealty.domain.model.FlatViewObject
import com.lounah.yarealty.presentation.common.BaseFragment
import com.lounah.yarealty.presentation.flatdetails.FlatDetailsActivity
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.SwipeDirection
import kotlinx.android.synthetic.main.fragment_flats.*
import javax.inject.Inject


class FlatsFragment : BaseFragment(), LoadingCallback {

    override val TAG = "FLATS_FRAGMENT"

    override val layoutRes: Int = R.layout.fragment_flats

    @Inject
    lateinit var viewModel: FlatsViewModel

    private lateinit var flatsCardAdapter: FlatsCvAdapter

    private var onOpenDetailsActivityTaskStarted = false
    private var onReverseRequested = false

    private lateinit var previousItem: FlatViewObject

    override fun initComponents() {
        viewModel.restoreSettings()
        flatsCardAdapter = FlatsCvAdapter(context, R.layout.fragment_flats, this, OnDetailsTappedCallback())
        cardview_flats.setAdapter(flatsCardAdapter)
        viewModel.getFlats()
    }

    override fun initComponentsCallbacks() {
        cardview_flats.setCardEventListener(object : CardStackView.CardEventListener {
            override fun onCardDragging(percentX: Float, percentY: Float) {}
            override fun onCardReversed() {}
            override fun onCardMovedToOrigin() {}
            override fun onCardClicked(index: Int) {}
            override fun onCardSwiped(direction: SwipeDirection?) {
                flatsCardAdapter.resetCurrentIndex()
                when (direction) {
                    SwipeDirection.Left ->
                        if (flatsCardAdapter.currentItemInitialized())
                            dislikeFlat(flatsCardAdapter.currentItem)
                    SwipeDirection.Right -> if (flatsCardAdapter.currentItemInitialized())
                        likeFlat(flatsCardAdapter.currentItem)
                }
            }
        })
        button_flats_dislike.setOnClickListener { swipeLeft() }
        button_flats_addtofavs.setOnClickListener { swipeRight() }
        button_flats_back.setOnClickListener { moveBack() }
        initViewModelObservers()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && ::viewModel.isInitialized) {
            viewModel.checkSettingsAndRefreshFeedIfShould()
        }
    }

    override fun onResume() {
        super.onResume()
        onOpenDetailsActivityTaskStarted = false
    }

    override fun loadMore() {
        viewModel.getNextFlats()
    }

    override fun likeFlat(offer: FlatViewObject) {
        onReverseRequested = false
        viewModel.likeFlat(offer.refToOffer)
        previousItem = offer
        flatsCardAdapter.remove(offer)
        flatsCardAdapter.notifyDataSetChanged()
    }

    override fun dislikeFlat(offer: FlatViewObject) {
        onReverseRequested = false
        viewModel.dislikeFlat(offer.refToOffer)
        previousItem = offer
        flatsCardAdapter.remove(offer)
        flatsCardAdapter.notifyDataSetChanged()
    }

    private fun initViewModelObservers() {
        viewModel.loadingState.observe(this, Observer {
            processLoadingState(it!!)
        })

        viewModel.flats.observe(this, Observer {
            if (it!!.isEmpty()) processEmptyState()
            flatsCardAdapter.updateDataSet(it)

        })

        viewModel.shouldRefreshFlats.observe(this, Observer { shouldRefresh ->
            if (shouldRefresh!!) {
                flatsCardAdapter.clear()
                flatsCardAdapter.notifyDataSetChanged()
                viewModel.getFlats()
            }
        })

        viewModel.paginatedFlats.observe(this, Observer {
            if (it!!.isEmpty()) processEmptyState() else {
                flatsCardAdapter.updateDataSet(it)
            }
        })
    }

    private fun swipeLeft() {
        val target = cardview_flats.topView
        val targetOverlay = cardview_flats.topView.overlayContainer

        val rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", -10f))
        rotation.duration = 200
        val translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, -2000f))
        val translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, 500f))
        translateX.startDelay = 100
        translateY.startDelay = 100
        translateX.duration = 500
        translateY.duration = 500
        val cardAnimationSet = AnimatorSet()
        cardAnimationSet.playTogether(rotation, translateX, translateY)

        val overlayAnimator = ObjectAnimator.ofFloat(targetOverlay, "alpha", 0f, 1f)
        overlayAnimator.duration = 200
        val overlayAnimationSet = AnimatorSet()
        overlayAnimationSet.playTogether(overlayAnimator)
        cardview_flats.swipe(SwipeDirection.Left, cardAnimationSet, overlayAnimationSet)
    }

    private fun swipeRight() {

        val target = cardview_flats.topView
        val targetOverlay = cardview_flats.topView.overlayContainer

        val rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", 10f))
        rotation.duration = 200
        val translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, 2000f))
        val translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, 500f))
        translateX.startDelay = 100
        translateY.startDelay = 100
        translateX.duration = 500
        translateY.duration = 500
        val cardAnimationSet = AnimatorSet()
        cardAnimationSet.playTogether(rotation, translateX, translateY)

        val overlayAnimator = ObjectAnimator.ofFloat(targetOverlay, "alpha", 0f, 1f)
        overlayAnimator.duration = 200
        val overlayAnimationSet = AnimatorSet()
        overlayAnimationSet.playTogether(overlayAnimator)
        cardview_flats.swipe(SwipeDirection.Right, cardAnimationSet, overlayAnimationSet)
    }

    private fun moveBack() {
        if (::previousItem.isInitialized && !onReverseRequested) {
            flatsCardAdapter.addItemToTop(previousItem)
            viewModel.performReverse(previousItem)
            onReverseRequested = true
            cardview_flats.reverse()
        }
    }

    private fun processLoadingState(isLoading: Boolean) {
        if (isLoading) onShowLoadingView() else onHideLoadingView()
    }

    private fun onShowLoadingView() {
        container_fragmentflats_pb.visibility = View.VISIBLE
        progressbar_fragmentflats.visibility = View.VISIBLE
        textview_fragmentflats_error.visibility = View.GONE
    }

    private fun onHideLoadingView() {
        container_fragmentflats_pb.visibility = View.GONE
        progressbar_fragmentflats.visibility = View.GONE
        textview_fragmentflats_error.visibility = View.VISIBLE
    }

    private fun processEmptyState() {

    }

    inner class OnDetailsTappedCallback : FlatsCvAdapter.OnDetailsTappedCallback {
        override fun onDetailsTapped(currentItem: FlatViewObject) {
            if (!onOpenDetailsActivityTaskStarted) {
                onOpenDetailsActivityTaskStarted = true
                val bundle = Bundle().apply { putString(Constants.OFFER_ID, currentItem.id) }
                val openDetailsActivityIntent = Intent(context, FlatDetailsActivity::class.java)
                openDetailsActivityIntent.putExtras(bundle)
                startActivityForResult(openDetailsActivityIntent, Constants.REQUEST_CODE_DETAILS)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                Constants.REQUEST_CODE_DETAILS -> {
                    viewModel.getFlats()
                }
            }
        }
    }
}