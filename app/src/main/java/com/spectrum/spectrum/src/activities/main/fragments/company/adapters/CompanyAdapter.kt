package com.spectrum.spectrum.src.activities.main.fragments.company.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.ItemCompanyBinding
import com.spectrum.spectrum.src.models.Company
import com.spectrum.spectrum.src.config.Constants.SCREEN_WIDTH

class CompanyAdapter(private val items: ArrayList<Company>, private val orientation: Int): RecyclerView.Adapter<CompanyAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemCompanyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(company: Company) {
            company.apply {
                binding.title = name
                binding.watcherCount = watcherCount
                binding.duties = duties
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemCompanyBinding>(inflater, R.layout.item_company, parent, false)
        binding.root.apply {
            if (orientation == VERTICAL) {
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            }
            if (orientation == HORIZONTAL) {
                layoutParams.width = (SCREEN_WIDTH * 0.7).toInt()
            }
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    companion object {
        const val VERTICAL = 0
        const val HORIZONTAL = 1
    }

}