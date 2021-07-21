package com.spectrum.spectrum.src.activities.main.fragments.companyInfo

import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentCompanyInfoBinding
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.customs.CircleView
import com.spectrum.spectrum.src.models.Analysis
import com.spectrum.spectrum.src.models.Info
import com.spectrum.spectrum.src.models.Spec

class CompanyInfoViewModel: ViewModel() {

    private var mAnalysis = ArrayList<Analysis>()
    private var mSpecs = ArrayList<Spec>()

    fun bindViews(binding: FragmentCompanyInfoBinding) {
        binding.apply {
            // TEST CODE START
                mAnalysis.add(Analysis(0, "3.79", "학점", 94))
                mAnalysis.add(Analysis(0, "수도권(4년제)", "학력", 88))
                mAnalysis.add(Analysis(0, "1.2회", "경력", 23))
                mAnalysis.add(Analysis(0, "1.9개", "자격증", 57))
                mAnalysis.add(Analysis(0, "870점", "토익", 73))
                for (i in 0 until mAnalysis.size) {
                    createCircle(this, mAnalysis[i], i)
                }
                val spec = Spec("", "스펙왕", "06/28 20:25 업데이트됨",
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
                specs = mSpecs
            // TEST CODE END
        }
    }

    fun backButtonAction(fragment: CompanyInfoFragment) {
        fragment.findNavController().popBackStack()
    }

    private fun createCircle(binding: FragmentCompanyInfoBinding, analysis: Analysis, index: Int) {
        binding.apply {
            CircleView(root.context).apply {
                val p = analysis.percent
                var fontSize = 0
                val params = ConstraintLayout.LayoutParams(dp2px(80+p), dp2px(80+p))
                when(p) {
                    in 0..25 -> { fontSize = 14 }
                    in 25..50 -> { fontSize = 16 }
                    in 50..75 -> { fontSize = 18 }
                    in 75..100 -> { fontSize = 20 }
                }
                when(index) {
                    0 -> {
                        params.leftToLeft = R.id.graph_view
                        params.topToTop = R.id.graph_view
                        params.topMargin = dp2px(16)
                        params.marginStart = dp2px(16)
                    }
                    1 -> {
                        params.leftToLeft = R.id.graph_view
                        params.bottomToBottom = R.id.graph_view
                        params.bottomMargin = dp2px(16)
                        params.marginStart = dp2px(16)
                    }
                    2 -> {
                        params.leftToLeft = R.id.graph_view
                        params.rightToRight = R.id.graph_view
                        params.topToTop = R.id.graph_view
                        params.bottomToBottom = R.id.graph_view
                    }
                    3 -> {
                        params.rightToRight = R.id.graph_view
                        params.topToTop = R.id.graph_view
                        params.topMargin = dp2px(16)
                        params.marginEnd = dp2px(16)
                    }
                    4 -> {
                        params.rightToRight = R.id.graph_view
                        params.bottomToBottom = R.id.graph_view
                        params.bottomMargin = dp2px(16)
                        params.marginEnd = dp2px(16)
                    }
                }
                layoutParams = params
                setText1(analysis.data, dp2px(fontSize).toFloat())
                setText2(analysis.type, dp2px(fontSize-2).toFloat())
                setText3("$p%가 보유", dp2px(fontSize-2).toFloat())
                graphView.addView(this)
            }
        }
    }

}