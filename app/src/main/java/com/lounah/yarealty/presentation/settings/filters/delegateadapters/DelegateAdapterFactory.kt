package com.lounah.yarealty.presentation.settings.filters.delegateadapters

import com.example.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.example.delegateadapter.delegate.diff.IComparableItem
import com.lounah.yarealty.R
import com.lounah.yarealty.presentation.settings.filters.SearchFiltersViewModel
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.agencycommission.AgencyCommissionViewModel
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.buildinginfo.BuildingInfoViewModel
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.buildtype.BuildTypeViewModel
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.buyflat.BuyFlatDelegateAdapter
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.buyflat.BuyFlatViewModel
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.flatinfo.FlatInfoViewModel
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.floorinfo.FloorInfoViewModel
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.houseinfo.HouseInfoViewModel
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.ihc.IhcViewModel
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.onlyfromowners.OnlyFromOwnersViewModel
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.onlyphotoandowners.OnlyPhotoAndOwnersViewModel
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.region.RegionViewModel
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.roomfacilities.RoomFacilitiesViewModel
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.roominfo.RoomInfoViewModel
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.roomspec.RoomSpecViewModel
import com.lounah.yarealty.presentation.settings.filters.delegateadapters.timeperiod.TimePeriodViewModel

class DelegateAdapterFactory private constructor() {

    companion object {

        fun provideViewModels(dealType: SearchFiltersViewModel.DealType): List<IComparableItem> {
            return when (dealType) {
                SearchFiltersViewModel.DealType.BUY_FLAT -> {
                    val buildingInfoViewModel = BuildingInfoViewModel(0, 2030)
                    val buildTypeViewModel = BuildTypeViewModel(R.id.btn_new_building)
                    val buyFlatViewModel = BuyFlatViewModel(0, 10000, 0,  10000, false)
                    val floorInfoViewModel = FloorInfoViewModel(0, 100000, false)
                    val onlyPhotoAndOwnersViewModel = OnlyPhotoAndOwnersViewModel(false, false)
                    listOf(
                            buildTypeViewModel,
                            buildingInfoViewModel,
                            buyFlatViewModel,
                            floorInfoViewModel,
                            onlyPhotoAndOwnersViewModel)
                }
                SearchFiltersViewModel.DealType.BUY_HOUSE -> {
                    val onlyPhotoAndOwnersViewModel = OnlyPhotoAndOwnersViewModel(false, false)
                    val houseInfoViewModel = HouseInfoViewModel(0, 10000, 0, 10000)
                    listOf(
                            houseInfoViewModel,
                            onlyPhotoAndOwnersViewModel)
                }
                SearchFiltersViewModel.DealType.BUY_AREA -> {
                    val onlyPhotoAndOwnersViewModel = OnlyPhotoAndOwnersViewModel(false, false)
                    val ihcViewModel = IhcViewModel(true, true)
                    val regionViewModel = RegionViewModel(0, 10000)
                    listOf(
                            regionViewModel,
                            ihcViewModel,
                            onlyPhotoAndOwnersViewModel)
                }
                SearchFiltersViewModel.DealType.BUY_ROOM -> {
                    val roomInfoViewModel = RoomInfoViewModel(0, 10000,0, 10000)
                    val floorInfoViewModel = FloorInfoViewModel(0, 1000, false)
                    val buildingInfoViewModel = BuildingInfoViewModel(0, 2030)
                    val onlyPhotoAndOwnersViewModel = OnlyPhotoAndOwnersViewModel(false, false)
                    listOf(
                            roomInfoViewModel,
                            floorInfoViewModel,
                            buildingInfoViewModel,
                            onlyPhotoAndOwnersViewModel)
                }
                SearchFiltersViewModel.DealType.RENT_FLAT -> {
                    val timePeriodViewModel = TimePeriodViewModel(R.id.btn_perday)
                    val rentFlatViewModel = FlatInfoViewModel(0, 1000, 0, 1000)
                    val floorInfoViewModel = FloorInfoViewModel(0, 1000, false)
                    val buildingInfoViewModel = BuildingInfoViewModel(0, 2030)
                    val agencyCommissionViewModel = AgencyCommissionViewModel(false)
                    val onlyFromOwnersViewModel = OnlyFromOwnersViewModel(false)
                    val roomFacilitiesViewModel = RoomFacilitiesViewModel(false, false, false, false, true, false, false)
                    listOf(
                            timePeriodViewModel,
                            rentFlatViewModel,
                            floorInfoViewModel,
                            buildingInfoViewModel,
                            roomFacilitiesViewModel,
                            agencyCommissionViewModel,
                            onlyFromOwnersViewModel)
                }
                SearchFiltersViewModel.DealType.RENT_ROOM -> {
                    val timePeriodViewModel = TimePeriodViewModel(R.id.btn_perday)
                    val roomSpecViewModel = RoomSpecViewModel(0, 1000)
                    val floorInfoViewModel = FloorInfoViewModel(0, 1000, false)
                    val buildingInfoViewModel = BuildingInfoViewModel(0, 2030)
                    val roomFacilitiesViewModel = RoomFacilitiesViewModel(false, false, false, false, true, false, false)
                    val agencyCommissionViewModel = AgencyCommissionViewModel(false)
                    val onlyFromOwnersViewModel = OnlyFromOwnersViewModel(false)
                    listOf(
                            timePeriodViewModel,
                            roomSpecViewModel,
                            floorInfoViewModel,
                            buildingInfoViewModel,
                            roomFacilitiesViewModel,
                            agencyCommissionViewModel,
                            onlyFromOwnersViewModel)
                }
                SearchFiltersViewModel.DealType.RENT_HOUSE -> {
                    val timePeriodViewModel = TimePeriodViewModel(R.id.btn_perday)
                    val agencyCommissionViewModel = AgencyCommissionViewModel(false)
                    val onlyFromOwnersViewModel = OnlyFromOwnersViewModel(false)
                    listOf(
                            timePeriodViewModel,
                            agencyCommissionViewModel,
                            onlyFromOwnersViewModel)
                }
            }
        }

//        fun createSearchFiltersCompositeAdapter() = DiffUtilCompositeAdapter.Builder()
//                .add(BuyFlatDelegateAdapter())

//        fun createBuyFlatAdapter() = DiffUtilCompositeAdapter.Builder()
//                .add(buildTypeDelegateAdapter)
//                .add(buildingInfoDelegateAdapter)
//                .add(buyFlatDelegateAdapter)
//                .add(floorInfoDelegateAdapter)
//                .add(onlyPhotoAndOwnersDelegateAdapter)
//                .build()
//
//        fun createBuyRoomAdapter() = DiffUtilCompositeAdapter.Builder()
//                .add(roomInfoDelegateAdapter)
//                .add(floorInfoDelegateAdapter)
//                .add(buildingInfoDelegateAdapter)
//                .add(onlyPhotoAndOwnersDelegateAdapter)
//                .build()
//
//        fun createBuyHouseAdapter() = DiffUtilCompositeAdapter.Builder()
//                .add(houseInfoDelegateAdapter)
//                .add(onlyPhotoAndOwnersDelegateAdapter)
//                .build()
//
//        fun createBuyAreaAdapter() = DiffUtilCompositeAdapter.Builder()
//                .add(regionDelegateAdapter)
//                .add(ihcDelegateAdapter)
//                .add(onlyPhotoAndOwnersDelegateAdapter)
//                .build()
//
//        fun createRentFlatAdapter() = DiffUtilCompositeAdapter.Builder()
//                .add(timePeriodDelegateAdapter)
//                .add(flatInfoDelegateAdapter)
//                .add(floorInfoDelegateAdapter)
//                .add(buildingInfoDelegateAdapter)
//                .add(roomFacilitiesDelegateAdapter)
//                .add(agencyCommissionDelegateAdapter)
//                .add(onlyFromOwnersDelegateAdapter)
//                .build()
//
//        fun createRentRoomAdapter() = DiffUtilCompositeAdapter.Builder()
//                .add(timePeriodDelegateAdapter)
//                .add(roomSpecDelegateAdapter)
//                .add(floorInfoDelegateAdapter)
//                .add(buildingInfoDelegateAdapter)
//                .add(roomFacilitiesDelegateAdapter)
//                .add(agencyCommissionDelegateAdapter)
//                .add(onlyFromOwnersDelegateAdapter)
//                .build()
//
//        fun createRentHouseAdapter() = DiffUtilCompositeAdapter.Builder()
//                .add(timePeriodDelegateAdapter)
//                .add(agencyCommissionDelegateAdapter)
//                .add(onlyFromOwnersDelegateAdapter)
//                .build()
    }
}