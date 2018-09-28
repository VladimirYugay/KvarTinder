package com.lounah.yarealty.di.common.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lounah.yarealty.di.common.ViewModelKey
import com.lounah.yarealty.presentation.common.AppViewModelFactory
import com.lounah.yarealty.presentation.favourites.calls.CallHistoryViewModel
import com.lounah.yarealty.presentation.favourites.myfavorites.FavouritesViewModel
import com.lounah.yarealty.presentation.flat.FlatsViewModel
import com.lounah.yarealty.presentation.flatdetails.FlatDetailsViewModel
import com.lounah.yarealty.presentation.settings.filters.SearchFiltersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchFiltersViewModel::class)
    abstract fun bindSearchFilterstViewModel(viewModel: SearchFiltersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FlatsViewModel::class)
    abstract fun bindFlatsViewModel(viewModel: FlatsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouritesViewModel::class)
    abstract fun bindFavouritesViewModel(viewModel: FavouritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FlatDetailsViewModel::class)
    abstract fun bindFlatDetailsViewModel(viewModel: FlatDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CallHistoryViewModel::class)
    abstract fun bindCallHistoryViewModel(viewModel: CallHistoryViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}