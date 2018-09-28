package com.lounah.yarealty.di.realty

import com.lounah.yarealty.data.repository.CallHistoryRepositoryImpl
import com.lounah.yarealty.data.repository.OfferRepositoryImpl
import com.lounah.yarealty.data.repository.SettingsRepositoryImpl
import com.lounah.yarealty.domain.repository.CallHistoryRepository
import com.lounah.yarealty.domain.repository.OfferRepository
import com.lounah.yarealty.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RealtyBindingModule {

    @Singleton
    @Binds
    abstract fun bindOfferRepository(impl: OfferRepositoryImpl): OfferRepository

    @Singleton
    @Binds
    abstract fun bindSettingRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Singleton
    @Binds
    abstract fun bindCallHistoryRepository(impl: CallHistoryRepositoryImpl): CallHistoryRepository

}