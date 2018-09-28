package com.lounah.yarealty.di.realty

import android.app.Application
import com.lounah.yarealty.data.prefs.SettingsPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RealtyDomainModule {

    @Singleton
    @Provides
    fun provideSettingsPreferences(context: Application) = SettingsPreferences(context)
}