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
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.models.Company
import com.spectrum.spectrum.src.models.Duty
import com.spectrum.spectrum.src.models.Industry
import com.spectrum.spectrum.src.models.JobGroup

object BindingAdapters {

    @BindingAdapter("companies_vertical")
    @JvmStatic
    fun bindCompaniesVertical(recyclerView: RecyclerView, items: ArrayList<Company>?) {
        val companies = items ?: ArrayList()
        recyclerView.apply {
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val pos = parent.getChildLayoutPosition(view)
                        if (pos == 0) outRect.top += dp2px(24)
                        outRect.bottom += dp2px(24)
                        outRect.left += dp2px(16)
                        outRect.right += dp2px(16)
                    }
                })
            }
            if (adapter == null) {
                adapter = CompanyVerticalAdapter(companies)
                return
            }
            adapter?.notifyDataSetChanged()
        }
    }

    @BindingAdapter("companies_horizontal")
    @JvmStatic
    fun bindCompaniesHorizontal(recyclerView: RecyclerView, items: ArrayList<Company>?) {
        val companies = items ?: ArrayList()
        recyclerView.apply {
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val pos = parent.getChildLayoutPosition(view)
                        if (pos == 0) outRect.left += dp2px(16)
                        outRect.top += dp2px(24)
                        outRect.bottom += dp2px(24)
                        outRect.right += dp2px(16)
                    }
                })
                PagerSnapHelper().attachToRecyclerView(this)
            }
            if (adapter == null) {
                adapter = CompanyHorizontalAdapter(companies)
                return
            }
            adapter?.notifyDataSetChanged()
        }
    }

    @BindingAdapter("company_industry_chips")
    @JvmStatic
    fun bindIndustryChipGroup(recyclerView: RecyclerView, items: ArrayList<Industry>?) {
        recyclerView.apply {
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration(){
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.left += dp2px(4)
                        outRect.right += dp2px(4)
                        outRect.top += dp2px(8)
                        outRect.bottom += dp2px(8)
                    }
                })
            }
            if (adapter == null) {
                adapter = IndustryAdapter(items ?: arrayListOf())
                return
            }
            adapter?.notifyDataSetChanged()
        }
    }

}