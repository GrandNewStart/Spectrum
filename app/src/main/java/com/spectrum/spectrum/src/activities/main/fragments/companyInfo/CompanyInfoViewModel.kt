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
import androidx.core.text.toSpannable
import androidx.core.text.toSpanned
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.companyInfo.dialogs.SubmitSpecDialog
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.models.Analysis
import com.spectrum.spectrum.src.models.Info
import com.spectrum.spectrum.src.models.Spec

@SuppressLint("StaticFieldLeak")
class CompanyInfoViewModel: ViewModel() {

    var view: View? = null
    private var mIsDataLoaded = false
    private var mCompanyName: String? = null
    private var mAnalysis = ArrayList<Analysis>()
    private var mSpecs = ArrayList<Spec>()

    fun bindViews(fragment: CompanyInfoFragment) {
        fragment.mBinding.apply {
            if (mAnalysis.size > 0) analysis1 = mAnalysis[0]
            if (mAnalysis.size > 1) analysis2 = mAnalysis[1]
            if (mAnalysis.size > 2) analysis3 = mAnalysis[2]
            if (mAnalysis.size > 3) analysis4 = mAnalysis[3]
            companyName = mCompanyName
            specs = mSpecs

            if (!mIsDataLoaded) {
                mIsDataLoaded = true
                // TEST CODE START
                    testAnalysis(fragment)
                // TEST CODE END
                (blurringText.text.toSpannable()).apply {
                    val blue = blurringText.resources.getColor(R.color.spectrumBlue, null)
                    val font = Typeface.createFromAsset(fragment.resources.assets, "roboto_bold.ttf")
                    setSpan(ForegroundColorSpan(blue), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        setSpan(TypefaceSpan(font), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    blurringText.text = this
                }
            }
        }
    }

    private fun testAnalysis(fragment: CompanyInfoFragment) {
        mCompanyName = "카카오"
        mAnalysis.add(Analysis(0, "3.79", "학점", 94))
        mAnalysis.add(Analysis(0, "수도권(4년제)", "학력", 88))
        mAnalysis.add(Analysis(0, "1.2회", "경력", 23))
        mAnalysis.add(Analysis(0, "1.9개", "자격증", 57))
        mAnalysis.add(Analysis(0, "870점", "토익", 73))
        val spec = Spec("", "https://lh3.googleusercontent.com/a-/AOh14Gh3kcrod_xVhGf7kUci563N0l7mAcWJv1EeBigXng=s288-p-rw-no","스펙왕", "06/28 20:25 업데이트됨",
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
            companyName = mCompanyName
        }
    }

    fun backButtonAction(fragment: CompanyInfoFragment) {
        fragment.findNavController().popBackStack()
    }

    fun favoriteButtonAction(fragment: CompanyInfoFragment) {
        fragment.mBinding.apply {
            if (isFavorite == true) {
                fragment.showToast(Constants.under_construction)
            }
            else {
                SubmitSpecDialog(fragment)
                    .setOnDoneListener { didSubmit ->
                        fragment.showToast(Constants.spec_updated)
                        isFavorite = true
                    }
                    .show()
            }
        }
    }

    fun proceedToSpec(fragment: CompanyInfoFragment) {
        fragment.findNavController().navigate(R.id.company_info_to_spec)
    }

    companion object {
        val TAG = CompanyInfoViewModel::class.java.toString()
    }

}