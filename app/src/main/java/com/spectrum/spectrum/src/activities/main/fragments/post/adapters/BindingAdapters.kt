package com.spectrum.spectrum.src.activities.main.fragments.post.adapters

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.post.CommentChip
import com.spectrum.spectrum.src.activities.main.fragments.post.PostViewModel
import com.spectrum.spectrum.src.activities.main.fragments.post.models.Comment
import com.spectrum.spectrum.src.activities.main.fragments.post.models.Education
import com.spectrum.spectrum.src.activities.main.fragments.post.models.Experience
import com.spectrum.spectrum.src.activities.main.fragments.post.models.License
import com.spectrum.spectrum.src.config.Constants

object BindingAdapters {

    @BindingAdapter("post_texts")
    @JvmStatic
    fun bindTextViews(textView: TextView, text: String?) {
        textView.text = text ?: Constants.no_entry
    }

    @BindingAdapter("post_user_info")
    @JvmStatic
    fun bindUserInfoChipGroup(chipGroup: ChipGroup, items: ArrayList<String>?) {
        val userInfo = items ?: arrayListOf()
        chipGroup.apply {
            removeAllViews()
            for (i in 0 until userInfo.size) {
                val info = userInfo[i]
                if (i == 0) {
                    Chip(context).apply {
                        setEnsureMinTouchTargetSize(false)
                        setTextAppearance(R.style.ChipTextBig)
                        setChipBackgroundColorResource(R.color.clear)
                        setChipStrokeWidthResource(R.dimen.thin_stroke_width)
                        isClickable = false
                        if (info == "R") {
                            text = "취업준비"
                            setChipStrokeColorResource(R.color.spectrumSilver2)
                        }
                        if (info == "N") {
                            text = "n차합격"
                            setChipStrokeColorResource(R.color.spectrumBlue)
                            setTextColor(resources.getColor(R.color.spectrumBlue, null))
                        }
                        if (info == "F") {
                            text = "최종합격"
                            setChipStrokeColorResource(R.color.spectrumBlue)
                            setTextColor(resources.getColor(R.color.spectrumBlue, null))
                        }
                        addView(this)
                    }
                }
                else {
                    Chip(context).apply {
                        text = info
                        setEnsureMinTouchTargetSize(false)
                        setTextAppearance(R.style.ChipTextBig)
                        setChipBackgroundColorResource(R.color.spectrumSilver2)
                        isClickable = false
                        addView(this)
                    }
                }
            }
        }
    }

    @BindingAdapter("post_educations", "post_no_education_text")
    @JvmStatic
    fun bindEducationList(recyclerView: RecyclerView,
                          items: ArrayList<Education>?,
                          textView: TextView) {
        recyclerView.apply {
            items?.let { items ->
                adapter = EducationAdapter(items)
                visibility = if (items.isEmpty()) View.GONE else View.VISIBLE
                textView.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    @BindingAdapter("post_experiences", "post_no_experience_text")
    @JvmStatic
    fun bindExperienceList(recyclerView: RecyclerView,
                           items: ArrayList<Experience>?,
                           textView: TextView) {
        recyclerView.apply {
            items?.let { items ->
                adapter = ExperienceAdapter(items)
                visibility = if (items.isEmpty()) View.GONE else View.VISIBLE
                textView.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    @BindingAdapter("post_licenses", "post_no_license_text")
    @JvmStatic
    fun bindLicenseList(recyclerView: RecyclerView,
                        items: ArrayList<License>?,
                        textView: TextView) {
        recyclerView.apply {
            items?.let { items ->
                adapter = LicenseAdapter(items)
                visibility = if (items.isEmpty()) View.GONE else View.VISIBLE
                textView.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    @BindingAdapter("post_top_five_responses", "post_no_top_five_text", requireAll = true)
    @JvmStatic
    fun bindTopFiveResponses(recyclerView: RecyclerView, items: ArrayList<Comment>?, textView: TextView) {
        val comments = items ?: return
        for (i in 0 until comments.size) {
            if (comments[i].count != 0) break
            if (i == comments.size-1) {
                textView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                return
            }
        }
        textView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        recyclerView.adapter = TopFiveAdapter(comments)
    }


    @BindingAdapter(
        "post_zero_chip_group",
        "post_expand_button",
        "post_non_zero",
        "post_zero",
        "post_view_model", requireAll = true)
    @JvmStatic
    fun bindResponseChipGroup(c1: ChipGroup,
                              c2: ChipGroup,
                              button: ImageButton,
                              nonZero: ArrayList<Comment>?,
                              zero: ArrayList<Comment>?,
                              viewModel: PostViewModel) {
        val nonZero = nonZero ?: return
        val zero = zero ?: return
        c1.apply {
            removeAllViews()
            if (nonZero.isEmpty()) {
                c2.visibility = View.GONE
                button.visibility = View.GONE
                zero.forEach { comment ->
                    CommentChip(context, comment).apply {
                        setOnClickListener {
                            val selectedItem = loopItems(zero, comment)
                            returnItem(selectedItem, c1, viewModel)
                            for (i in 0 until zero.size) {
                                (c1.getChildAt(i) as CommentChip).updateUI(zero[i])
                            }
                        }
                        addView(this)
                    }
                }
                return
            }
            button.visibility = View.VISIBLE
            nonZero.forEach { comment ->
                CommentChip(context, comment).apply {
                    setOnClickListener {
                        val selectedItem1 = loopItems(nonZero, comment)
                        val selectedItem2 = loopItems(zero, comment)
                        if (selectedItem1 == null && selectedItem2 == null) {
                            returnItem(null, c1, viewModel)
                        }
                        if (selectedItem1 != null) {
                            returnItem(selectedItem1, c1, viewModel)
                        }
                        if (selectedItem2 != null) {
                            returnItem(selectedItem2, c1, viewModel)
                        }
                        for (i in 0 until nonZero.size) {
                            (c1.getChildAt(i) as CommentChip).updateUI(nonZero[i])
                        }
                        for (i in 0 until zero.size) {
                            (c2.getChildAt(i) as CommentChip).updateUI(zero[i])
                        }
                    }
                    addView(this)
                }
            }
        }
        c2.apply {
            removeAllViews()
            zero.forEach { comment ->
                CommentChip(context, comment).apply {
                    setOnClickListener {
                        val selectedItem1 = loopItems(nonZero, comment)
                        val selectedItem2 = loopItems(zero, comment)
                        if (selectedItem1 == null && selectedItem2 == null) {
                            returnItem(null, c1, viewModel)
                        }
                        if (selectedItem1 != null) {
                            returnItem(selectedItem1, c1, viewModel)
                        }
                        if (selectedItem2 != null) {
                            returnItem(selectedItem2, c1, viewModel)
                        }
                        for (i in 0 until nonZero.size) {
                            (c1.getChildAt(i) as CommentChip).updateUI(nonZero[i])
                        }
                        for (i in 0 until zero.size) {
                            (c2.getChildAt(i) as CommentChip).updateUI(zero[i])
                        }
                    }
                    addView(this)
                }
            }
        }
    }

    private fun loopItems(items: ArrayList<Comment>, target: Comment): Comment? {
        var result: Comment? = null
        for (i in 0 until items.size) {
            val item = items[i]
            if (item.id == target.id) {
                if (item.isSelected) {
                    item.isSelected = false
                    item.count--
                    result = null
                }
                else {
                    item.isSelected = true
                    item.count++
                    result = item
                }
            }
            else {
                if (item.isSelected) {
                    item.isSelected = false
                    item.count--
                    result = null
                }
            }
        }
        return result
    }

    private fun returnItem(comment: Comment?, chipGroup: ChipGroup, viewModel: PostViewModel) {
        when(chipGroup.id) {
            R.id.edu_non_zero_comment_chip_group -> { viewModel.mEduComment.value = comment }
            R.id.exp_non_zero_comment_chip_group -> { viewModel.mExpComment.value = comment }
            R.id.lic_non_zero_comment_chip_group -> { viewModel.mLicComment.value = comment }
            R.id.others_non_zero_comment_chip_group -> { viewModel.mOtherComment.value = comment }
        }
    }

    @BindingAdapter("post_chip_group_to_expand")
    @JvmStatic
    fun bindExpandButton(button: ImageButton, chipGroup: ChipGroup) {
        button.setOnClickListener {
            if (chipGroup.visibility == View.VISIBLE) {
                chipGroup.visibility = View.GONE
                button.animate().setDuration(200).rotation(0f)
                return@setOnClickListener
            }
            if (chipGroup.visibility == View.GONE) {
                chipGroup.visibility = View.VISIBLE
                button.animate().setDuration(200).rotation(180f)
                return@setOnClickListener
            }
        }
    }

}