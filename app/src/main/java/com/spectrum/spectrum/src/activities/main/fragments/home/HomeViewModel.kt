package com.spectrum.spectrum.src.activities.main.fragments.home

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentHomeBinding
import com.spectrum.spectrum.src.models.Post
import com.spectrum.spectrum.src.models.Info
import com.spectrum.spectrum.src.models.JobGroup

class HomeViewModel: ViewModel() {

    var mIsDataLoaded = false
    private var mHottestPosts = ArrayList<Post>()
    private var mLatestPosts = ArrayList<Post>()
    var mJobGroup1: JobGroup? = null
    var mJobGroup2: JobGroup? = null
    var mJobGroup3: JobGroup? = null

    fun bindViews(binding: FragmentHomeBinding) {
        if (mIsDataLoaded)  {
            binding.apply {
                jobGroup1 = mJobGroup1
                jobGroup2 = mJobGroup2
                jobGroup3 = mJobGroup3
                hottest = mHottestPosts
                latest = mLatestPosts
            }
            return
        }
        mIsDataLoaded = true
        binding.apply {
            // TEST CODE START
                mHottestPosts.apply {
                    val specs = arrayListOf(
                        Info(0, "최종합격"),
                        Info(1, "26세"),
                        Info(2, "여성"),
                        Info(3, "IT/인터넷"),
                        Info(4, "디자인")
                    )
                    add(Post("", "네이버 최합", "06/25 16:25", 40, 829, specs))
                    add(Post("", "네이버 최합", "06/25 16:25", 40, 829, specs))
                    add(Post("", "네이버 최합", "06/25 16:25", 40, 829, specs))
                    add(Post("", "네이버 최합", "06/25 16:25", 40, 829, specs))
                    add(Post("", "네이버 최합", "06/25 16:25", 40, 829, specs))
                }
                mLatestPosts.apply {
                    val specs = arrayListOf(
                        Info(0, "취업준비"),
                        Info(1, "26세"),
                        Info(2, "여성"),
                        Info(3, "IT/인터넷"),
                        Info(4, "디자인")
                    )
                    add(Post("", "졸업 전 대기업 인턴", "06/25 16:25", 12, 0, specs))
                    add(Post("", "졸업 전 대기업 인턴", "06/25 16:25", 12, 0, specs))
                    add(Post("", "졸업 전 대기업 인턴", "06/25 16:25", 12, 0, specs))
                    add(Post("", "졸업 전 대기업 인턴", "06/25 16:25", 12, 0, specs))
                    add(Post("", "졸업 전 대기업 인턴", "06/25 16:25", 12, 0, specs))
                }
            jobGroup1 = mJobGroup1
            jobGroup2 = mJobGroup2
            jobGroup3 = mJobGroup3
            hottest = mHottestPosts
            latest = mLatestPosts
            // TEST CODE END
        }
    }

    fun backButtonAction(fragment: HomeFragment, editText: EditText) {
        if (editText.hasFocus()) {
            fragment.showKeyboard(editText, false)
            editText.clearFocus()
        }
    }

    fun fabAction(fragment: HomeFragment) {
        fragment.findNavController().navigate(R.id.home_to_create_post)
    }

    fun proceedToPost(fragment: HomeFragment, id: String) {
        fragment.findNavController().navigate(R.id.home_to_post)
    }

    companion object {
        val TAG = HomeViewModel::class.java.simpleName
    }

}