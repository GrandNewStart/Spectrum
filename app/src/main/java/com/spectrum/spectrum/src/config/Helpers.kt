package com.spectrum.spectrum.src.config

import android.content.SharedPreferences
import android.util.Log
import com.orhanobut.logger.Logger
import com.spectrum.spectrum.src.config.Constants.BASE_URL
import com.spectrum.spectrum.src.config.Constants.DENSITY
import com.spectrum.spectrum.src.config.Constants.MEDIA_TYPE_JSON
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Helpers {

    lateinit var sharedPreferences: SharedPreferences

    private var loggingInterceptor = HttpLoggingInterceptor {
        if (it.startsWith("{") && it.endsWith("}")) {
            Logger.t("OKHTTP").json(it)
        }
        else {
            Log.i("OKHTTP", it)
        }
    }.apply { setLevel(HttpLoggingInterceptor.Level.BODY) }

    private var httpClient = OkHttpClient.Builder()
        .readTimeout(3000, TimeUnit.MILLISECONDS)
        .connectTimeout(3000, TimeUnit.MILLISECONDS)
        .addNetworkInterceptor(loggingInterceptor)
        .addNetworkInterceptor(RequestInterceptor(MEDIA_TYPE_JSON))
        .build()

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun px2dp(px: Int): Int {
        val dp = px / DENSITY
        return dp.toInt()
    }

    fun dp2px(dp: Int): Int {
        val px = dp * DENSITY
        return px.toInt()
    }

}