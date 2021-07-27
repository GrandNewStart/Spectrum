package com.spectrum.spectrum.src.activities.main.fragments.editpost.adapters

import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.editpost.models.Post
import com.spectrum.spectrum.src.activities.main.fragments.editpost.models.Spec
import com.spectrum.spectrum.src.config.Constants

object BindingAdapters {

    @BindingAdapter("edit_post_spec")
    @JvmStatic
    fun bindMySpecView(view: View, spec: Spec?) {
        when(view.id) {
            R.id.spec_card_view -> {
                view.visibility = if (spec == null) View.GONE else View.VISIBLE
            }
            R.id.no_spec_view -> {
                view.visibility = if (spec == null) View.VISIBLE else View.GONE
            }
            R.id.your_spec_text -> {
                val text = "${spec?.username} 님의 스펙"
                (view as TextView).text = text
            }
            R.id.update_time_text -> {
                val text = "${spec?.updatedAt}에 업데이트됨"
                (view as TextView).text = text
            }
            R.id.user_info_chip_group -> {
                val spec = spec ?: return
                (view as ChipGroup).let { group ->
                    group.removeAllViews()
                    Chip(group.context).let { chip ->
                        val t = "${spec.age}세"
                        chip.text = t
                        chip.setEnsureMinTouchTargetSize(false)
                        chip.setChipBackgroundColorResource(R.color.spectrumSilver2)
                        chip.setTextAppearance(R.style.ChipTextSmall)
                        group.addView(chip)
                    }
                    Chip(group.context).let { chip ->
                        chip.text = spec.sex
                        chip.setEnsureMinTouchTargetSize(false)
                        chip.setChipBackgroundColorResource(R.color.spectrumSilver2)
                        chip.setTextAppearance(R.style.ChipTextSmall)
                        group.addView(chip)
                    }
                    spec.jobGroups.forEach {
                        Chip(group.context).let { chip ->
                            chip.text = it.name
                            chip.setEnsureMinTouchTargetSize(false)
                            chip.setChipBackgroundColorResource(R.color.spectrumSilver2)
                            chip.setTextAppearance(R.style.ChipTextSmall)
                            group.addView(chip)
                        }
                    }
                }
            }
        }
    }

    @BindingAdapter("edit_post_post")
    @JvmStatic
    fun bindMyPostView(view: View, post: Post?) {
        val post = post ?: return
        when(view.id) {
            R.id.title_edit_text -> {
                (view as TextView).text = post.title
            }
            R.id.content_edit_text -> {
                (view as EditText).setText(post.content)
            }
        }
    }

    @BindingAdapter("edit_post_category_spinner", "edit_post_post", "edit_post_content_title", "edit_post_content_view", requireAll = true)
    @JvmStatic
    fun bindSelectedSpinnerItems(statusSpinner: Spinner,
                                 categorySpinner: Spinner,
                                 post: Post?,
                                 textView: TextView,
                                 editText: EditText) {
        val post = post ?: return
        val status = post.jobStatus ?: return
        val category = post.category ?: return

        val statusList = arrayListOf("취업준비", "n차합격", "최종합격")
        statusSpinner.adapter = ArrayAdapter(statusSpinner.context, android.R.layout.simple_dropdown_item_1line, statusList)

        when(status.id) {
            // 취업준비
            "R"->{
                statusSpinner.setSelection(0)
                categorySpinner.adapter = ArrayAdapter(categorySpinner.context, android.R.layout.simple_dropdown_item_1line, arrayListOf("자유고민"))
                // 자유고민
                categorySpinner.setSelection(0)
            }
            // n차합격
            "N"->{
                statusSpinner.setSelection(1)
                categorySpinner.adapter = ArrayAdapter(categorySpinner.context, android.R.layout.simple_dropdown_item_1line, arrayListOf("자유고민", "자유후기"))
                // 자유고민
                if (category.id == "R") categorySpinner.setSelection(0)
                // 자유후기
                if (category.id == "N")categorySpinner.setSelection(1)
            }
            // 최종함격
            "F"->{
                statusSpinner.setSelection(2)
                categorySpinner.adapter = ArrayAdapter(categorySpinner.context, android.R.layout.simple_dropdown_item_1line, arrayListOf("자유후기"))
                // 자유후기
                categorySpinner.setSelection(0)
            }
        }

        statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var items = ArrayList<String>()
                when (position) {
                    0 -> { items = arrayListOf("자유고민") }
                    1 -> { items = arrayListOf("자유고민", "자유후기") }
                    2 -> { items = arrayListOf("자유후기") }
                }
                categorySpinner.adapter = ArrayAdapter(statusSpinner.context, android.R.layout.simple_dropdown_item_1line, items)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    0 -> {
                        if (statusSpinner.selectedItemPosition == 0){
                            textView.text = Constants.free_form_text
                            editText.hint = Constants.free_form_text_hint
                        }
                        if (statusSpinner.selectedItemPosition == 1) {
                            textView.text = Constants.free_form_text
                            editText.hint = Constants.free_form_text_hint
                        }
                        if (statusSpinner.selectedItemPosition == 2) {
                            textView.text = Constants.free_reviews
                            editText.hint = Constants.free_reviews_hint
                        }
                    }
                    1 -> {
                        if (statusSpinner.selectedItemPosition == 1) {
                            textView.text = Constants.free_reviews
                            editText.hint = Constants.free_reviews_hint
                        }
                        if (statusSpinner.selectedItemPosition == 2) {
                            textView.text = Constants.free_reviews
                            editText.hint = Constants.free_reviews_hint
                        }
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

}