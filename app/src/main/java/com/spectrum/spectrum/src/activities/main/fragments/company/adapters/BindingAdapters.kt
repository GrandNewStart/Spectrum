package com.spectrum.spectrum.src.activities.main.fragments.company.adapters

import android.graphics.Rect
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.company.adapters.MyJobGroupAdapter
import com.spectrum.spectrum.src.models.Company
import com.spectrum.spectrum.src.models.Duty
import com.spectrum.spectrum.src.models.JobGroup

object BindingAdapters {

    @BindingAdapter("companies_vertical")
    @JvmStatic
    fun bindCompaniesVertical(recyclerView: RecyclerView, companies: ArrayList<Company>?) {
        if (companies == null) return
        recyclerView.apply {
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val spacing = resources.getDimensionPixelSize(R.dimen.default_margin)
                        outRect.top += spacing
                        outRect.bottom += spacing
                        outRect.left += spacing
                        outRect.right += spacing
                    }
                })
            }
            if (adapter == null) {
                adapter = CompanyAdapter(companies, CompanyAdapter.VERTICAL)
                return
            }
            adapter?.notifyDataSetChanged()
        }
    }

    @BindingAdapter("companies_horizontal")
    @JvmStatic
    fun bindCompaniesHorizontal(recyclerView: RecyclerView, companies: ArrayList<Company>?) {
        if (companies == null) return
        recyclerView.apply {
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val spacing = resources.getDimensionPixelSize(R.dimen.default_double_margin)
                        outRect.top += spacing
                        outRect.bottom += spacing
                        outRect.left += spacing
                    }
                })
                PagerSnapHelper().attachToRecyclerView(this)
            }
            if (adapter == null) {
                adapter = CompanyAdapter(companies, CompanyAdapter.HORIZONTAL)
                return
            }
            adapter?.notifyDataSetChanged()
        }
    }

    @BindingAdapter("company_duties")
    @JvmStatic
    fun bindDutyChipGroup(chipGroup: ChipGroup, duties: ArrayList<Duty>?) {
        if (duties == null) return
        chipGroup.apply {
            for (i in 0 until duties.size) {
                Chip(chipGroup.context).apply {
                    setChipStrokeWidthResource(R.dimen.default_stroke_width)
                    setChipStrokeColorResource(R.color.spectrumSilver3)
                    setChipBackgroundColorResource(if (i == 0) R.color.clear else R.color.spectrumSilver3)
                    setTextAppearance(R.style.ChipText)
                    text = duties[i].name
                    addView(this)
                }
            }
        }
    }

    @BindingAdapter("company_job_groups")
    @JvmStatic
    fun bindDutyChips(recyclerView: RecyclerView, duties: ArrayList<JobGroup>?) {
        if (duties == null) return
        recyclerView.apply {
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val spacing = recyclerView.resources.getDimensionPixelSize(R.dimen.default_half_margin)
                        outRect.left += spacing
                        outRect.right += spacing
                        outRect.top += spacing/2
                        outRect.bottom += spacing/2
                    }
                })
            }
            if (adapter == null) {
                adapter = MyJobGroupAdapter(duties)
                return
            }
            adapter?.notifyDataSetChanged()
        }
    }

}