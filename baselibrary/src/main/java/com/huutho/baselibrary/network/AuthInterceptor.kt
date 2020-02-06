package com.huutho.baselibrary.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val map: Map<String, String>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url
        val urlBuilder = originalHttpUrl.newBuilder()
        map.forEach { urlBuilder.addQueryParameter(it.key, it.value) }
        val requestBuilder = original.newBuilder().url(urlBuilder.build())
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}