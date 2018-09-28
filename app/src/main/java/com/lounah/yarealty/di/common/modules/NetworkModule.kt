package com.lounah.yarealty.di.common.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lounah.yarealty.BuildConfig
import com.lounah.yarealty.data.network.AuthInterceptor
import com.lounah.yarealty.data.network.RealtyApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideAuthInterceptor() = AuthInterceptor()

    @Singleton
    @Provides
    fun provideHttpClient(authInterceptor: AuthInterceptor) = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideGson() = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient, gson: Gson) = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit) = retrofit.create(RealtyApi::class.java)

}