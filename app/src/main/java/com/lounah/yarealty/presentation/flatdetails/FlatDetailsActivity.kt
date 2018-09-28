package com.lounah.yarealty.presentation.flatdetails

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.airbnb.deeplinkdispatch.DeepLink
import com.lounah.yarealty.BuildConfig
import com.lounah.yarealty.R
import com.lounah.yarealty.data.entity.Call
import com.lounah.yarealty.data.entity.Offer
import com.lounah.yarealty.domain.model.ComplainViewObject
import com.lounah.yarealty.domain.model.FlatViewObject
import com.lounah.yarealty.presentation.common.BaseActivity
import com.lounah.yarealty.presentation.flat.Constants
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapType
import com.yandex.runtime.image.ImageProvider
import kotlinx.android.synthetic.main.activity_flat_zoomed.*
import kotlinx.android.synthetic.main.content_scrolling_activity_flatdetails.*
import java.util.*
import javax.inject.Inject

@DeepLink("app://app.viewdetails")
class FlatDetailsActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: FlatDetailsViewModel

    private lateinit var vpAdapter: FlatDetailsPhotosVpAdapter

    private var initiatorViewId: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMapKit()
        setContentView(R.layout.activity_flat_zoomed)
        val intent = intent
        if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
            val parameters = intent.extras
            val idString = parameters!!.getString("offerId")
            viewModel.loadFlat(idString)
        } else {
            viewModel.loadFlat(intent.getStringExtra(Constants.OFFER_ID)!!)
        }
        initiatorViewId = intent.getStringExtra(Constants.INITIATOR_VIEW)
        initComponents()
    }

    override fun onResume() {
        super.onResume()
        initComponentsListeners()
    }

    override fun onStart() {
        super.onStart()
        mapview_flatdetails.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        mapview_flatdetails.onStop()
        MapKitFactory.getInstance().onStop()
    }

    private fun initMapKit() {
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        MapKitFactory.initialize(this)
    }

    private fun initComponents() {
        vpAdapter = FlatDetailsPhotosVpAdapter()
        viewpager_flatzoomed_images.adapter = vpAdapter
        recycler_parameters_zoomedflat.layoutManager = LinearLayoutManager(this)

        if (initiatorViewId == "MY_FAVS_FRAGMENT" || initiatorViewId == "CALL_HISTORY_FRAGMENT") {
            button_flats_addtofavs.hide()
            button_flats_dislike.hide()
        }

        initViewModelObservers()
    }

    private fun initComponentsListeners() {
        fab_flatzoomed_close.setOnClickListener {
            finish()
        }
        button_flats_dislike.setOnClickListener {
            dislikeFlat(intent.getStringExtra(Constants.OFFER_ID))
        }
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

    private fun dislikeFlat(flatId: String) {
        viewModel.dislikeFlatById(flatId)
    }

    private fun addFlatToFavs(flat: Offer) {
        viewModel.likeFlat(flat)
    }

    private fun initViewModelObservers() {
        viewModel.flat.observe(this, Observer {
            it?.let {
                bindDataToView(it)
            }
        })

        viewModel.actionsResult.observe(this, Observer {
            it?.let {
                if (it) {
                    showToast(R.string.successfully)
                    setResult(Activity.RESULT_OK)
                    finish()
                } else showToast(R.string.error)
            }
        })

        viewModel.loadingState.observe(this, Observer {
            it?.let { processLoadingState(it) }
        })
    }

    private fun processLoadingState(isLoading: Boolean) {
        if (isLoading) onShowLoadingView() else onHideLoadingView()
    }

    private fun onShowLoadingView() {
        ll_flatdetails_main_content.visibility = View.INVISIBLE
        container_fragmentflatdetails_pb.visibility = View.VISIBLE
        progressbar_fragmentflatdetails.visibility = View.VISIBLE
    }

    private fun onHideLoadingView() {
        ll_flatdetails_main_content.visibility = View.VISIBLE
        container_fragmentflatdetails_pb.visibility = View.GONE
        progressbar_fragmentflatdetails.visibility = View.GONE
    }

    private fun bindDataToView(data: FlatViewObject) {

        val roomsInfo = if (data.roomsTotal != null) "${data.roomsTotal} комнаты" else ""
        val areaInfo = if (data.area != null) "${data.area} кв.м" else ""
        val floorInfo = if ((data.floors != null) and (data.totalFloors != null))
            "этаж ${data.floors}/${data.totalFloors}" else ""
        textview_flatzoomed_price.text = data.cost
        textview_flatzoomed_businessinfo.text = "$roomsInfo $areaInfo $floorInfo"
        textview_flatzoomed_adress.text = data.address
        textview_flatzoomed_description.text = data.description

        vpAdapter.updateDataSet(data.images)
        tabindicator_flatzoomed.setViewPager(viewpager_flatzoomed_images)

        button_flats_call.setOnClickListener { makeCall(data) }
        button_flats_addtofavs.setOnClickListener { addFlatToFavs(data.refToOffer) }

        textview_zoomedflat_report.setOnClickListener {
            if (data.refToOffer.partnerId != null && data.refToOffer.uid != null) {
                val complainViewObject = ComplainViewObject(data.refToOffer.offerId, data.refToOffer.partnerId,
                        resources.getString(R.string.default_report_text), data.refToOffer.uid)
                viewModel.complainToFlat(complainViewObject)
                viewModel.dislikeFlatById(data.id)
                showToast(R.string.successfully)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }

        val paramsViewObject = mutableListOf<Pair<String, String>>()

        data.others.mapKeys { it ->
            when (it.key) {
                "pricePerPart" -> resources.getString(R.string.price_per_part)
                "pricePerPartUnit" -> resources.getString(R.string.price_per_part_unit)
                "ceilingHeight" -> resources.getString(R.string.ceiling_height)
                "siteDisplayName" -> resources.getString(R.string.site_display_name)
                "builtQuarter" -> resources.getString(R.string.built_quarter)
                "builtYear" -> resources.getString(R.string.build_year)
                "metro" -> resources.getString(R.string.metro)
                "timeToMetro" -> resources.getString(R.string.time_to_metro)
                "shareURL" -> resources.getString(R.string.share_URL)
                "views" -> resources.getString(R.string.views)
                else -> ""
            }
        }.filter {
            it.value != "0"
        }.forEach { entry ->
            entry.value?.let {
                paramsViewObject.add(Pair(entry.key, it))
            }
        }
        recycler_parameters_zoomedflat.adapter = FlatDetailsParametersRvAdapter(paramsViewObject)
        data.location?.let {
            initMapView(it.latitude!!, it.longitude!!)
        }
    }

    private fun initMapView(latitude: Double, longitude: Double) {
        mapview_flatdetails.map.mapType = MapType.MAP
        mapview_flatdetails.map.move(CameraPosition(Point(latitude, longitude),
                13.0f, 0.0f, 0.0f), Animation(Animation.Type.SMOOTH, 0f),
                null)
        mapview_flatdetails.map.mapObjects
                .addPlacemark(Point(latitude, longitude)).setIcon(ImageProvider.fromResource(this, R.drawable.ic_location_red_24dp))

        mapview_flatdetails.setNoninteractive(true)
        mapview_flatdetails.setOnClickListener { openMap(latitude, longitude) }
    }

    private fun openMap(latitude: Double, longitude: Double) {
        val uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }
}
