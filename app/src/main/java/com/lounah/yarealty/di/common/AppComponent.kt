package com.lounah.yarealty.di.common

import android.app.Application
import android.content.Context
import com.lounah.yarealty.YaRealtyApplication
import com.lounah.yarealty.di.common.modules.AppModule
import com.lounah.yarealty.di.common.modules.NetworkModule
import com.lounah.yarealty.di.realty.RealtyBindingModule
import com.lounah.yarealty.di.realty.RealtyDomainModule
import com.lounah.yarealty.di.realty.RealtyRepositoriesModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivitiesBuildersModule::class,
    AppModule::class,
    NetworkModule::class,
    RealtyRepositoriesModule::class,
    RealtyDomainModule::class,
    RealtyBindingModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun appContext(@ApplicationContext context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app: YaRealtyApplication)
}