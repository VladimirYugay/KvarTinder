package com.lounah.yarealty.presentation.settings.filters

import android.Manifest
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.lounah.yarealty.R
import com.lounah.yarealty.device.location.LocationListener
import com.lounah.yarealty.presentation.common.BaseFragment
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.DelegateAdapterFactory
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.agencycommission.AgencyCommissionDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.areakitchen.AreaKitchenDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.buildinginfo.BuildingInfoDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.buildtype.BuildTypeDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.buyflat.BuyFlatDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.flatinfo.FlatInfoDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.floorinfo.FloorInfoDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.houseinfo.HouseInfoDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.ihc.IhcDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.onlyfromowners.OnlyFromOwnersDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.onlyphotoandowners.OnlyPhotoAndOwnersDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.region.RegionDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.roomfacilities.RoomFacilitiesDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.roominfo.RoomInfoDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.roomspec.RoomSpecDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.timeperiod.TimePeriodDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.dialog.FiltersDialogFragment
import com.lounah.yarealty.utils.Utils
import kotlinx.android.synthetic.main.fragment_searchfilters.*
import kotlinx.android.synthetic.main.item_filter_agency_commission.*
import kotlinx.android.synthetic.main.item_filter_area_kitchen.*
import kotlinx.android.synthetic.main.item_filter_buildinginfo.*
import kotlinx.android.synthetic.main.item_filter_buyflat.*
import kotlinx.android.synthetic.main.item_filter_flatinfo.*
import kotlinx.android.synthetic.main.item_filter_floorinfo.*
import kotlinx.android.synthetic.main.item_filter_houseinfo.*
import kotlinx.android.synthetic.main.item_filter_onlyphoto_and_owners.*
import kotlinx.android.synthetic.main.item_filter_region.*
import kotlinx.android.synthetic.main.item_filter_room_facilities.*
import kotlinx.android.synthetic.main.item_filter_roominfo.*
import javax.inject.Inject

class SearchFiltersFragment : BaseFragment(), FiltersDialogFragment.OnClickListener {

    override val TAG = "SEARCH_FILTERS_FRAGMENT"

    override val layoutRes = R.layout.fragment_searchfilters

    private companion object {
        const val SETTING_REQUEST_CODE_MASK = 0xF0
        const val ACTION_REQUEST_CODE_MASK = 0xF

        const val ACTION_LOCATION_LOCAL_CODE = 0x4
        const val ACTION_LOCATION_GLOBAL_CODE = 0x5

        const val SETTING_LOCATION_CODE = 0xA0
    }

    @Inject
    lateinit var viewModel: SearchFiltersViewModel

    private lateinit var buildTypeDelegateAdapter: BuildTypeDelegateAdapter
    private lateinit var buildingInfoDelegateAdapter: BuildingInfoDelegateAdapter
    private lateinit var buyFlatDelegateAdapter: BuyFlatDelegateAdapter
    private lateinit var roomSpecDelegateAdapter: RoomSpecDelegateAdapter
    private lateinit var floorInfoDelegateAdapter: FloorInfoDelegateAdapter
    private lateinit var onlyPhotoAndOwnersDelegateAdapter: OnlyPhotoAndOwnersDelegateAdapter
    private lateinit var roomInfoDelegateAdapter: RoomInfoDelegateAdapter
    private lateinit var houseInfoDelegateAdapter: HouseInfoDelegateAdapter
    private lateinit var regionDelegateAdapter: RegionDelegateAdapter
    private lateinit var ihcDelegateAdapter: IhcDelegateAdapter
    private lateinit var timePeriodDelegateAdapter: TimePeriodDelegateAdapter
    private lateinit var areaKitchenDelegateAdapter: AreaKitchenDelegateAdapter
    private lateinit var roomFacilitiesDelegateAdapter: RoomFacilitiesDelegateAdapter
    private lateinit var agencyCommissionDelegateAdapter: AgencyCommissionDelegateAdapter
    private lateinit var onlyFromOwnersDelegateAdapter: OnlyFromOwnersDelegateAdapter
    private lateinit var flatInfoDelegateAdapter: FlatInfoDelegateAdapter

    private lateinit var currentAdapter: DiffUtilCompositeAdapter

    private lateinit var dialog: FiltersDialogFragment

    private val localLocationListener = object : LocationListener() {
        private var lastLocation: Location? = null
        override fun onLocationChanged(location: Location?) {
            location?.let {
                if (lastLocation == null) {
                    lastLocation = it
                } else if (it.accuracy < lastLocation!!.accuracy) {
                    lastLocation = it
                    viewModel.stopLocationUpdate()
                }
                lastLocation?.let { viewModel.getLocalRegionId(it.latitude, it.longitude) }
            }
        }
    }

    private val globalLocationListener = object : LocationListener() {
        override fun onLocationChanged(location: Location?) {
            location?.let {
                viewModel.getGlobalRegion(it.latitude, it.longitude)
                viewModel.stopLocationUpdate()
            }
        }
    }

    override fun initComponents() {
        initDelegateAdapters()

        viewModel.dealType.observe(this, Observer { newDealType ->
            onDealTypeChanged(newDealType!!)
        })

        viewModel.localRegions.observe(this, Observer { regions ->
            regions?.let {
                val geoObject = it.firstOrNull()
                geoObject?.let {
                    viewModel.updateLocalRegionId(it.rgid)
                }
            }
        })

        viewModel.globalRegion.observe(this, Observer { region ->
            region?.let {
                viewModel.updateGlobalRegionId(it.rgid)
                textview_filter_main_city.text = it.name
            }
        })

        recyclerview_fitlers_params.layoutManager = LinearLayoutManager(context)
    }

    override fun initComponentsCallbacks() {
        spinner_filters_buyrent.onItemSelectedListener = BuyRentSpinnerListener()
        spinner_filters_type.onItemSelectedListener = DealTypeSpinnerListener()
        linearlayout_filter_main_price.setOnClickListener(SettingsItemClickListener())

        switch_search_near.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val isEnabled = checkPermissions(SETTING_LOCATION_CODE or ACTION_LOCATION_LOCAL_CODE)
                if (isEnabled) defineLocalRegion()
                switch_search_near.isChecked = isEnabled
            } else {
                viewModel.restoreGlobalRegionId()
                viewModel.stopLocationUpdate()
            }
        }

        textview_filter_main_city.setOnClickListener {
            if (checkPermissions(SETTING_LOCATION_CODE or ACTION_LOCATION_GLOBAL_CODE)) {
                defineGlobalRegion()
            }
        }
    }

    private fun checkPermissions(requestCode: Int): Boolean {
        context?.let { ctx ->
            return if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestCode)
                false
            } else {
                shouldOpenLocationSettings(requestCode)
            }
        }
        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode and SETTING_REQUEST_CODE_MASK == SETTING_LOCATION_CODE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            shouldOpenLocationSettings(requestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        defineLocation(requestCode)
    }

    private fun defineLocalRegion() {
        switch_search_near.isChecked = true
        viewModel.updateLocation(localLocationListener)
    }

    private fun defineGlobalRegion() {
        switch_search_near.isChecked = false
        viewModel.updateLocation(globalLocationListener)
    }

    private fun shouldOpenLocationSettings(requestCode: Int): Boolean {
        return if (!viewModel.isLocationProvidersEnabled()) {
            startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), requestCode)
            false
        } else {
            defineLocation(requestCode)
            true
        }
    }

    private fun defineLocation(requestCode: Int) {
        when (requestCode and ACTION_REQUEST_CODE_MASK) {
            ACTION_LOCATION_GLOBAL_CODE -> {
                defineGlobalRegion()
            }
            ACTION_LOCATION_LOCAL_CODE -> {
                defineLocalRegion()
            }
        }
    }

    override fun onCancelClicked() {
        dialog.dismiss()
    }

    override fun onOkClicked(resultFrom: Long, resultTo: Long, initiatorViewId: Int) {
        dialog.dismiss()
        when (initiatorViewId) {
            R.id.linearlayout_filter_areakitchen_total_area -> {
                updateTextViewValues(textview_filter_areakitchen_total_area_spec, resultFrom, resultTo,
                        "areaMin", "areaMax")
            }
            R.id.linearlayout_filter_main_price -> {
                updateTextViewValues(textview_filter_main_price_spec, resultFrom, resultTo,
                        "priceMin", "priceMax")
            }
            R.id.linearlayout_filter_areakitchen_kitchen -> {
                updateTextViewValues(textview_filter_areakitchen_kitchen_spec, resultFrom, resultTo,
                        "kitchenSpaceMin", "kitchenSpaceMax")
            }
            R.id.linearlayout_filter_buildinginfo_year -> {
                updateTextViewValues(textview_buildinginfo_year_spec, resultFrom, resultTo,
                        "builtYearMin", "builtYearMax")
            }
            R.id.linearlayout_filter_buyflat_total_area -> {
                updateTextViewValues(textview_filter_buyflat_total_area_spec, resultFrom, resultTo,
                        "areaMin", "areaMax")
            }
            R.id.linearlayout_filter_buyflat_kitchen -> {
                updateTextViewValues(textview_filter_buyflat_kitchen_spec, resultFrom, resultTo,
                        "kitchenSpaceMin", "kitchenSpaceMax")
            }
            R.id.linearlayout_filter_flatinfo_room_area -> {
                updateTextViewValues(textview_flatinfo_roomarea_spec, resultFrom, resultTo,
                        "areaMin", "areaMax")
            }
            R.id.linearlayout_filter_flatinfo_kitchen -> {
                updateTextViewValues(textview_filter_flatinfo_kitchen_spec, resultFrom, resultTo,
                        "kitchenSpaceMin", "kitchenSpaceMax")
            }
            R.id.linearlayout_filter_floorinfo_floor -> {
                updateTextViewValues(textview_floorinfo_floor_spec, resultFrom, resultTo,
                        "floorMin", "floorMax")
            }
            R.id.linearlayout_filter_houseinfo_housearea -> {
                updateTextViewValues(textview_filter_houseinfo_housearea_spec, resultFrom, resultTo,
                        "areaMin", "areaMax")
            }
            R.id.linearlayout_filter_houseinfo_region -> {
                updateTextViewValues(textview_filter_houseinfo_region_spec, resultFrom, resultTo,
                        "lotAreaMin", "lotAreaMax")
            }
            R.id.linearlayout_filter_region -> {
                updateTextViewValues(textview_filter_region_spec, resultFrom, resultTo,
                        "lotAreaMin", "lotAreaMax")
            }

            R.id.linearlayout_filter_roominfo_room_area -> {
                updateTextViewValues(textview_roominfo_roomarea_spec, resultFrom, resultTo,
                        "livingSpaceMin", "livingSpaceMax")
            }
            R.id.linearlayout_filter_roominfo_kitchen -> {
                updateTextViewValues(textview_roominfo_kitchen_spec, resultFrom, resultTo,
                        "kitchenSpaceMin", "kitchenSpaceMax")
            }
        }
    }

    private fun updateTextViewValues(textView: TextView, valueFrom: Long, valueTo: Long, valueFromKey: String, valueToKey: String) {
        if (valueFrom == 0L && valueTo == 0L) {
            textView.text = getString(R.string.unimportantly)
            viewModel.updateIntSetting(valueFromKey, 0)
            viewModel.updateIntSetting(valueToKey, 1000000000)
            return
        }

        if (valueFrom == 0L && valueTo > 0) {
            viewModel.updateIntSetting(valueFromKey, valueFrom.toInt())
            viewModel.updateIntSetting(valueToKey, valueTo.toInt())
            textView.text = getString(R.string.price_just_to, Utils.truncateFieldValue(valueTo))
            return
        }
        if (valueFrom > 0 && valueTo == 1_000_000_000L) {
            viewModel.updateIntSetting(valueFromKey, valueFrom.toInt())
            viewModel.updateIntSetting(valueToKey, valueTo.toInt())
            textView.text = getString(R.string.price_just_from, Utils.truncateFieldValue(valueFrom))
            return
        }

        viewModel.updateIntSetting(valueFromKey, valueFrom.toInt())
        viewModel.updateIntSetting(valueToKey, valueTo.toInt())
        textView.text = "${Utils.truncateFieldValue(valueFrom)}-${Utils.truncateFieldValue(valueTo)}"
    }

    private fun initDelegateAdapters() {
        buildTypeDelegateAdapter = BuildTypeDelegateAdapter { rg, checkedId ->

        }
        buildingInfoDelegateAdapter = BuildingInfoDelegateAdapter(SettingsItemClickListener())
        flatInfoDelegateAdapter = FlatInfoDelegateAdapter(SettingsItemClickListener(), SettingsItemClickListener())
        buyFlatDelegateAdapter = BuyFlatDelegateAdapter(SettingsItemClickListener(), SettingsItemClickListener(), SettingsSwitchButtonClickListener(R.id.switch_buyflat))
        floorInfoDelegateAdapter = FloorInfoDelegateAdapter(SettingsItemClickListener(), SettingsSwitchButtonClickListener(R.id.switch_floorinfo_excludefirst))
        onlyPhotoAndOwnersDelegateAdapter = OnlyPhotoAndOwnersDelegateAdapter(SettingsSwitchButtonClickListener(R.id.switch_only_with_photo), SettingsSwitchButtonClickListener(R.id.switch_only_with_photo))
        roomInfoDelegateAdapter = RoomInfoDelegateAdapter(SettingsItemClickListener(), SettingsItemClickListener())
        houseInfoDelegateAdapter = HouseInfoDelegateAdapter(SettingsItemClickListener(), SettingsItemClickListener())
        regionDelegateAdapter = RegionDelegateAdapter(SettingsItemClickListener())
        ihcDelegateAdapter = IhcDelegateAdapter(SettingsSwitchButtonClickListener(R.id.switch_ihc_ihc), SettingsSwitchButtonClickListener(R.id.switch_ihc_hnp))
        timePeriodDelegateAdapter = TimePeriodDelegateAdapter { rg, checkedId ->
            when (checkedId) {
                R.id.btn_perday -> viewModel.updateStringSetting("rentTime", "SHORT")
                R.id.btn_longtime -> viewModel.updateStringSetting("rentTime", "LARGE")
            }
        }
        areaKitchenDelegateAdapter = AreaKitchenDelegateAdapter(SettingsItemClickListener(), SettingsItemClickListener())
        roomFacilitiesDelegateAdapter = RoomFacilitiesDelegateAdapter(SettingsSwitchButtonClickListener(R.id.switch_roomfacilities_fridge),
                SettingsSwitchButtonClickListener(R.id.switch_roomfacilities_dishwasher),
                SettingsSwitchButtonClickListener(R.id.switch_roomfacilities_air_conditioning),
                SettingsSwitchButtonClickListener(R.id.switch_roomfacilities_washer),
                SettingsSwitchButtonClickListener(R.id.switch_roomfacilities_furniture),
                SettingsSwitchButtonClickListener(R.id.switch_roomfacilities_with_pets),
                SettingsSwitchButtonClickListener(R.id.switch_roomfacilities_with_kids))
        agencyCommissionDelegateAdapter = AgencyCommissionDelegateAdapter(SettingsSwitchButtonClickListener(R.id.switch_agency_no_commission))
        onlyFromOwnersDelegateAdapter = OnlyFromOwnersDelegateAdapter(SettingsSwitchButtonClickListener(R.id.switch_only_from_owners))
        roomSpecDelegateAdapter = RoomSpecDelegateAdapter(SettingsItemClickListener())

        currentAdapter = DiffUtilCompositeAdapter.Builder()
                .add(buildingInfoDelegateAdapter)
                .add(buildTypeDelegateAdapter)
                .add(flatInfoDelegateAdapter)
                .add(buyFlatDelegateAdapter)
                .add(floorInfoDelegateAdapter)
                .add(onlyPhotoAndOwnersDelegateAdapter)
                .add(onlyFromOwnersDelegateAdapter)
                .add(roomInfoDelegateAdapter)
                .add(houseInfoDelegateAdapter)
                .add(regionDelegateAdapter)
                .add(ihcDelegateAdapter)
                .add(timePeriodDelegateAdapter)
                .add(areaKitchenDelegateAdapter)
                .add(roomFacilitiesDelegateAdapter)
                .add(agencyCommissionDelegateAdapter)
                .add(roomSpecDelegateAdapter)
                .build()


        recyclerview_fitlers_params.adapter = currentAdapter
    }

    private fun onDealTypeChanged(newDealType: SearchFiltersViewModel.DealType) {
        textview_filter_main_price_spec.text = resources.getString(R.string.unimportantly)
        val newViewModels = DelegateAdapterFactory.provideViewModels(newDealType)
        currentAdapter.swapData(newViewModels)
        recyclerview_fitlers_params.smoothScrollToPosition(0)
        resetUIItems()
    }

    private fun resetUIItems() {
        textview_filter_areakitchen_total_area_spec?.let { it.text = resources.getString(R.string.unimportantly) }
        textview_filter_main_price_spec?.let { it.text = resources.getString(R.string.unimportantly) }
        textview_filter_areakitchen_kitchen_spec?.let { it.text = resources.getString(R.string.unimportantly) }
        textview_buildinginfo_year_spec?.let { it.text = resources.getString(R.string.unimportantly) }
        textview_filter_buyflat_total_area_spec?.let { it.text = resources.getString(R.string.unimportantly) }
        textview_filter_buyflat_kitchen_spec?.let { it.text = resources.getString(R.string.unimportantly) }
        textview_flatinfo_roomarea_spec?.let { it.text = resources.getString(R.string.unimportantly) }
        textview_filter_flatinfo_kitchen_spec?.let { it.text = resources.getString(R.string.unimportantly) }
        textview_floorinfo_floor_spec?.let { it.text = resources.getString(R.string.unimportantly) }
        textview_filter_houseinfo_housearea_spec?.let { it.text = resources.getString(R.string.unimportantly) }
        textview_filter_houseinfo_region_spec?.let { it.text = resources.getString(R.string.unimportantly) }
        textview_filter_region_spec?.let { it.text = resources.getString(R.string.unimportantly) }
        textview_roominfo_roomarea_spec?.let { it.text = resources.getString(R.string.unimportantly) }
        textview_roominfo_kitchen_spec?.let { it.text = resources.getString(R.string.unimportantly) }

        switch_roomfacilities_fridge?.let { it.isChecked = true }
        switch_roomfacilities_dishwasher?.let { it.isChecked = false }
        switch_roomfacilities_air_conditioning?.let { it.isChecked = false }
        switch_roomfacilities_washer?.let { it.isChecked = false }
        switch_roomfacilities_furniture?.let { it.isChecked = false }
        switch_roomfacilities_with_pets?.let { it.isChecked = false }
        switch_roomfacilities_with_kids?.let { it.isChecked = false }
        switch_agency_no_commission?.let { it.isChecked = false }
        switch_only_from_owners?.let { it.isChecked = false }
        switch_only_with_photo?.let { it.isChecked = false }
        switch_floorinfo_excludefirst?.let { it.isChecked = false }
    }

    private fun onShowDialogFragment(initiatorView: View) {
        val valuesFromThisField = Utils.getValuesFromSearchFilters(((initiatorView as LinearLayout).getChildAt(1) as TextView).text.toString())
        val valueFrom = valuesFromThisField[0].toLong()
        val valueTo = valuesFromThisField[1].toLong()
        dialog = FiltersDialogFragment.newInstance(initiatorView.id, valueFrom, valueTo)
        dialog.show(childFragmentManager, TAG)
    }


    inner class SettingsItemClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            onShowDialogFragment(initiatorView = view!!)
        }
    }

    inner class SettingsSwitchButtonClickListener(private val clickedId: Int) : View.OnClickListener {
        override fun onClick(view: View?) {
            when (clickedId) {
                R.id.switch_roomfacilities_fridge -> {
                    val state = switch_roomfacilities_fridge.isChecked
                    viewModel.updateBooleanSetting("hasRefrigerator", state)
                }
                R.id.switch_roomfacilities_dishwasher -> {
                    val state = switch_roomfacilities_dishwasher.isChecked
                    viewModel.updateBooleanSetting("hasDishwasher", state)
                }
                R.id.switch_roomfacilities_air_conditioning -> {
                    val state = switch_roomfacilities_air_conditioning.isChecked
                    viewModel.updateBooleanSetting("hasAircondition", state)
                }
                R.id.switch_roomfacilities_washer -> {
                    val state = switch_roomfacilities_washer.isChecked
                    viewModel.updateBooleanSetting("hasWashingMachine", state)
                }
                R.id.switch_roomfacilities_furniture -> {
                    val state = switch_roomfacilities_furniture.isChecked
                    viewModel.updateBooleanSetting("hasFurniture", state)
                }
                R.id.switch_roomfacilities_with_pets -> {
                    val state = switch_roomfacilities_with_pets.isChecked
                    viewModel.updateBooleanSetting("withPets", state)
                }
                R.id.switch_roomfacilities_with_kids -> {
                    val state = switch_roomfacilities_with_kids.isChecked
                    viewModel.updateBooleanSetting("withChildren", state)
                }
                R.id.switch_agency_no_commission -> {
                    val state = switch_agency_no_commission.isChecked
                    viewModel.updateBooleanSetting("hasAgentFee", state)
                }
                R.id.switch_only_from_owners -> {
                    val state = switch_only_from_owners.isChecked
                    viewModel.updateBooleanSetting("hasRefrigerator", state)
                }
                R.id.switch_only_with_photo -> {
                    val state = switch_only_with_photo.isChecked
                    viewModel.updateBooleanSetting("hasPhoto", state)
                }
                R.id.switch_ihc_ihc -> {
                    viewModel.updateStringSetting("lotType", "IGS")
                }
                R.id.switch_ihc_hnp -> {
                    viewModel.updateStringSetting("lotType", "GARDEN")
                }
                R.id.switch_floorinfo_excludefirst -> {
                    val state = switch_floorinfo_excludefirst.isChecked
                    viewModel.updateBooleanSetting("floorExceptFirst", state)
                }
            }
        }
    }

    inner class BuyRentSpinnerListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, itemSelected: View?, selectedItemPosition: Int, selectedItemId: Long) {
            viewModel.setBuyRentPosition(selectedItemPosition)
        }
    }

    inner class DealTypeSpinnerListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, itemSelected: View?, selectedItemPosition: Int, selectedItemId: Long) {
            viewModel.setTypePosition(selectedItemPosition)
        }
    }
}