package com.spectrum.spectrum.src.activities.main.fragments.home

import android.view.View
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spectrum.spectrum.databinding.FragmentHomeBinding
import com.spectrum.spectrum.src.models.Duty
import com.spectrum.spectrum.src.models.Post
import com.spectrum.spectrum.src.models.Spec
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.models.JobGroup

class HomeViewModel: ViewModel() {

    private val mSearchFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        mSearchHasFocusLiveData.value = hasFocus
    }

    private var mHottestPosts = ArrayList<Post>().apply {
        val specs = arrayListOf(Spec(0, "최종합격"), Spec(1, "26세"), Spec(2, "여성"), Spec(3, "IT/인터넷"), Spec(4, "디자인"))
        add(Post("", "네이버 최합", "06/25 16:25", 40, 829, specs))
        add(Post("", "네이버 최합", "06/25 16:25", 40, 829, specs))
        add(Post("", "네이버 최합", "06/25 16:25", 40, 829, specs))
        add(Post("", "네이버 최합", "06/25 16:25", 40, 829, specs))
        add(Post("", "네이버 최합", "06/25 16:25", 40, 829, specs))
    }
    private var mLatestPosts = ArrayList<Post>().apply {
        val specs = arrayListOf(Spec(0, "취업준비"), Spec(1, "26세"), Spec(2, "여성"), Spec(3, "IT/인터넷"), Spec(4, "디자인"))
        add(Post("", "졸업 전 대기업 인턴", "06/25 16:25", 12, 0, specs))
        add(Post("", "졸업 전 대기업 인턴", "06/25 16:25", 12, 0, specs))
        add(Post("", "졸업 전 대기업 인턴", "06/25 16:25", 12, 0, specs))
        add(Post("", "졸업 전 대기업 인턴", "06/25 16:25", 12, 0, specs))
        add(Post("", "졸업 전 대기업 인턴", "06/25 16:25", 12, 0, specs))
    }
    val mSearchHasFocusLiveData = MutableLiveData<Boolean>()

    fun bindViews(binding: FragmentHomeBinding) {
        binding.apply {
            //searchFocusListener = mSearchFocusChangeListener
            hottest = mHottestPosts
            latest = mLatestPosts
        }
    }

    fun backButtonAction(fragment: HomeFragment, editText: EditText) {
        if (editText.hasFocus()) {
            fragment.showKeyboard(editText, false)
            editText.clearFocus()
        }
    }

    fun fabAction(fragment: HomeFragment) {
        fragment.showToast(Constants.under_construction)
    }

    companion object {
        val TAG = HomeViewModel::class.java.simpleName
    }

}