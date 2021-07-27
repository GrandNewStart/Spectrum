package com.spectrum.spectrum.src.activities.main.fragments.post.adapters

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.post.PostViewModel
import com.spectrum.spectrum.src.activities.main.fragments.post.models.Education
import com.spectrum.spectrum.src.activities.main.fragments.post.models.Experience
import com.spectrum.spectrum.src.activities.main.fragments.post.models.License
import com.spectrum.spectrum.src.models.Evaluation

object BindingAdapters {

    @BindingAdapter("post_texts")
    @JvmStatic
    fun bindTextViews(textView: TextView, text: String?) {
        textView.text = text
    }

    @BindingAdapter("post_user_info")
    @JvmStatic
    fun bindUserInfoChipGroup(chipGroup: ChipGroup, items: ArrayList<String>?) {
        val userInfo = items ?: arrayListOf()
        chipGroup.apply {
            for (i in 0 until userInfo.size) {
                val info = userInfo[i]
                if (i == 0) {
                    Chip(context).apply {
                        setEnsureMinTouchTargetSize(false)
                        setTextAppearance(R.style.ChipTextSmall)
                        setChipBackgroundColorResource(R.color.clear)
                        setChipStrokeWidthResource(R.dimen.default_stroke_width)
                        isClickable = false
                        if (info == "R") {
                            text = "취업준비"
                            setChipStrokeColorResource(R.color.spectrumSilver2)
                        }
                        if (info == "N") {
                            text = "n차합격"
                            setChipStrokeColorResource(R.color.spectrumLightBlue)
                        }
                        if (info == "F") {
                            text = "최종합격"
                            setChipStrokeColorResource(R.color.spectrumBlue)
                        }
                        addView(this)
                    }
                }
                else {
                    Chip(context).apply {
                        text = info
                        setEnsureMinTouchTargetSize(false)
                        setTextAppearance(R.style.ChipTextSmall)
                        setChipBackgroundColorResource(R.color.spectrumSilver2)
                        isClickable = false
                        addView(this)
                    }
                }
            }
        }
    }

    @BindingAdapter("post_educations")
    @JvmStatic
    fun bindEducationList(recyclerView: RecyclerView, items: ArrayList<Education>?) {
        recyclerView.apply {
            items?.let { items ->
                if (adapter == null) {
                    adapter = EducationAdapter(items)
                    return@apply
                }
                adapter?.notifyDataSetChanged()
            }
        }
    }

    @BindingAdapter("post_experiences")
    @JvmStatic
    fun bindExperienceList(recyclerView: RecyclerView, items: ArrayList<Experience>?) {
        recyclerView.apply {
            items?.let { items ->
                if (adapter == null) {
                    adapter = ExperienceAdapter(items)
                    return@apply
                }
                adapter?.notifyDataSetChanged()
            }
        }
    }

    @BindingAdapter("post_licenses")
    @JvmStatic
    fun bindCertificationList(recyclerView: RecyclerView, items: ArrayList<License>?) {
        recyclerView.apply {
            items?.let { items ->
                if (adapter == null) {
                    adapter = LicenseAdapter(items)
                    return@apply
                }
                adapter?.notifyDataSetChanged()
            }
        }
    }

    @BindingAdapter("post_top_five_responses")
    @JvmStatic
    fun bindTopFiveResponses(recyclerView: RecyclerView, items: ArrayList<Evaluation>?) {
        recyclerView.apply {
            items?.let { items ->
                if (adapter == null) {
                    adapter = TopFiveAdapter(items)
                    return@apply
                }
                adapter?.notifyDataSetChanged()
            }
        }
    }


    @BindingAdapter("post_responses", "post_view_model", "post_post_button", requireAll = true)
    @JvmStatic
    fun bindResponseChipGroup(chipGroup: ChipGroup, items: ArrayList<Evaluation>?, viewModel: PostViewModel, button: MaterialButton) {
        chipGroup.apply {
            val responses   = items ?: arrayListOf()
            val blue        = resources.getColor(R.color.spectrumBlue, null)
            val silver      = resources.getColor(R.color.spectrumSilver3, null)
            val gray        = resources.getColor(R.color.spectrumGray3, null)

            for (i in 0 until responses.size) {
                val response = responses[i]

                val chip = Chip(context, null, R.style.ResponseChip).apply {
                    val drawable = ChipDrawable.createFromAttributes(context, null, 0, R.style.ResponseChip)
                    setChipDrawable(drawable)
                    if (response.count == 0) {
                        text = response.name
                        setTextColor(silver)
                    }
                    else {
                        setTextColor(gray)
                        val t = "${response.name} ${response.count}"
                        val spannable = SpannableStringBuilder(t)
                        spannable.setSpan(
                            ForegroundColorSpan(blue),
                            response.name.length,
                            t.length,
                            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                        )
                        text = spannable
                    }
                    isClickable = true
                    isCheckable = true
                    isChecked = response.isSelected

                    setOnCheckedChangeListener { _, isChecked ->
                        if (checkedChipIds.size > 1) {
                            if (isChecked) setChecked(false)
                            return@setOnCheckedChangeListener
                        }
                        items?.let {
                            if (isChecked) {
                                it[i].count++
                                it[i].isSelected = true
                                when(chipGroup.id) {
                                    R.id.edu_response_chip_group -> { viewModel.mEduResponse = response }
                                    R.id.exp_response_chip_group -> { viewModel.mExpResponse = response }
                                    R.id.cert_response_chip_group -> { viewModel.mCertResponse = response }
                                    R.id.other_response_chip_group -> { viewModel.mOtherResponse = response }
                                }
                                viewModel.apply {
                                    if (mEduResponse == null && mExpResponse == null && mCertResponse == null && mOtherResponse == null) {
                                        button.visibility = View.GONE
                                    }
                                    else {
                                        button.visibility = View.VISIBLE
                                    }
                                }
                            }
                            else {
                                it[i].count--
                                it[i].isSelected = false
                                when(chipGroup.id) {
                                    R.id.edu_response_chip_group -> { viewModel.mEduResponse = null }
                                    R.id.exp_response_chip_group -> { viewModel.mExpResponse = null }
                                    R.id.cert_response_chip_group -> { viewModel.mCertResponse = null }
                                    R.id.other_response_chip_group -> { viewModel.mOtherResponse = null }
                                }
                                viewModel.apply {
                                    if (mEduResponse == null && mExpResponse == null && mCertResponse == null && mOtherResponse == null) {
                                        button.visibility = View.GONE
                                    }
                                    else {
                                        button.visibility = View.VISIBLE
                                    }
                                }
                            }

                            if (it[i].count == 0) {
                                setTextColor(silver)
                                text = it[i].name
                            }
                            else {
                                val t = "${response.name} ${response.count}"
                                val spannable = SpannableStringBuilder(t)
                                spannable.setSpan(
                                    ForegroundColorSpan(blue),
                                    response.name.length,
                                    t.length,
                                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                                )
                                setTextColor(gray)
                                text = spannable
                            }
                        }
                    }
                }

                addView(chip)
            }
        }
    }

}