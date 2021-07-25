package com.spectrum.spectrum.src.activities.main.fragments.spec

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.models.*
import kotlinx.coroutines.launch
import java.lang.Exception

class SpecViewModel: ViewModel() {

    private var mSpec: Spec? = null

    fun bindViews(fragment: SpecFragment) {
        mSpec?.apply {
            fragment.mBinding.apply {
                spec = mSpec
            }
            return
        }
        // TEST CODE START
            val userInfo = arrayListOf(Info(0, "23세"), Info(0, "여성"), Info(0, "IT/인터넷"), Info(0, "디자인"))
            val edu = Education(Location(1,"수도권"), Graduate(2, "재학중"), Degree(4, "대학교(4년제)"), "무슨 학교", "무슨 전공", 3.8)
            val educations = arrayListOf(edu, edu, edu, edu)
            val exp = Experience("무슨 기업", "IT/인터넷", "사원", "0000.00.00", "0000.00.00")
            val experiences = arrayListOf(exp, exp, exp)
            val licences = arrayListOf(License("무슨 시험", "무슨 등급"), License("무슨 시험", "무슨 등급"))
            mSpec = Spec("",
                "https://lh3.googleusercontent.com/a-/AOh14Gh3kcrod_xVhGf7kUci563N0l7mAcWJv1EeBigXng=s288-p-rw-no",
                "스펙킹",
                "07/22 07:30에 업데이트 됨",
                userInfo,
                educations,
                experiences,
                licences,
                "기타 스펙")
            fragment.mBinding.spec = mSpec
        // TEST CODE END
        getSpec(fragment)
    }

    fun backButtonAction(fragment: SpecFragment) {
        fragment.findNavController().popBackStack()
    }

    private fun getSpec(fragment: SpecFragment) {
        try {
            viewModelScope.launch {

            }
        }
        catch (e: Exception) {
            Log.e(TAG, "---> SPEC FETCH FAILURE: $e")
            fragment.showToast(Constants.request_failed)
        }
    }

    companion object {
        val TAG = SpecViewModel::class.java.toString()
    }

}