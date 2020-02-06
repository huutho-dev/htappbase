package com.huutho.baselibrary.network

import android.content.Context
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitHelper {
    fun <T> initial(
        context: Context,
        baseUrl: String,
        serviceClazz: Class<T>,
        mapHeader: Map<String, String> = mapOf(),
        queryParams: Map<String, String> = mapOf(),
        captureResponseCode: (isSuccessful: Boolean, code: Int, message: String) -> Unit = { _, _, _ -> },
        gson: Gson = Gson(),
        timeOut: Long = 30L,
        cacheSize: Long = 10 * 1024 * 1024
    ): T {
        val okHttpClient = OkHttpClient.Builder()
            .cache(Cache(File(context.cacheDir, "https-cache"), cacheSize))
            .addInterceptor(HeaderInterceptor(mapHeader))
            .addInterceptor(AuthInterceptor(queryParams))
            .addInterceptor(CacheInterceptor(context))
            .addInterceptor(LoggingInterceptor(captureResponseCode))
            .callTimeout(timeOut, TimeUnit.SECONDS)
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .writeTimeout(timeOut, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(serviceClazz)
    }
}