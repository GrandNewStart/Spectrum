package com.spectrum.spectrum.src.activities.main.fragments.companyInfo

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.Spanned
import android.text.SpannedString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.text.toSpannable
import androidx.core.text.toSpanned
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.companyInfo.dialogs.SubmitSpecDialog
import com.spectrum.spectrum.src.activities.main.fragments.companyInfo.interfaces.CompanyInfoApi
import com.spectrum.spectrum.src.activities.main.fragments.companyInfo.models.Company
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.models.Analysis
import com.spectrum.spectrum.src.models.Info
import com.spectrum.spectrum.src.models.Spec
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class CompanyInfoViewModel: ViewModel() {

    private val mService = retrofit.create(CompanyInfoApi::class.java)

    private var mIsDataLoaded = false
    private var mDidReachEnd = false
    private var mIsLoading = false
    var view: View? = null
    var mCompanyId: Int? = null
    private var mCompany: Company? = null
    private var mAnalysis = ArrayList<Analysis>()
    private var mSpecs = ArrayList<Spec>()
    private var mPage = 0

    fun bindViews(fragment: CompanyInfoFragment) {
        fragment.mBinding.apply {
            if (mAnalysis.size > 0) analysis1 = mAnalysis[0]
            if (mAnalysis.size > 1) analysis2 = mAnalysis[1]
            if (mAnalysis.size > 2) analysis3 = mAnalysis[2]
            if (mAnalysis.size > 3) analysis4 = mAnalysis[3]
            company = mCompany
            specs = mSpecs
        }

        if (!mIsDataLoaded) {
            mIsDataLoaded = true
            getCompanyInfo(fragment)
            getSpecs(fragment)
        }
    }

    private fun testAnalysis(fragment: CompanyInfoFragment) {
        mAnalysis.add(Analysis(0, "3.79", "학점", 94))
        mAnalysis.add(Analysis(0, "수도권(4년제)", "학력", 88))
        mAnalysis.add(Analysis(0, "1.2회", "경력", 23))
        mAnalysis.add(Analysis(0, "1.9개", "자격증", 57))
        mAnalysis.add(Analysis(0, "870점", "토익", 73))
        val spec = Spec(0, "https://lh3.googleusercontent.com/a-/AOh14Gh3kcrod_xVhGf7kUci563N0l7mAcWJv1EeBigXng=s288-p-rw-no","스펙왕", "06/28 20:25 업데이트됨",
            arrayListOf(Info(0, "23세"), Info(0, "여성"), Info(0, "IT/인터넷"), Info(0, "디자인")),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            ""
        )
        mSpecs.add(spec)
        mSpecs.add(spec)
        mSpecs.add(spec)
        mSpecs.add(spec)
        mSpecs.add(spec)
        mSpecs.add(spec)
        mSpecs.add(spec)
        mSpecs.add(spec)
        fragment.mBinding.apply {
            if (mAnalysis.size > 0) analysis1 = mAnalysis[0]
            if (mAnalysis.size > 1) analysis2 = mAnalysis[1]
            if (mAnalysis.size > 2) analysis3 = mAnalysis[2]
            if (mAnalysis.size > 3) analysis4 = mAnalysis[3]
            specs = mSpecs
        }
    }

    fun backButtonAction(fragment: CompanyInfoFragment) {
        fragment.findNavController().popBackStack()
    }

    fun favoriteButtonAction(fragment: CompanyInfoFragment) {
        if (mCompany?.isMine == "Y") {
            favoriteCompany(fragment)
        }
        else {
            SubmitSpecDialog(fragment)
                .setOnDoneListener { didEnter ->
                    favoriteCompany(fragment)
                }
                .show()
        }
    }

    fun proceedToSpec(id: Int, fragment: CompanyInfoFragment) {
        fragment.findNavController().navigate(R.id.company_info_to_spec, bundleOf("id" to id))
    }

    fun refresh(fragment: CompanyInfoFragment) {
        mPage = 0
        mCompany = null
        mDidReachEnd = false
        mSpecs.clear()
        mAnalysis.clear()
        fragment.mBinding.apply {
            company = mCompany
            specs = mSpecs
            if (mAnalysis.size > 0) analysis1 = mAnalysis[0]
            if (mAnalysis.size > 1) analysis2 = mAnalysis[1]
            if (mAnalysis.size > 2) analysis3 = mAnalysis[2]
            if (mAnalysis.size > 3) analysis4 = mAnalysis[3]
        }
        getCompanyInfo(fragment)
        getSpecs(fragment)
    }

    private fun getCompanyInfo(fragment: CompanyInfoFragment) {
        val id = mCompanyId ?: return
        viewModelScope.launch {
            mService.getCompanyInfo(id).apply {
                if (isSuccess) {
                    fragment.mBinding.apply {
                        mCompany = result
                        company = mCompany
                    }
                }
                else {
                    Log.e(TAG, "---> COMPANY INFO FETCH FAILURE: $message")
                    fragment.showToast(Constants.request_failed)
                    fragment.findNavController().popBackStack()
                }
            }
        }
    }

    private fun getSpecs(fragment: CompanyInfoFragment) {
        if (mDidReachEnd) return
        if (mIsLoading) return

        viewModelScope.launch {

        }
    }

    private fun favoriteCompany(fragment: CompanyInfoFragment) {
        val id = mCompanyId ?: return
        viewModelScope.launch {
            mService.favoriteCompany(id).apply {
                if (isSuccess) {
                    mCompany?.apply {
                        if (isMine == "Y") {
                            isMine = "N"
                            fragment.showToast(Constants.spec_withdrawn)
                        }
                        else {
                            isMine = "Y"
                            fragment.showToast(Constants.spec_updated)
                        }
                        fragment.mBinding.company = this
                    }
                }
                else {
                    Log.e(TAG, "---> FAVORITE COMPANY FAILURE: $message")
                    fragment.showToast(Constants.request_failed)
                }
            }
        }
    }

    companion object {
        val TAG = CompanyInfoViewModel::class.java.toString()
    }

}