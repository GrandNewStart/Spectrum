package com.spectrum.spectrum.src.activities.main.fragments.myPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.src.activities.main.fragments.myPost.adapters.PostAdapter
import com.spectrum.spectrum.src.activities.main.fragments.myPost.models.JobGroup
import com.spectrum.spectrum.src.activities.main.fragments.myPost.models.Post
import kotlinx.coroutines.launch

class MyPostViewModel: ViewModel() {

    private var mIsDataLoaded = false
    private var mPosts = ArrayList<Post>()

    fun bindViews(fragment: MyPostFragment) {
        fragment.mBinding.apply {
            postRecyclerView.adapter = PostAdapter(mPosts)
        }

        if (!mIsDataLoaded) {
            mIsDataLoaded = true
            getMyPosts(fragment)
        }
    }

    fun backButtonAction(fragment: MyPostFragment) {
        fragment.findNavController().popBackStack()
    }

    private fun getMyPosts(fragment: MyPostFragment) {
        viewModelScope.launch {
            // TEST CODE START
                val jobGroups = arrayListOf(JobGroup(0, "서비스"), JobGroup(0, "교육"), JobGroup(0, "특수계층/공공"))
                val post1 = Post(0, "내 스펙 좀 보소", "0000.00.00 00:00", "취업준비", 25, "여성", jobGroups)
                val post2 = Post(0, "내 스펙 좀 보소", "0000.00.00 00:00", "n차합격", 25, "여성", jobGroups)
                mPosts.add(post1)
                mPosts.add(post2)
                mPosts.add(post1)
                mPosts.add(post2)
                fragment.mBinding.posts = mPosts
            // TEST CODE END
        }
    }

}