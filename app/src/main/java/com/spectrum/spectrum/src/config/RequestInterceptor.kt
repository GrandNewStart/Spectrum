package com.spectrum.spectrum.src.config

import com.spectrum.spectrum.src.config.Constants.TOKEN
import com.spectrum.spectrum.src.config.Helpers.sharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(private val contentType: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().newBuilder().apply {
//            sharedPreferences.getString(TOKEN, null)?.let { addHeader() }
            addHeader("Content-Type", "application/json")
            return chain.proceed(build())
        }
    }
}