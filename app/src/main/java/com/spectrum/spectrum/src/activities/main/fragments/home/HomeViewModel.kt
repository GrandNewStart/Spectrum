package com.spectrum.spectrum.src.activities.main.fragments.home

import android.util.Log
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.MainActivity
import com.spectrum.spectrum.src.activities.main.fragments.home.adapters.PostAdapter
import com.spectrum.spectrum.src.activities.main.fragments.home.dialogs.JobGroupDialog
import com.spectrum.spectrum.src.activities.main.fragments.home.interfaces.HomeApi
import com.spectrum.spectrum.src.activities.main.fragments.home.models.JobGroup
import com.spectrum.spectrum.src.activities.main.fragments.home.models.Post
import com.spectrum.spectrum.src.config.Constants.request_failed
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.models.PostEvent
import com.spectrum.spectrum.src.models.RefreshEvent
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeViewModel: ViewModel() {

    // Api
    private val mService = retrofit.create(HomeApi::class.java)

    // Data
    var mIsDataLoaded = false
    var mDataEnded = false
    var mIsLoading = false
    var mPostId: Int? = null
    private var mShouldRefresh = false
    private var mPage = 0
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
        if (mShouldRefresh) {
            refresh(fragment)
            mShouldRefresh = false
        }

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
            editText.text = null
        }
    }

    fun showJobGroupDialog(fragment: HomeFragment) {
        var job1: com.spectrum.spectrum.src.models.JobGroup? = null
        var job2: com.spectrum.spectrum.src.models.JobGroup? = null

        mJobGroup1?.let {
            job1 = com.spectrum.spectrum.src.models.JobGroup(it.id, it.data)
        }
        mJobGroup2?.let {
            job2 = com.spectrum.spectrum.src.models.JobGroup(it.id, it.data)
        }

        JobGroupDialog(fragment.requireContext())
            .setPreSelectedItems(job1, job2)
            .setOnSaveListener{ first, second ->
                mJobGroup1 = null
                mJobGroup2 = null
                first?.let { mJobGroup1 = JobGroup(it.id, it.name) }
                second?.let { mJobGroup2 = JobGroup(it.id, it.name) }
                fragment.mBinding.apply {
                    jobGroup1 = mJobGroup1
                    jobGroup2 = mJobGroup2
                }
                refresh(fragment)
            }.show()
    }

    fun fabAction(fragment: HomeFragment) {
        fragment.findNavController().navigate(R.id.home_to_create_post)
    }

    fun proceedToPost(fragment: HomeFragment, id: Int) {
        fragment.findNavController().navigate(R.id.home_to_post, bundleOf("id" to id))
    }

    fun proceedToSearch(fragment: HomeFragment, keyword: String) {
        (fragment.activity as MainActivity).hideSearchDialog()
        fragment.findNavController().navigate(R.id.home_to_search, bundleOf("keyword" to keyword))
    }

    fun refresh(fragment: HomeFragment) {
        mPage = 0
        mDataEnded = false
        mHottestPosts.clear()
        mLatestPosts.clear()
        fragment.mBinding.hottest = mHottestPosts
        fragment.mBinding.latest = mLatestPosts
        getHomePage(fragment)
    }

    fun getHomePage(fragment: HomeFragment) {
        if (mIsLoading) return
        if (mDataEnded) return

        fragment.showProgressDialog(true)
        mIsLoading = true

        viewModelScope.launch {
            mJobGroup1?.let { first -> mJobGroup2?.let { second ->
                mService.getHomePage(mPage, first.id, second.id).apply {
                    fragment.showProgressDialog(false)
                    mIsLoading = false
                    if (isSuccess) {
                        Log.d(TAG, "---> HOME PAGE LOAD(${first.id},${second.id}) SUCCESS($mPage)")
                        mPage++
                        mDataEnded = result.isEmpty()
                        (fragment.mBinding.latestRecyclerView.adapter as PostAdapter).addItems(result)
                        return@launch
                    }
                    Log.d(TAG, "---> HOME PAGE LOAD(${first.id},${second.id}) FAILURE: $message")
                    fragment.showToast(request_failed)
                    return@launch
                }
            }}
            mJobGroup1?.let { first ->
                mService.getHomePage(mPage, first.id).apply {
                    fragment.showProgressDialog(false)
                    mIsLoading = false
                    if (isSuccess) {
                        Log.d(TAG, "---> HOME PAGE LOAD(${first.id}) SUCCESS($mPage)")
                        mPage++
                        mDataEnded = result.isEmpty()
                        (fragment.mBinding.latestRecyclerView.adapter as PostAdapter).addItems(result)
                        return@launch
                    }
                    Log.d(TAG, "---> HOME PAGE LOAD(${first.id}) FAILURE: $message")
                    fragment.showToast(request_failed)
                    return@launch
                }
            }
            mService.getHomePage(mPage).apply {
                fragment.showProgressDialog(false)
                mIsLoading = false
                if (isSuccess) {
                    Log.d(TAG, "---> HOME PAGE LOAD SUCCESS($mPage)")
                    mPage++
                    mDataEnded = result.isEmpty()
                    (fragment.mBinding.latestRecyclerView.adapter as PostAdapter).addItems(result)
                    return@launch
                }
                Log.d(TAG, "---> HOME PAGE LOAD FAILURE: $message")
                fragment.showToast(request_failed)
                return@launch
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receivePostEvent(event: PostEvent) {
        mPostId = event.id
        mShouldRefresh = true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveRefreshEvent(event: RefreshEvent) {
        mShouldRefresh = true
    }

    companion object {
        val TAG = HomeViewModel::class.java.simpleName.toString()
    }

}