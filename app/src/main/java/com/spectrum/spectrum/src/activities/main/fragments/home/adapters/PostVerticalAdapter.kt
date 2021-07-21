package com.spectrum.spectrum.src.activities.main.fragments.home.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.ItemPostHorizontalBinding
import com.spectrum.spectrum.databinding.ItemPostVerticalBinding
import com.spectrum.spectrum.src.activities.main.fragments.home.HomeFragment
import com.spectrum.spectrum.src.activities.main.fragments.home.HomeViewModel
import com.spectrum.spectrum.src.config.Helpers
import com.spectrum.spectrum.src.models.Post

class PostVerticalAdapter(private val items: ArrayList<Post>): RecyclerView.Adapter<PostVerticalAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private lateinit var mBinding: ItemPostVerticalBinding
    private lateinit var mFragment: HomeFragment
    private lateinit var mViewModel: HomeViewModel

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.apply {
            mFragment = findFragment()
            mViewModel = mFragment.mViewModel

            layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    val hSpacing = Helpers.dp2px(24)
                    outRect.bottom += hSpacing
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_post_vertical, parent, false)
        return ViewHolder(mBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mBinding.apply {
            fragment = mFragment
            viewModel = mViewModel
            post = items[position]
        }
    }

    override fun getItemCount(): Int = items.size


}