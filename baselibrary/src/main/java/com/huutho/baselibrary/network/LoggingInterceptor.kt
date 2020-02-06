package com.huutho.baselibrary.network

import android.util.Log
import com.huutho.baselibrary.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class LoggingInterceptor(private val captureResponseCode: (isSuccessful: Boolean, code: Int, message: String) -> Unit) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val interceptor =
            HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    i("Retrofit", message)
                }
            })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val response = interceptor.intercept(chain)
        captureResponseCode.invoke(response.isSuccessful, response.code, response.message)
        return response
    }

    fun i(TAG: String, message: String) {
        val maxLogSize = 3072
        for (i in 0..message.length / maxLogSize) {
            val start = i * maxLogSize
            var end = (i + 1) * maxLogSize
            end = if (end > message.length) message.length else end
            Log.i(TAG, message.substring(start, end))
        }
    }
}