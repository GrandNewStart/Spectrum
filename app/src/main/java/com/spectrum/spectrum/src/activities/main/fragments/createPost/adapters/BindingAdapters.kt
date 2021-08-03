package com.spectrum.spectrum.src.activities.main.fragments.createPost.adapters

import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.createPost.models.Spec
import com.spectrum.spectrum.src.config.Constants

object BindingAdapters {

   @BindingAdapter("my_spec")
   @JvmStatic
   fun bindMySpec(view: View, spec: Spec?) {
       when (view.id) {
           R.id.spec_card_view -> {
               view.visibility = if (spec == null) View.GONE else View.VISIBLE
           }
           R.id.no_spec_view -> {
               view.visibility = if (spec == null) View.VISIBLE else View.GONE
           }
           R.id.your_spec_text -> {
               val text = "${spec?.username}님의 스펙"
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

    @BindingAdapter("create_post_category_spinner", "create_post_content_view", "create_post_content_title", requireAll = true)
    @JvmStatic
    fun bindTypeSpinner(statusSpinner: Spinner,
                        categorySpinner: Spinner,
                        editText: EditText,
                        textView: TextView) {
        val statusList = arrayListOf("종류", "취업준비", "n차합격", "최종합격")
        val categoryList = arrayListOf("주제", "자유고민", "자유후기")

        statusSpinner.adapter = ArrayAdapter(statusSpinner.context, R.layout.item_spinner, statusList)
        categorySpinner.adapter = ArrayAdapter(categorySpinner.context, R.layout.item_spinner, categoryList)
        textView.text = Constants.free_form_text

        statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var items = ArrayList<String>()
                when (position) {
                    0 -> { items = arrayListOf("주제")}
                    1 -> { items = arrayListOf("주제", "자유고민") }
                    2 -> { items = arrayListOf("주제", "자유고민", "자유후기") }
                    3 -> { items = arrayListOf("주제", "자유후기") }
                }
                categorySpinner.adapter = ArrayAdapter(statusSpinner.context, R.layout.item_spinner, items)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    0 -> {
                        textView.text = Constants.free_form_text
                        editText.hint = Constants.free_form_text_hint
                    }
                    1 -> {
                        if (statusSpinner.selectedItemPosition == 3) {
                            textView.text = Constants.free_reviews
                            editText.hint = Constants.free_reviews_hint
                        }
                        else {
                            textView.text = Constants.free_advices
                            editText.hint = Constants.free_advices_hint
                        }
                    }
                    2 -> {
                        textView.text = Constants.free_reviews
                        editText.hint = Constants.free_reviews_hint
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }
    }

}