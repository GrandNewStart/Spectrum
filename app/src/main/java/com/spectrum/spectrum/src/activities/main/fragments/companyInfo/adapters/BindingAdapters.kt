package com.spectrum.spectrum.src.activities.main.fragments.companyInfo.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.dp2px
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

}