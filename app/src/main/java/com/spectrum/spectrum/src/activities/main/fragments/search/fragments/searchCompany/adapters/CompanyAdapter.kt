package com.spectrum.spectrum.src.activities.main.fragments.search.fragments.searchCompany.adapters

import android.annotation.SuppressLint
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.ItemSearchCompanyBinding
import com.spectrum.spectrum.src.activities.main.fragments.search.fragments.searchCompany.models.Company

class CompanyAdapter(private val items: ArrayList<Company>): RecyclerView.Adapter<CompanyAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemSearchCompanyBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindViews(company: Company) {
            binding.apply {
                titleText.text = company.name
                industryText.text = company.industry
                val countText = company.specCount.toString()
                val countTextWithSuffix = "${countText}개의 스펙"
                val spannable = SpannableString(countTextWithSuffix).apply {
                    val blue = specCountText.resources.getColor(R.color.spectrumBlue, null)
                    setSpan(ForegroundColorSpan(blue), 0, countText.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
                }
                specCountText.text = spannable
            }
        }
    }

    private lateinit var mBinding: ItemSearchCompanyBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_search_company, parent, false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun addItems(newItems: ArrayList<Company>) {
        newItems.forEach {
            items.add(it)
            notifyItemInserted(items.size-1)
        }
    }

}