package com.spectrum.spectrum.src.activities.main.fragments.home.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
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
import com.spectrum.spectrum.src.activities.main.MainActivity
import com.spectrum.spectrum.src.activities.main.fragments.home.HomeFragment
import com.spectrum.spectrum.src.activities.main.fragments.home.models.JobGroup
import com.spectrum.spectrum.src.activities.main.fragments.home.models.Post
import com.spectrum.spectrum.src.config.Helpers.dp2px

object BindingAdapters {

    @BindingAdapter("home_logo_button", "home_fragment", requireAll = true)
    @JvmStatic
    fun bindSearchEditText(editText: EditText, button: ImageButton, fragment: HomeFragment) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                button.setImageResource(R.drawable.icon_back)
                (fragment.activity as MainActivity).showSearchDialog()
            }
            else {
                button.setImageResource(R.drawable.icon_logo_small)
                fragment.showKeyboard(editText, false)
                (fragment.activity as MainActivity).hideSearchDialog()
            }
        }
        editText.setOnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_SEARCH) {
                val keyword = editText.text.toString().trim()
                if (keyword.isNotEmpty()) {
                    fragment.mViewModel.proceedToSearch(fragment, keyword)
                    editText.clearFocus()
                }
                true
            }
            else false
        }
        editText.setOnKeyListener { _, id, _ ->
            if (id == KeyEvent.KEYCODE_BACK && editText.hasFocus()) {
                editText.clearFocus()
                editText.text = null
                true
            }
            else {
                false
            }
        }
    }

    @BindingAdapter("home_posts_horizontal")
    @JvmStatic
    fun bindHottestPosts(recyclerView: RecyclerView, posts: ArrayList<Post>?) {
        val items = posts ?: return
        recyclerView.apply {
            adapter = PostAdapter(items, PostAdapter.HORIZONTAL)
        }
    }

    @BindingAdapter("home_posts_vertical")
    @JvmStatic
    fun bindLatestPosts(recyclerView: RecyclerView, posts: ArrayList<Post>?) {
        val items = posts ?: return
        recyclerView.apply {
            adapter = PostAdapter(items, PostAdapter.VERTICAL)
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
                        setTextAppearance(R.style.ChipTextBig)
                        setChipStrokeWidthResource(R.dimen.thin_stroke_width)
                        setChipBackgroundColorResource(R.color.clear)
                        setChipStrokeColorResource(R.color.spectrumSilver3)
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
                        setTextAppearance(R.style.ChipTextBig)
                        isClickable = false
                        addView(this)
                    }
                    Chip(context).apply{
                        text = post.sex
                        setEnsureMinTouchTargetSize(false)
                        setChipBackgroundColorResource(R.color.spectrumSilver2)
                        setTextAppearance(R.style.ChipTextBig)
                        isClickable = false
                        addView(this)
                    }
                    Chip(context).apply {
                        text = post.jobGroupList[0].data
                        setEnsureMinTouchTargetSize(false)
                        setChipBackgroundColorResource(R.color.spectrumSilver2)
                        setTextAppearance(R.style.ChipTextBig)
                        isClickable = false
                        addView(this)
                    }
                }
            }
        }
    }

}