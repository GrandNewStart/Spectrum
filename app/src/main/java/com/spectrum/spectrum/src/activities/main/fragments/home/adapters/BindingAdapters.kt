package com.spectrum.spectrum.src.activities.main.fragments.home.adapters

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.home.HomeFragment
import com.spectrum.spectrum.src.activities.main.fragments.home.dialogs.JobGroupDialog
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.models.JobGroup
import com.spectrum.spectrum.src.models.Post
import com.spectrum.spectrum.src.models.Info

object BindingAdapters {

    @BindingAdapter("home_logo_button")
    @JvmStatic
    fun bindSearchEditText(editText: EditText, button: ImageButton) {
        editText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                button.setImageResource(R.drawable.icon_back)
            }
            else {
                button.setImageResource(R.drawable.icon_logo_small)
                editText.clearFocus()
                val imm = editText.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }
    }

    @BindingAdapter("home_posts_horizontal")
    @JvmStatic
    fun bindHottestPosts(recyclerView: RecyclerView, posts: ArrayList<Post>?) {
        recyclerView.apply {
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val pos = parent.getChildAdapterPosition(view)
                        outRect.left += if (pos == 0) dp2px(15) else dp2px(24)
                        posts?.let {
                            if (pos == it.size-1) { outRect.right += dp2px(24) }
                        }
                    }
                })
                PagerSnapHelper().attachToRecyclerView(recyclerView)
            }
            if (adapter == null) {
                posts?.let { adapter = PostHorizontalAdapter(it) }
                return@apply
            }
            adapter?.notifyDataSetChanged()
        }
    }

    @BindingAdapter("home_posts_vertical")
    @JvmStatic
    fun bindLatestPosts(recyclerView: RecyclerView, posts: ArrayList<Post>?) {
        recyclerView.apply {
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val hSpacing = dp2px(24)
                        outRect.bottom += hSpacing
                    }
                })
            }
            if (adapter == null) {
                posts?.let { adapter = PostVerticalAdapter(it) }
                return@apply
            }
            adapter?.notifyDataSetChanged()
        }
    }

    @BindingAdapter("home_fragment")
    @JvmStatic
    fun bindJobGroupChips(view: View, fragment: HomeFragment) {
        fragment.apply { view.setOnClickListener {
            JobGroupDialog(fragment.requireContext())
                .setPreSelectedItems(mViewModel.mJobGroup1, mViewModel.mJobGroup2, mViewModel.mJobGroup3)
                .setOnSaveListener{ first, second, third ->
                    mViewModel.mJobGroup1 = first
                    mViewModel.mJobGroup2 = second
                    mViewModel.mJobGroup3 = third

                    mBinding.jobGroup1 = first
                    mBinding.jobGroup2 = second
                    mBinding.jobGroup3 = third
                }.show()
        }
    }}

    @BindingAdapter("home_job_group")
    @JvmStatic
    fun bindChip(chip: Chip, jobGroup: JobGroup?) {
        chip.apply {
            visibility = if (jobGroup == null) View.GONE else View.VISIBLE
            jobGroup?.let {
                chip.text = it.name
            }
        }
    }

}