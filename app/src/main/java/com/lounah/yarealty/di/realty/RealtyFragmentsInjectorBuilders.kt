package com.lounah.yarealty.di.realty

import com.lounah.yarealty.presentation.favourites.FavouritesFragment
import com.lounah.yarealty.presentation.favourites.calls.CallHistoryFragment
import com.lounah.yarealty.presentation.favourites.myfavorites.MyFavouritesFragment
import com.lounah.yarealty.presentation.flat.FlatsFragment
import com.lounah.yarealty.presentation.main.FragmentMain
import com.lounah.yarealty.presentation.settings.SettingsFragment
import com.lounah.yarealty.presentation.settings.appsettings.AppSettingsFragment
import com.lounah.yarealty.presentation.settings.filters.SearchFiltersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface RealtyFragmentsInjectorBuilders {
    @ContributesAndroidInjector
    fun provideMainFragment(): FragmentMain

    @ContributesAndroidInjector
    fun provideFavsFragment(): FavouritesFragment

    @ContributesAndroidInjector
    fun provideFlatsFragment(): FlatsFragment

    @ContributesAndroidInjector
    fun provideSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    fun provideAppSettingsFragment(): AppSettingsFragment

    @ContributesAndroidInjector
    fun provideSearchFiltersFragment(): SearchFiltersFragment

    @ContributesAndroidInjector
    fun provideCallHistoryFragment(): CallHistoryFragment

    @ContributesAndroidInjector
    fun provideMyFavouritesFragment(): MyFavouritesFragment
}