package com.huutho.baselibrary.network

import okhttp3.Headers.Companion.toHeaders
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val map: Map<String, String>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder().build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .headers(map.toHeaders())
            .method(request.method, request.body)
            .build()
        return chain.proceed(newRequest)
    }
}