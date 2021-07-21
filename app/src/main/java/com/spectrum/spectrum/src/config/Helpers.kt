package com.spectrum.spectrum.src.config

import android.annotation.SuppressLint
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
import java.text.SimpleDateFormat
import java.util.*
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
        .addNetworkInterceptor(RequestInterceptor(MEDIA_TYPE_JSON))
        .addNetworkInterceptor(loggingInterceptor)
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

    @SuppressLint("SimpleDateFormat")
    fun formatDate(year: Int, month: Int, day: Int): String {
        val formatter = SimpleDateFormat("yyyy.MM.dd")
        val date = Date(year-1900, month, day)
        return formatter.format(date)
    }

    fun compareDates(startDate: String?, endDate: String?): Boolean {
        if (startDate == null) return false
        if (endDate == null) return false

        val startYear = startDate.substring(0..3).toInt()
        val endYear = endDate.substring(0..3).toInt()
        if (startYear > endYear) return false
        if (startYear < endYear) return true

        val startMonth = startDate.substring(5..6).toInt()
        val endMonth = endDate.substring(5..6).toInt()
        if (startMonth > endMonth) return false
        if (startMonth < endMonth) return true

        val startDay = startDate.substring(8..9).toInt()
        val endDay = endDate.substring(8..9).toInt()
        if (startDay > endDay) return false

        return true
    }

}