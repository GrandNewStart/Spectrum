package com.spectrum.spectrum.src.activities.login

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.models.JobGroup
import com.spectrum.spectrum.src.customs.BaseActivity

class LogInActivity: BaseActivity() {

    private lateinit var mNavController: NavController
    var mAge: String? = null
    var mSex: String? = null
    var mJobGroup1: JobGroup? = null
    var mJobGroup2: JobGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        mNavController = navHostFragment.navController
    }

}