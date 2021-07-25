package com.spectrum.spectrum.src.activities.main.fragments.companyInfo.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.companyInfo.CompanyInfoFragment
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.customs.CircleView
import com.spectrum.spectrum.src.models.Analysis
import com.spectrum.spectrum.src.models.Spec

object BindingAdapters {

    @BindingAdapter("company_info_specs")
    @JvmStatic
    fun bindSpecRecyclerView(recyclerView: RecyclerView, items: ArrayList<Spec>?) {
        recyclerView.apply {
            if (adapter == null) {
                adapter = SpecAdapter(items ?: arrayListOf())
                return
            }
            adapter?.notifyDataSetChanged()
        }
    }

    @BindingAdapter("company_info_spec")
    @JvmStatic
    fun bindSpecItem(view: View, spec: Spec?) {
        spec?.let {
            when(view.id) {
                R.id.title_text -> {
                    val t = "${it.userName} ${Constants.spec_of}"
                    (view as TextView).text = t
                }
                R.id.update_time_text -> {
                    (view as TextView).text = it.update
                }
                R.id.user_info_chip_group -> {
                    (view as ChipGroup).apply {
                        it.userInfo.forEach { info ->
                            val chip = Chip(context).apply {
                                val drawable = ChipDrawable.createFromAttributes(context, null, 0, R.style.DefaultChip)
                                setChipDrawable(drawable)
                                text = info.name
                                chipMinHeight = dp2px(32).toFloat()
                            }
                            addView(chip)
                        }
                    }
                }
                else -> {}
            }
        }
    }

    @BindingAdapter("company_info_analysis")
    @JvmStatic
    fun bindSpecGraphs(view: CircleView, analysis: Analysis?) {
        analysis?.let {
            view.apply {
                val percent = it.percent
                var fontSize = 0
                when(percent) {
                    in 0..25 -> { fontSize = 14 }
                    in 25..50 -> { fontSize = 16 }
                    in 50..75 -> { fontSize = 18 }
                    in 75..100 -> { fontSize = 20 }
                }
                setText1(it.data, dp2px(fontSize).toFloat())
                setText2(it.type, dp2px(fontSize-2).toFloat())
                setText3("$percent%가 보유", dp2px(fontSize-2).toFloat())
                val params = ConstraintLayout.LayoutParams(dp2px(80+percent), dp2px(80+percent))
                when (view.id) {
                    R.id.circle_1 -> {
                        params.rightToLeft = R.id.v_guide
                        params.bottomToTop = R.id.h_guide
                        params.bottomMargin = dp2px(8)
                        params.rightMargin = dp2px(8)
                    }
                    R.id.circle_2 -> {
                        params.leftToLeft = R.id.v_guide
                        params.bottomToTop = R.id.h_guide
                        params.bottomMargin = dp2px(8)
                        params.leftMargin = dp2px(8)
                    }
                    R.id.circle_3 -> {
                        params.rightToLeft = R.id.v_guide
                        params.topToBottom = R.id.h_guide
                        params.topMargin = dp2px(8)
                        params.rightMargin = dp2px(8)
                    }
                    R.id.circle_4 -> {
                        params.leftToLeft = R.id.v_guide
                        params.topToBottom = R.id.h_guide
                        params.topMargin = dp2px(8)
                        params.leftMargin = dp2px(8)
                    }
                }
                layoutParams = params
            }
        }
    }

}