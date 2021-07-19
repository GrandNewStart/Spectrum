package com.spectrum.spectrum.src.activities.main.fragments.createPost.adapters

import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.models.Spec

object BindingAdapters {

   @BindingAdapter("my_spec")
   @JvmStatic
   fun bindMySpec(view: View, spec: Spec) {
       when (view.id) {
           R.id.your_spec_text -> {
               val text = "${spec.userName} 님의 스펙"
               (view as TextView).text = text
           }
           R.id.update_time_text -> { (view as TextView).text = spec.update }
           R.id.chip_1 -> { (view as Chip).text = spec.userInfo[0].name }
           R.id.chip_2 -> { (view as Chip).text = spec.userInfo[1].name }
           R.id.chip_3 -> { (view as Chip).text = spec.userInfo[2].name }
           R.id.chip_4 -> { (view as Chip).text = spec.userInfo[3].name }
       }
   }

    @BindingAdapter("create_post_sub_spinner", "create_post_content_view", "create_post_content_title")
    @JvmStatic
    fun bindTypeSpinner(spinner: Spinner, subSpinner: Spinner, editText: EditText, textView: TextView) {
        val types = arrayListOf("종류", "취업준비", "n차합격", "최종합격")
        val subTypes = arrayListOf("주제")

        spinner.adapter = ArrayAdapter(spinner.context, android.R.layout.simple_dropdown_item_1line, types)
        subSpinner.adapter = ArrayAdapter(spinner.context, android.R.layout.simple_dropdown_item_1line, subTypes)
        textView.text = Constants.free_form_text

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var items = ArrayList<String>()
                when (position) {
                    0 -> { items = arrayListOf("주제") }
                    1 -> { items = arrayListOf("주제", "자유고민") }
                    2 -> { items = arrayListOf("주제", "자유고민", "자유후기") }
                    3 -> { items = arrayListOf("주제", "자유후기") }
                }
                subSpinner.adapter = ArrayAdapter(spinner.context, android.R.layout.simple_dropdown_item_1line, items)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        subSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    0 -> {
                        textView.text = Constants.free_form_text
                        editText.hint = Constants.free_form_text_hint
                    }
                    1 -> {
                        textView.text = Constants.free_consults
                        editText.hint = Constants.free_consults_hint
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