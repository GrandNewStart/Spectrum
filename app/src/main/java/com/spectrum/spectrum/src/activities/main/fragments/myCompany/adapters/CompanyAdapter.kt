package com.spectrum.spectrum.src.activities.main.fragments.myCompany.adapters

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.ItemMyCompanyCompanyBinding
import com.spectrum.spectrum.src.activities.main.fragments.myCompany.MyCompanyFragment
import com.spectrum.spectrum.src.activities.main.fragments.myCompany.models.Company
import com.spectrum.spectrum.src.config.Helpers.dp2px

class CompanyAdapter(private val items: ArrayList<Company>): RecyclerView.Adapter<CompanyAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMyCompanyCompanyBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindViews(company: Company) {
            binding.apply {
                titleText.text = company.name
                groupText.text = company.jobGroup
                specCountText.text = "${company.specCount}개의 스펙"
            }
        }
    }

    private lateinit var mBinding: ItemMyCompanyCompanyBinding
    private lateinit var mRecyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(object : RecyclerView.ItemDecoration(){
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.left = dp2px(16)
                    outRect.right = dp2px(16)
                    outRect.top = dp2px(12)
                    outRect.bottom = dp2px(12)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_my_company_company, parent,false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun proceedToCompanyInfo() {
        val fragment = mRecyclerView.findFragment<MyCompanyFragment>()
    }

}