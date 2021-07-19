package com.spectrum.spectrum.src.activities.main.fragments.company.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.ItemCompanyHorizontalBinding
import com.spectrum.spectrum.src.activities.main.fragments.company.CompanyFragment
import com.spectrum.spectrum.src.models.Company

class CompanyHorizontalAdapter(private val items: ArrayList<Company>): RecyclerView.Adapter<CompanyHorizontalAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private lateinit var mBinding: ItemCompanyHorizontalBinding
    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_company_horizontal, parent, false)
        mBinding.apply { adapter = this@CompanyHorizontalAdapter }
        return ViewHolder(mBinding.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mBinding.apply {
            items[position].let {
                titleText.text = it.name
                groupText.text = it.group
                specCountText.text = "${it.specCount}개의 스펙"
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun proceedToCompanyInfo() {
        val fragment = recyclerView?.findFragment<CompanyFragment>()
        fragment?.apply {
            findNavController().navigate(R.id.company_to_company_info)
        }
    }

}