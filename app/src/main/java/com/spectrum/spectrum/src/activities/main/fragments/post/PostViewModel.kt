package com.spectrum.spectrum.src.activities.main.fragments.post

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.databinding.FragmentPostBinding
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.models.CertItem
import com.spectrum.spectrum.src.models.EduItem
import com.spectrum.spectrum.src.models.Evaluation
import com.spectrum.spectrum.src.models.ExpItem

class PostViewModel: ViewModel() {

    var mIsDataLoaded = false
    private var mUserName: String? = null
    private var mUserThumbnail: String? = null
    private var mDate: String? = null
    private var mUserInfo = ArrayList<String>()
    private var mPostTitle: String? = null
    private var mPostContent: String? = null
    private var mUserEduItems = ArrayList<EduItem>()
    private var mExpItems = ArrayList<ExpItem>()
    private var mCertItems = ArrayList<CertItem>()
    private var mOtherSpecs: String? = null

    private var mTopFiveResponses = ArrayList<Evaluation>()
    private var mEduResponses = ArrayList<Evaluation>()
    private var mExpResponses = ArrayList<Evaluation>()
    private var mCertResponses = ArrayList<Evaluation>()
    private var mOtherResponses = ArrayList<Evaluation>()

    var mEduResponse: Evaluation? = null
    var mExpResponse: Evaluation? = null
    var mCertResponse: Evaluation? = null
    var mOtherResponse: Evaluation? = null

    fun backButtonAction(fragment: PostFragment) {
        fragment.findNavController().popBackStack()
    }

    fun markButtonAction(fragment: PostFragment) {
        fragment.showToast(Constants.under_construction)
    }

    fun leaveResponseAction(fragment: PostFragment) {
        Log.d(TAG, "--- LEAVE MY RESPONSE ---")
        Log.d(TAG, "    EDU: ${mEduResponse?.name}")
        Log.d(TAG, "    EXP: ${mExpResponse?.name}")
        Log.d(TAG, "    CERT: ${mCertResponse?.name}")
        Log.d(TAG, "    OTHER: ${mOtherResponse?.name}")
    }

    fun bindViews(binding: FragmentPostBinding) {
        if (mIsDataLoaded) {
            binding.apply {
                userName = mUserName
                userThumbnail = mUserThumbnail
                date = mDate
                userInfo = mUserInfo
                postTitle = mPostTitle
                postContent = mPostContent

                educations = mUserEduItems
                experiences = mExpItems
                certifications = mCertItems
                otherSpecs = mOtherSpecs

                topFiveResponses = mTopFiveResponses

                educationResponses = mEduResponses

                experienceResponses = mExpResponses

                certificationResponses = mCertResponses

                otherSpecsResponses = mOtherResponses
            }
            return
        }
        mIsDataLoaded = true
        binding.apply {
            // TEST CODE START
                mUserName = "스펙왕"
                mUserThumbnail = "https://lh3.googleusercontent.com/ogw/ADea4I4c2HPqSR5Mw5MPQLKRoqBCVaEWBbmCYFm0phUv=s64-c-mo"
                mDate = "06/28 20:25"
                mUserInfo = arrayListOf("취업준비", "23세", "여성", "IT/인터넷", "디자인")
                mPostTitle = "졸업 전 대기업 인턴"
                mPostContent = "UXUI 직무를 희망하는데, \n대기업 인턴하려면 어떤 스펙을 더 쌓아야 할까요?"
                mUserEduItems = arrayListOf(
                    EduItem("똥통대학교", "수도권", "대학교(4년제)", "재학중", "산업디자인학과", 3.63, 4.5),
                    EduItem("똥통대학교", "수도권", "대학교(4년제)", "재학중", "산업디자인학과", 3.63, 4.5))
                mExpItems = arrayListOf(
                    ExpItem("정승네트워크", "UIUX 디자이너", "인턴", "2021.03.03", "2021.06.06"),
                    ExpItem("정승네트워크", "UIUX 디자이너", "인턴", "2021.03.03", "2021.06.06"),
                    ExpItem("정승네트워크", "UIUX 디자이너", "인턴", "2021.03.03", "2021.06.06"))
                mCertItems = arrayListOf(
                    CertItem("TOEIC", "910점"),
                    CertItem("GTQ", "1급"))
                mOtherSpecs = "저는요, 할 줄 아는게 아무것두 없지만 딱 한가지 내세울건 바로 문법 맞추기에오. 띠어쓰기 똑바로 안 하면 갱장히 불 편하구요. 단어도 마춤뻡 틀리면 갱장히 불 편한 사람 이에오."

                mTopFiveResponses.add(Evaluation(0, "인턴 경험 좋아요", 18))
                mTopFiveResponses.add(Evaluation(1, "공모전은 어때요?", 13))
                mTopFiveResponses.add(Evaluation(2, "대외활동은 어때요?", 9))
                mTopFiveResponses.add(Evaluation(3, "어학 점수 좋아요", 8))
                mTopFiveResponses.add(Evaluation(4, "직무 자격증 좋아요", 6))

                mEduResponses.add(Evaluation(0, "학점 좋아요", 5))
                mEduResponses.add(Evaluation(0, "학점 좋아요", 0))
                mEduResponses.add(Evaluation(1, "학점 보통", 0))
                mEduResponses.add(Evaluation(2, "학점 UP", 0))

                mExpResponses.add(Evaluation(0, "인턴 경험 좋아요", 18))
                mExpResponses.add(Evaluation(1, "인턴 경험 UP", 5))
                mExpResponses.add(Evaluation(0, "인턴 경험은 어때요?", 0))

                mCertResponses.add(Evaluation(0, "어학 점수 좋아요", 8))
                mCertResponses.add(Evaluation(1, "직무 자격증 좋아요", 6))
                mCertResponses.add(Evaluation(0, "어학 점수 UP", 0))
                mCertResponses.add(Evaluation(1, "직무 자격증은 어때요?", 0))
                mCertResponses.add(Evaluation(2, "직무 자격증 UP", 0))

                mOtherResponses.add(Evaluation(0, "공모전의 어때요?", 13))
                mOtherResponses.add(Evaluation(1, "대외활동은 어때요?", 9))
                mOtherResponses.add(Evaluation(0, "공모전 좋아요", 0))
                mOtherResponses.add(Evaluation(1, "대외활동은 좋아요", 0))
                mOtherResponses.add(Evaluation(2, "공모전 UP", 0))
                mOtherResponses.add(Evaluation(3, "대외활동 UP", 0))

                userName = mUserName
                userThumbnail = mUserThumbnail
                date = mDate
                userInfo = mUserInfo
                postTitle = mPostTitle
                postContent = mPostContent

                educations = mUserEduItems
                experiences = mExpItems
                certifications = mCertItems
                otherSpecs = mOtherSpecs

                topFiveResponses = mTopFiveResponses

                educationResponses = mEduResponses

                experienceResponses = mExpResponses

                certificationResponses = mCertResponses

                otherSpecsResponses = mOtherResponses
            // TEST CODE END
        }
    }

    companion object {
        val TAG = PostViewModel::class.java.simpleName.toString()
    }

}