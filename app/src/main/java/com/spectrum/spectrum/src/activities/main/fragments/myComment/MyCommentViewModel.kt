package com.spectrum.spectrum.src.activities.main.fragments.myComment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.src.activities.main.fragments.myComment.adapters.PostAdapter
import com.spectrum.spectrum.src.activities.main.fragments.myComment.models.Post
import com.spectrum.spectrum.src.activities.main.fragments.myComment.models.JobGroup
import kotlinx.coroutines.launch

class MyCommentViewModel: ViewModel() {

    private var mIsDataLoaded = false
    private var mPosts = ArrayList<Post>()

    fun bindViews(fragment: MyCommentFragment) {
        fragment.mBinding.apply {
            postRecyclerView.adapter = PostAdapter(mPosts)
        }

        if (!mIsDataLoaded) {
            mIsDataLoaded = true
            getMyEvaluations(fragment)
        }
    }

    fun backButtonAction(fragment: MyCommentFragment) {
        fragment.findNavController().popBackStack()
    }

    private fun getMyEvaluations(fragment: MyCommentFragment) {
        viewModelScope.launch {
            // TEST CODE START
            val jobGroups = arrayListOf(JobGroup(0, "서비스"), JobGroup(0, "교육"), JobGroup(0, "특수계층/공공"))
            val post1 = Post(211, "내 스펙 좀 보소", "0000.00.00 00:00", "취업준비", 25, "여성", jobGroups)
            val post2 =Post(210, "내 스펙 좀 보소", "0000.00.00 00:00", "n차합격", 25, "여성", jobGroups )
            mPosts.add(post1)
            mPosts.add(post2)
            mPosts.add(post1)
            mPosts.add(post2)
            fragment.mBinding.postRecyclerView.adapter?.apply {
                for (i in 0 until mPosts.size) notifyItemInserted(i)
            }
            // TEST CODE END
        }
    }
}