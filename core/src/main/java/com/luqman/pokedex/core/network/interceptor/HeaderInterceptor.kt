package com.luqman.pokedex.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/*
* This the place to put your credential keys for the API
* */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .build()
//                .addHeader("key", "value")
        )
    }
}