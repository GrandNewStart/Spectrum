package com.spectrum.spectrum.src.config

import android.content.SharedPreferences
import com.spectrum.spectrum.src.config.Constants.DENSITY

object Helpers {

    lateinit var sharedPreferences: SharedPreferences

    fun px2dp(px: Int): Int {
        val dp = px / DENSITY
        return dp.toInt()
    }

    fun dp2px(dp: Int): Int {
        val px = dp * DENSITY
        return px.toInt()
    }

}