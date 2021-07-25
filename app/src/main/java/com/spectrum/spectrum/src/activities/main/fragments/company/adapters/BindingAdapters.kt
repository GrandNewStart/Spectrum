package com.spectrum.spectrum.src.activities.main.fragments.company.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spectrum.spectrum.src.models.Company
import com.spectrum.spectrum.src.models.Industry

object BindingAdapters {

    @BindingAdapter("companies_vertical")
    @JvmStatic
    fun bindCompaniesVertical(recyclerView: RecyclerView, items: ArrayList<Company>?) {
        val companies = items ?: ArrayList()
        recyclerView.apply {
            if (adapter == null) {
                adapter = CompanyAdapter(companies, CompanyAdapter.VERTICAL)
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
            if (adapter == null) {
                adapter = CompanyAdapter(companies, CompanyAdapter.HORIZONTAL)
                return
            }
            adapter?.notifyDataSetChanged()
        }
    }

    @BindingAdapter("company_industry_chips")
    @JvmStatic
    fun bindIndustryChipGroup(recyclerView: RecyclerView, items: ArrayList<Industry>?) {
        recyclerView.apply {
            if (adapter == null) {
                adapter = IndustryAdapter(items ?: arrayListOf())
                return
            }
            adapter?.notifyDataSetChanged()
        }
    }

}