package com.spectrum.spectrum.src.activities.main.fragments.search.fragments.searchCompany

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.search.fragments.searchCompany.adapters.CompanyAdapter
import com.spectrum.spectrum.src.activities.main.fragments.search.fragments.searchCompany.models.Company
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.customs.BaseFragment

class SearchCompanyFragment: BaseFragment() {

    var mCompanies = ArrayList<Company>()
    private var mAdapter = CompanyAdapter(mCompanies)
    var mRecyclerView: RecyclerView? = null
    var mNoItemsText: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search_company, container, false)
        view.apply {
            mNoItemsText = findViewById(R.id.no_items_text)
            findViewById<RecyclerView>(R.id.company_recycler_view).apply {
                mRecyclerView = this
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration(){
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.top = dp2px(24)
                        outRect.left = dp2px(16)
                        outRect.right = dp2px(16)
                    }
                })
                adapter = mAdapter
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        mRecyclerView?.visibility = if (mCompanies.isEmpty()) View.GONE else View.VISIBLE
        mNoItemsText?.visibility = if (mCompanies.isEmpty()) View.VISIBLE else View.GONE
    }

    fun addItems(newItems: ArrayList<Company>) {
        mAdapter.addItems(newItems)
    }

    fun clearItems() {
        mAdapter.notifyItemRangeRemoved(0, mCompanies.size)
        mCompanies.clear()
    }

}