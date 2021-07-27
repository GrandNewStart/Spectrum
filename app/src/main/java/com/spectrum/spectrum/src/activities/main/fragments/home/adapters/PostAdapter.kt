package com.spectrum.spectrum.src.activities.main.fragments.home.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.ItemHomePostBinding
import com.spectrum.spectrum.src.activities.main.fragments.home.HomeFragment
import com.spectrum.spectrum.src.activities.main.fragments.home.HomeViewModel
import com.spectrum.spectrum.src.activities.main.fragments.home.models.Post
import com.spectrum.spectrum.src.config.Constants.SCREEN_WIDTH
import com.spectrum.spectrum.src.config.Helpers.dp2px

class PostAdapter(private val items: ArrayList<Post>, private val dir: Int = 0): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private lateinit var mBinding: ItemHomePostBinding
    private lateinit var mFragment: HomeFragment
    private lateinit var mViewModel: HomeViewModel

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.apply {
            mFragment = findFragment()
            mViewModel = mFragment.mViewModel
            if (dir == HORIZONTAL) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val pos = parent.getChildAdapterPosition(view)
                        outRect.left = if (pos == 0) dp2px(15) else dp2px(24)
                        outRect.top = dp2px(12)
                        outRect.bottom = dp2px(16)
                        items.let {
                            if (pos == it.size-1) { outRect.right = dp2px(24) }
                        }
                    }
                })
                PagerSnapHelper().attachToRecyclerView(recyclerView)
            }
            if (dir == VERTICAL) {
                layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val vSpacing = dp2px(12)
                        val hSpacing = dp2px(16)
                        outRect.top = vSpacing
                        outRect.bottom = vSpacing
                        outRect.left = hSpacing
                        outRect.right = hSpacing
                    }
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_home_post, parent, false)
        if (dir == HORIZONTAL) {
            mBinding.root.apply {
                layoutParams = ViewGroup.LayoutParams((SCREEN_WIDTH * 0.7).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
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

    fun addItems(newItems: ArrayList<Post>) {
        newItems.forEach {
            items.add(it)
            notifyItemInserted(items.size-1)
        }
    }

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1
    }

}