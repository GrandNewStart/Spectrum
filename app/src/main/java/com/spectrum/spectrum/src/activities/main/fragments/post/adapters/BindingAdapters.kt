package com.spectrum.spectrum.src.activities.main.fragments.post.adapters

import android.graphics.Rect
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.post.PostViewModel
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.models.CertItem
import com.spectrum.spectrum.src.models.EduItem
import com.spectrum.spectrum.src.models.Evaluation
import com.spectrum.spectrum.src.models.ExpItem

object BindingAdapters {

    @BindingAdapter("post_edu_items")
    @JvmStatic
    fun bindEducationList(recyclerView: RecyclerView, items: ArrayList<EduItem>?) {
        recyclerView.apply {
            items?.let { items ->
                if (layoutManager == null) {
                    layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
                    addItemDecoration(object : RecyclerView.ItemDecoration() {
                        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                            super.getItemOffsets(outRect, view, parent, state)
                            val pos = parent.getChildLayoutPosition(view)
                            if (pos != 0) outRect.top += dp2px(4)
                        }
                    })
                }
                if (adapter == null) {
                    adapter = EduItemAdapter(items)
                    return@apply
                }
                adapter?.notifyDataSetChanged()
            }
        }
    }

    @BindingAdapter("post_exp_items")
    @JvmStatic
    fun bindExperienceList(recyclerView: RecyclerView, items: ArrayList<ExpItem>?) {
        recyclerView.apply {
            items?.let { items ->
                if (layoutManager == null) {
                    layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
                    addItemDecoration(object : RecyclerView.ItemDecoration() {
                        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                            super.getItemOffsets(outRect, view, parent, state)
                            val pos = parent.getChildLayoutPosition(view)
                            if (pos != 0) outRect.top += dp2px(4)
                        }
                    })
                }
                if (adapter == null) {
                    adapter = ExpItemAdapter(items)
                    return@apply
                }
                adapter?.notifyDataSetChanged()
            }
        }
    }

    @BindingAdapter("post_cert_items")
    @JvmStatic
    fun bindCertificationList(recyclerView: RecyclerView, items: ArrayList<CertItem>?) {
        recyclerView.apply {
            items?.let { items ->
                if (layoutManager == null) {
                    layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
                    addItemDecoration(object : RecyclerView.ItemDecoration() {
                        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                            super.getItemOffsets(outRect, view, parent, state)
                            val pos = parent.getChildLayoutPosition(view)
                            if (pos != 0) outRect.top += dp2px(4)
                        }
                    })
                }
                if (adapter == null) {
                    adapter = CertItemAdapter(items)
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
                if (layoutManager == null) {
                    layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
                    addItemDecoration(object : RecyclerView.ItemDecoration(){
                        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                            super.getItemOffsets(outRect, view, parent, state)
                            val pos = parent.getChildLayoutPosition(view)
                            if (pos == 0) return
                            outRect.top += dp2px(12)
                        }
                    })
                }
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