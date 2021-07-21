package com.spectrum.spectrum.src.activities.main.fragments.company.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.ItemCompanyVerticalBinding
import com.spectrum.spectrum.src.activities.main.fragments.company.CompanyFragment
import com.spectrum.spectrum.src.config.Helpers
import com.spectrum.spectrum.src.models.Company

class CompanyVerticalAdapter(private val items: ArrayList<Company>): RecyclerView.Adapter<CompanyVerticalAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private lateinit var mBinding: ItemCompanyVerticalBinding
    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    val pos = parent.getChildLayoutPosition(view)
                    if (pos == 0) outRect.top = Helpers.dp2px(24)
                    outRect.bottom = Helpers.dp2px(24)
                    outRect.left = Helpers.dp2px(16)
                    outRect.right = Helpers.dp2px(16)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_company_vertical, parent, false)
        mBinding.apply { adapter = this@CompanyVerticalAdapter }
        return ViewHolder(mBinding.root)
    }

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