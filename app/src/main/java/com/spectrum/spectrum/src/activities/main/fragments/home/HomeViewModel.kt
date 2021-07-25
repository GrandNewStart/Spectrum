package com.spectrum.spectrum.src.activities.main.fragments.home

import android.graphics.Color
import android.util.Log
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.home.adapters.PostAdapter
import com.spectrum.spectrum.src.activities.main.fragments.home.dialogs.JobGroupDialog
import com.spectrum.spectrum.src.activities.main.fragments.home.interfaces.HomeApi
import com.spectrum.spectrum.src.activities.main.fragments.home.models.JobGroup
import com.spectrum.spectrum.src.activities.main.fragments.home.models.Post
import com.spectrum.spectrum.src.config.Constants.request_failed
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.models.PostEvent
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeViewModel: ViewModel() {

    private val mService = retrofit.create(HomeApi::class.java)

    var mIsDataLoaded = false
    var mDataEnded = false
    var mIsLoading = false
    var mPostId: Int? = null
    private var mPage = 1
    private var mHottestPosts = ArrayList<Post>()
    private var mLatestPosts = ArrayList<Post>()
    var mJobGroup1: JobGroup? = null
    var mJobGroup2: JobGroup? = null

    init {
        EventBus.getDefault().register(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.getDefault().unregister(this)
    }

    fun bindViews(fragment: HomeFragment) {
        fragment.mBinding.apply {
            jobGroup1 = mJobGroup1
            jobGroup2 = mJobGroup2
            hottest = mHottestPosts
            latest = mLatestPosts

            mPostId?.let {
                fragment.findNavController().navigate(R.id.home_to_post, bundleOf("id" to it))
                mPostId = null
            }
        }
        if (!mIsDataLoaded) {
            mIsDataLoaded = true
            getHomePage(fragment)
        }
    }

    fun backButtonAction(fragment: HomeFragment, editText: EditText) {
        if (editText.hasFocus()) {
            fragment.showKeyboard(editText, false)
            editText.clearFocus()
        }
    }

    fun showJobGroupDialog(fragment: HomeFragment) {
        var job1: com.spectrum.spectrum.src.models.JobGroup? = null
        var job2: com.spectrum.spectrum.src.models.JobGroup? = null

        mJobGroup1?.let {
            job1 = com.spectrum.spectrum.src.models.JobGroup(0, it.data)
        }
        mJobGroup2?.let {
            job2 = com.spectrum.spectrum.src.models.JobGroup(0, it.data)
        }

        JobGroupDialog(fragment.requireContext())
            .setPreSelectedItems(job1, job2)
            .setOnSaveListener{ first, second ->
                first?.let { mJobGroup1 = JobGroup("", it.name) }
                second?.let { mJobGroup2 = JobGroup("", it.name) }
                fragment.mBinding.apply {
                    jobGroup1 = mJobGroup1
                    jobGroup2 = mJobGroup2
                    if (first == null && second == null) {
                        allChip.setChipBackgroundColorResource(R.color.spectrumGray2)
                        allChip.setTextColor(Color.WHITE)
                    }
                    else {
                        allChip.setChipBackgroundColorResource(R.color.clear)
                        allChip.setTextColor(Color.BLACK)
                    }
                }
            }.show()
    }

    fun showAllResults(fragment: HomeFragment) {
        if (mJobGroup1 == null && mJobGroup2 == null) return
        mJobGroup1 = null
        mJobGroup2 = null
        fragment.mBinding.apply {
            jobGroup1 = mJobGroup1
            jobGroup2 = mJobGroup2
            allChip.apply {
                setChipBackgroundColorResource(R.color.spectrumGray2)
                setTextColor(Color.WHITE)
            }
        }
    }

    fun fabAction(fragment: HomeFragment) {
        fragment.findNavController().navigate(R.id.home_to_create_post)
    }

    fun proceedToPost(fragment: HomeFragment, id: Int) {
        fragment.findNavController().navigate(R.id.home_to_post, bundleOf("id" to id))
    }

    fun getHomePage(fragment: HomeFragment) {
        if (mIsLoading) return
        if (mDataEnded) return

        fragment.showProgressDialog(true)
        mIsLoading = true
        viewModelScope.launch {
            mService.getHomePage(mPage).apply {
                fragment.showProgressDialog(false)
                mIsLoading = false
                if (isSuccess) {
                    Log.d(TAG, "---> HOME PAGE LOAD($mPage) SUCCESSS")
                    mPage++
                    mDataEnded = result.isEmpty()
                    (fragment.mBinding.latestRecyclerView.adapter as PostAdapter).addItems(result)
                    return@launch
                }
                Log.d(TAG, "---> HOME PAGE LOAD FAILURE: $message")
                fragment.showToast(request_failed)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receivePostEvent(event: PostEvent) {
        mPostId = event.id
    }

    companion object {
        val TAG = HomeViewModel::class.java.simpleName.toString()
    }

}