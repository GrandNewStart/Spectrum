package com.spectrum.spectrum.src.activities.main.fragments.home.adapters

import android.graphics.Rect
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.home.HomeFragment
import com.spectrum.spectrum.src.models.JobGroup
import com.spectrum.spectrum.src.models.Post
import com.spectrum.spectrum.src.models.Spec

object BindingAdapters {

    @BindingAdapter("home_posts_horizontal", "home_fragment", requireAll = true)
    @JvmStatic
    fun bindHottestPosts(recyclerView: RecyclerView, posts: ArrayList<Post>?, fragment: HomeFragment) {
        recyclerView.apply {
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val spacing = resources.getDimensionPixelSize(R.dimen.default_double_margin)
                        outRect.top += spacing
                        outRect.bottom += spacing
                        outRect.left += spacing
                    }
                })
                PagerSnapHelper().attachToRecyclerView(recyclerView)
            }
            if (adapter == null) {
                posts?.let { adapter = PostAdapter(fragment, it, PostAdapter.HORIZONTAL) }
                return@apply
            }
            adapter?.notifyDataSetChanged()
        }
    }

    @BindingAdapter("home_posts_vertical", "home_fragment", requireAll = true)
    @JvmStatic
    fun bindLatestPosts(recyclerView: RecyclerView, posts: ArrayList<Post>?, fragment: HomeFragment) {
        recyclerView.apply {
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val spacing = resources.getDimensionPixelSize(R.dimen.default_margin)
                        outRect.top += spacing
                        outRect.bottom += spacing
                        outRect.left += spacing
                        outRect.right += spacing
                    }
                })
            }
            if (adapter == null) {
                posts?.let { adapter = PostAdapter(fragment, it, PostAdapter.VERTICAL) }
                return@apply
            }
            adapter?.notifyDataSetChanged()
        }
    }

    @BindingAdapter("home_specs")
    @JvmStatic
    fun bindSpecChips(chipGroup: ChipGroup, specs: ArrayList<Spec>?) {
        if (specs == null) return
        chipGroup.apply {
            for (i in 0 until specs.size) {
                val chip = Chip(chipGroup.context).apply {
                    text = specs[i].name
                    setChipBackgroundColorResource(if (i == 0) R.color.clear else R.color.spectrumSilver2)
                    setChipStrokeColorResource(R.color.spectrumSilver2)
                    setChipStrokeWidthResource(R.dimen.default_stroke_width)
                    setTextAppearance(R.style.ChipText)
                }
                addView(chip)
            }
        }
    }

    @BindingAdapter("home_job_groups")
    @JvmStatic
    fun bindDutyChips(recyclerView: RecyclerView, duties: ArrayList<JobGroup>?) {
        if (duties == null) return
        recyclerView.apply {
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val spacing = recyclerView.resources.getDimensionPixelSize(R.dimen.default_half_margin)
                        outRect.left += spacing
                        outRect.right += spacing
                        outRect.top += spacing/2
                        outRect.bottom += spacing/2
                    }
                })
            }
            if (adapter == null) {
                adapter = MyJobGroupAdapter(duties)
                return
            }
            adapter?.notifyDataSetChanged()
        }
    }

    @BindingAdapter("home_to_post")
    @JvmStatic
    fun bindItemView(view: View, fragment: HomeFragment) {
        view.setOnClickListener {
            fragment.findNavController().navigate(R.id.home_to_post)
        }
    }

}