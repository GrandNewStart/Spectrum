package com.spectrum.spectrum.src.activities.main.fragments.home.adapters

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.home.HomeFragment
import com.spectrum.spectrum.src.activities.main.fragments.home.models.JobGroup
import com.spectrum.spectrum.src.activities.main.fragments.home.models.Post

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
        val items = posts ?: return
        recyclerView.apply {
            if (adapter == null) {
                adapter = PostAdapter(items, PostAdapter.HORIZONTAL)
                return@apply
            }
            adapter?.notifyDataSetChanged()
        }
    }

    @BindingAdapter("home_posts_vertical")
    @JvmStatic
    fun bindLatestPosts(recyclerView: RecyclerView, posts: ArrayList<Post>?) {
        val items = posts ?: return
        recyclerView.apply {
            if (adapter == null) {
                adapter = PostAdapter(items, PostAdapter.VERTICAL)
                return@apply
            }
            adapter?.notifyDataSetChanged()
        }
    }

    @BindingAdapter("home_job_group")
    @JvmStatic
    fun bindChip(chip: Chip, jobGroup: JobGroup?) {
        chip.apply {
            visibility = if (jobGroup == null) View.GONE else View.VISIBLE
            jobGroup?.let {
                chip.text = it.data
            }
        }
    }

    @BindingAdapter("home_fragment")
    @JvmStatic
    fun bindScrollView(scrollView: NestedScrollView, fragment: HomeFragment) {
        scrollView.apply {
            setOnScrollChangeListener { _,_,_,_,_->
                if (!canScrollVertically(1)) {
                    fragment.mViewModel.getHomePage(fragment)
                }
            }
        }
    }

    @BindingAdapter("home_post_item")
    @JvmStatic
    fun bindPostHorizontal(view: View, post: Post) {
        when(view.id) {
            R.id.title_text -> { (view as TextView).text = post.title }
            R.id.update_time_text -> { (view as TextView).text = post.createdAt }
            R.id.response_count_text -> { (view as TextView).text = "0" }
            R.id.mark_count_text -> { (view as TextView).text = "0" }
            R.id.chip_group -> {
                (view as ChipGroup).apply {
                    removeAllViews()
                    post.jobGroupList.forEach {
                        Chip(context).apply {
                            text = it.data
                            setEnsureMinTouchTargetSize(false)
                            setChipBackgroundColorResource(R.color.spectrumSilver2)
                            setTextAppearance(R.style.ChipText)
                            addView(this)
                        }
                    }
                }
            }
        }
    }

}