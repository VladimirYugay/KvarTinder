package com.lounah.yarealty.api.data.api;

import com.lounah.yarealty.BuildConfig;
import com.lounah.yarealty.data.network.AuthInterceptor;
import com.lounah.yarealty.data.network.NetworkSource;
import com.lounah.yarealty.data.network.RealtyApi;

import org.junit.BeforeClass;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class ApiTest {
    protected static NetworkSource api;
    protected static final String offerId = "5004872943638045567";

    @BeforeClass
    public static void start() {
        api = new NetworkSource(new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(new OkHttpClient.Builder().addInterceptor(new AuthInterceptor()).build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RealtyApi.class));
    }
}
