package com.lounah.yarealty.di.realty

import android.app.Application
import android.arch.persistence.room.Room
import com.lounah.yarealty.BuildConfig
import com.lounah.yarealty.data.database.AppDatabase
import com.lounah.yarealty.data.network.NetworkSource
import com.lounah.yarealty.data.network.RealtyApi
import com.lounah.yarealty.data.prefs.SettingsPreferences
import com.lounah.yarealty.data.repository.SettingsRepositoryImpl
import com.lounah.yarealty.device.network.NetworkInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RealtyRepositoriesModule {

    @Singleton
    @Provides
    fun provideNetworkSource(realtyApi: RealtyApi) = NetworkSource(realtyApi)

    @Singleton
    @Provides
    fun provideDatabase(context: Application) =
            Room.databaseBuilder(context, AppDatabase::class.java, BuildConfig.DB_NAME).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideOfferDao(db: AppDatabase) = db.offerDao()

    @Singleton
    @Provides
    fun provideDislikedOfferDao(db: AppDatabase) = db.dislikedOfferDao()

    @Singleton
    @Provides
    fun provideCallHistoryDao(db: AppDatabase) = db.callsDao()

    @Singleton
    @Provides
    fun provideSettingsRepository(
            settingsPreferences: SettingsPreferences,
            networkSource: NetworkSource,
            networkInfo: NetworkInfo) = SettingsRepositoryImpl(settingsPreferences, networkSource, networkInfo)
}