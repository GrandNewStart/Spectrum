package com.spectrum.spectrum.src.activities.main

import android.app.AlertDialog
import android.graphics.Rect
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.ActivityMainBinding
import com.spectrum.spectrum.src.activities.main.adapters.RecentPostAdapter
import com.spectrum.spectrum.src.activities.main.models.JobGroup
import com.spectrum.spectrum.src.activities.main.models.RecentPost
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.customs.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity: BaseActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNavHostFragment: NavHostFragment
    private lateinit var mNavController: NavController
    lateinit var mSearchDialogBehavior: BottomSheetBehavior<View>
    private var mBackPressCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            enterTransition = Slide(Gravity.END)
            exitTransition = Slide(Gravity.START)
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.apply {
            lifecycleOwner = this@MainActivity
            activity = this@MainActivity

            mNavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
            mNavController = mNavHostFragment.findNavController()
            bottomNav.setupWithNavController(mNavController)
        }
        initSearchDialog()
        showConstructionSign()
    }

    override fun onBackPressed() {
        if (isSearchDialogVisible()) {
            hideSearchDialog()
            return
        }
        if (mNavHostFragment.childFragmentManager.backStackEntryCount == 0) {
            if (mBackPressCount == 0) {
                startBackPressCountTimer()
                mBackPressCount++
                showToast(Constants.press_back_one_more)
                return
            }
            finish()
        }
        else {
            super.onBackPressed()
        }
    }

    private fun initSearchDialog() {
        mBinding.searchDialog.apply {
            mSearchDialogBehavior = BottomSheetBehavior.from(root)
            mSearchDialogBehavior.apply {
                skipCollapsed = true
                isDraggable = false
            }
            recentRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration(){
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.top = dp2px(12)
                        outRect.bottom = dp2px(12)
                        outRect.left = dp2px(12)
                        outRect.right = dp2px(12)
                    }
                })
                PagerSnapHelper().attachToRecyclerView(this)
            }
        }
    }

    private fun startBackPressCountTimer() {
        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            mBackPressCount = 0
        }
    }

    fun showSearchDialog() {
        mSearchDialogBehavior.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            getRecentPosts()
        }
    }

    fun hideSearchDialog() {
        mSearchDialogBehavior.apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }
    }

    private fun isSearchDialogVisible(): Boolean {
        return mSearchDialogBehavior.state == BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getRecentPosts() {
        lifecycleScope.launch {
            // TEST CODE START
            val jobGroupList = arrayListOf(JobGroup(0, "IT/인터넷"), JobGroup(0, "디자인"))
            val post1 = RecentPost(0, "title 1", "0000.00.00 00:00", "취업준비", 29, "남자", jobGroupList)
            val post2 = RecentPost(0, "title 2", "0000.00.00 00:00", "n차합격", 29, "여자", jobGroupList)
            val post3 = RecentPost(0, "title 3", "0000.00.00 00:00", "취업준비", 29, "남자", jobGroupList)
            val post4 = RecentPost(0, "title 4", "0000.00.00 00:00", "최종합격", 29, "여자", jobGroupList)
            val recentPosts = arrayListOf(post1, post2, post3, post4)
            mBinding.searchDialog.recentRecyclerView.adapter = RecentPostAdapter(recentPosts)
            // TEST CODE END
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