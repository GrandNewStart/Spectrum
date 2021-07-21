package com.spectrum.spectrum.src.activities.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.LogInActivity
import com.spectrum.spectrum.src.activities.main.MainActivity
import com.spectrum.spectrum.src.activities.splash.interfaces.SplashApi
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.sharedPreferences
import com.spectrum.spectrum.src.customs.BaseActivity
import com.spectrum.spectrum.src.config.Constants.TOKEN
import com.spectrum.spectrum.src.config.Helpers.retrofit
import kotlinx.coroutines.launch
import java.lang.Exception

class SplashActivity: BaseActivity() {

    private val runnable = Runnable {
        startActivity(Intent(this, LogInActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        checkToken()
    }

    private fun checkToken() {
        sharedPreferences.apply {
            val token = getString(TOKEN, null)
            Log.d(TAG, "---> TOKEN: $token")
            if (token == null) {
                Handler().postDelayed(runnable, 1000)
                return
            }
            validateToken()
        }
    }

    private fun validateToken() {
        try {
            showProgressDialog(true)
            lifecycleScope.launch {
                retrofit.create(SplashApi::class.java).validateToken().apply {
                    showProgressDialog(false)
                    if (isSuccess) {
                        Log.d(TAG, "---> TOKEN VALIDATION SUCCESS")
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                        return@launch
                    }
                    Log.e(TAG, "---> TOKEN VALIDATION FAILURE: $message")
                    startActivity(Intent(this@SplashActivity, LogInActivity::class.java))
                    finish()
                    return@launch
                }
            }
        }
        catch (e: Exception) {
            Log.e(TAG, "---> TOKEN VALIDATION FAILURE: $e")
            startActivity(Intent(this@SplashActivity, LogInActivity::class.java))
            finish()
            showProgressDialog(false)
        }
    }

}