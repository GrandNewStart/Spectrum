package com.spectrum.spectrum.src.activities.main.fragments.myPost

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.src.activities.main.fragments.myPost.adapters.PostAdapter
import com.spectrum.spectrum.src.activities.main.fragments.myPost.interfaces.MyPostApi
import com.spectrum.spectrum.src.activities.main.fragments.myPost.models.JobGroup
import com.spectrum.spectrum.src.activities.main.fragments.myPost.models.Post
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.models.RefreshEvent
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MyPostViewModel: ViewModel() {

    private val mService = retrofit.create(MyPostApi::class.java)

    private var mIsDataLoaded = false
    private var mDidReachEnd = false
    private var mIsLoading = false
    private var mShouldReload = false
    private var mPage = 0
    private var mPosts = ArrayList<Post>()

    init {
        EventBus.getDefault().register(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.getDefault().unregister(this)
    }

    fun bindViews(fragment: MyPostFragment) {
        if (mShouldReload) {
            refresh(fragment)
            return
        }

        fragment.mBinding.apply {
            postRecyclerView.adapter = PostAdapter(mPosts)
            postRecyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
                if (!postRecyclerView.canScrollVertically(1)) {
                    getMyPosts(fragment)
                }
            }
        }

        if (!mIsDataLoaded) {
            mIsDataLoaded = true
            getMyPosts(fragment)
        }
    }

    fun backButtonAction(fragment: MyPostFragment) {
        fragment.findNavController().popBackStack()
    }

    fun refresh(fragment: MyPostFragment) {
        mPage = 0
        mDidReachEnd = false
        mShouldReload = false
        mPosts.clear()
        fragment.mBinding.postRecyclerView.adapter = PostAdapter(mPosts)
        getMyPosts(fragment)
    }

    private fun getMyPosts(fragment: MyPostFragment) {
        if (mDidReachEnd) return
        if (mIsLoading) return

        viewModelScope.launch {
            mIsLoading = true
            mService.getMyPosts(mPage).apply {
                mIsLoading = false
                if (isSuccess) {
                    if (result.isEmpty()) {
                        mDidReachEnd = true
                        return@launch
                    }
                    mPage++
                    result.forEach { mPosts.add(it) }
                    fragment.mBinding.postRecyclerView.apply {
                        (adapter as PostAdapter).addNewItems(result)
                    }
                }
                else {
                    Log.e(TAG, "---> MY POST FETCH FAILURE: $message")
                    fragment.showToast(Constants.request_failed)
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveRefreshEvent(event: RefreshEvent) {
        mShouldReload = true
    }

    companion object {
        val TAG = MyPostViewModel::class.java.simpleName.toString()
    }

}