package com.spectrum.spectrum.src.activities.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.LogInActivity
import com.spectrum.spectrum.src.customs.BaseActivity

class SplashActivity: BaseActivity() {

    private val runnable = Runnable {
        startActivity(Intent(this, LogInActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(runnable, 1000)
    }

}