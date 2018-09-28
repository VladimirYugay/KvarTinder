package com.lounah.yarealty.di.common

import com.lounah.yarealty.di.realty.RealtyFragmentsInjectorBuilders
import com.lounah.yarealty.presentation.flatdetails.FlatDetailsActivity
import com.lounah.yarealty.presentation.main.MainActivity
import com.lounah.yarealty.presentation.sharedoffers.SharedOffersActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesBuildersModule {
    @ContributesAndroidInjector(modules = [
        RealtyFragmentsInjectorBuilders::class]
    )
    fun provideMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [
        RealtyFragmentsInjectorBuilders::class]
    )
    fun provideFlatDetailsActivity(): FlatDetailsActivity

    @ContributesAndroidInjector(modules = [
        RealtyFragmentsInjectorBuilders::class]
    )
    fun provideSharedOffersActivity(): SharedOffersActivity
}