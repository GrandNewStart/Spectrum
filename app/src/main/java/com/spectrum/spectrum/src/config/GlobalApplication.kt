package com.spectrum.spectrum.src.config

import android.app.Application
import android.util.Log
import com.spectrum.spectrum.src.config.Constants.APP_TAG
import com.spectrum.spectrum.src.config.Constants.DENSITY
import com.spectrum.spectrum.src.config.Constants.SCREEN_HEIGHT
import com.spectrum.spectrum.src.config.Constants.SCREEN_WIDTH
import com.spectrum.spectrum.src.config.Constants.STATUS_BAR_HEIGHT
import com.spectrum.spectrum.src.config.Helpers.sharedPreferences

class GlobalApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        sharedPreferences = applicationContext.getSharedPreferences(APP_TAG, MODE_PRIVATE)

        SCREEN_WIDTH = resources.displayMetrics.widthPixels
        SCREEN_HEIGHT = resources.displayMetrics.heightPixels
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) { STATUS_BAR_HEIGHT = resources.getDimensionPixelSize(resourceId) }

        Log.d(TAG, "---> SCREEN WIDTH: $SCREEN_WIDTH")
        Log.d(TAG, "---> SCREEN HEIGHT: $SCREEN_HEIGHT")
        Log.d(TAG, "---> STATUS BAR HEIGHT: $STATUS_BAR_HEIGHT")

        DENSITY = resources.displayMetrics.density
    }

    companion object {
        val TAG = GlobalApplication::class.java.simpleName.toString()
    }

}