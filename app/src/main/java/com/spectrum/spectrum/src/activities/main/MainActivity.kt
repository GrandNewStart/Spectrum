package com.spectrum.spectrum.src.activities.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.ActivityMainBinding
import com.spectrum.spectrum.src.activities.main.fragments.home.dialogs.HomeSearchDialog
import com.spectrum.spectrum.src.customs.BaseActivity
import com.spectrum.spectrum.src.models.JobGroup
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: BaseActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val mViewModel by viewModels<MainViewModel>()
    private lateinit var navController: NavController
    var mJobGroup1: JobGroup? = null
    var mJobGroup2: JobGroup? = null
    var mJobGroup3: JobGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.apply {
            lifecycleOwner = this@MainActivity
            activity = this@MainActivity
            viewModel = mViewModel

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
            navController = navHostFragment.findNavController()
            bottomNav.setupWithNavController(navController)
        }
    }


}