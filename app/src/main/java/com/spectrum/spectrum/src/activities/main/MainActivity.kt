package com.spectrum.spectrum.src.activities.main

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.ActivityMainBinding
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.customs.BaseActivity
import com.spectrum.spectrum.src.models.JobGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity: BaseActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private var mBackPressCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.apply {
            lifecycleOwner = this@MainActivity
            activity = this@MainActivity

            navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
            navController = navHostFragment.findNavController()
            bottomNav.setupWithNavController(navController)
        }
        showConstructionSign()
    }

    override fun onBackPressed() {
        if (navHostFragment.childFragmentManager.backStackEntryCount == 0) {
            if (mBackPressCount == 0) {
                startBackPressCountTimer()
                mBackPressCount++
                showToast(Constants.press_back_one_more)
            }
            else {
                finish()
            }
        }
        else {
            super.onBackPressed()
        }
    }

    private fun startBackPressCountTimer() {
        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            mBackPressCount = 0
        }
    }

    private fun showConstructionSign() {
        AlertDialog.Builder(this)
            .setMessage("스펙트럼은 현재 개발 중인 앱입니다.\n빠른 시일내에 완성하여 보여드릴께요!")
            .setPositiveButton("닫기", null)
            .create()
            .show()
    }

}