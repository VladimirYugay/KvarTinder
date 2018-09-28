package com.lounah.yarealty.data.network

import com.lounah.yarealty.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
                .addHeader("X-Authorization", BuildConfig.REALTY_API_KEY)
                .build()
        return chain.proceed(request)
    }
}