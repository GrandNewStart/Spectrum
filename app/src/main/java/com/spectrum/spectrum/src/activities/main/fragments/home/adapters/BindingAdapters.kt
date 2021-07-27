package com.spectrum.spectrum.src.activities.main.fragments.home.adapters

import android.annotation.SuppressLint
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

    @BindingAdapter("home_job_group_1", "home_job_group_2")
    @JvmStatic
    fun bindChip(chip: Chip, jobGroup1: JobGroup?, jobGroup2: JobGroup?) {
        chip.apply {
            when(id) {
                R.id.all_chip -> {
                    visibility = if (jobGroup1 == null && jobGroup2 == null) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
                R.id.chip_1 -> {
                    visibility = if (jobGroup1 == null) View.GONE else View.VISIBLE
                    jobGroup1?.let { text = it.data }
                }
                R.id.chip_2 -> {
                    visibility = if (jobGroup2 == null) View.GONE else View.VISIBLE
                    jobGroup2?.let { text = it.data }
                }
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

    @SuppressLint("SetTextI18n")
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
                    Chip(context).apply{
                        text = post.jobStatus
                        setEnsureMinTouchTargetSize(false)
                        setTextAppearance(R.style.ChipTextSmall)
                        setChipStrokeWidthResource(R.dimen.default_stroke_width)
                        setChipBackgroundColorResource(R.color.clear)
                        setChipStrokeColorResource(R.color.spectrumSilver2)
                        isClickable = false
                        if (post.jobStatus == "n차합격" || post.jobStatus == "최종합격") {
                            setChipBackgroundColorResource(R.color.clear)
                            setChipStrokeColorResource(R.color.spectrumBlue)
                            setTextColor(resources.getColor(R.color.spectrumBlue, null))
                        }
                        addView(this)
                    }
                    Chip(context).apply{
                        text = "${post.age}세"
                        setEnsureMinTouchTargetSize(false)
                        setChipBackgroundColorResource(R.color.spectrumSilver2)
                        setTextAppearance(R.style.ChipTextSmall)
                        isClickable = false
                        addView(this)
                    }
                    Chip(context).apply{
                        text = post.sex
                        setEnsureMinTouchTargetSize(false)
                        setChipBackgroundColorResource(R.color.spectrumSilver2)
                        setTextAppearance(R.style.ChipTextSmall)
                        isClickable = false
                        addView(this)
                    }
                    post.jobGroupList.forEach {
                        Chip(context).apply {
                            text = it.data
                            setEnsureMinTouchTargetSize(false)
                            setChipBackgroundColorResource(R.color.spectrumSilver2)
                            setTextAppearance(R.style.ChipTextSmall)
                            isClickable = false
                            addView(this)
                        }
                    }
                }
            }
        }
    }

}