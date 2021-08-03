package com.spectrum.spectrum.src.activities.main.fragments.editSpec.adapters

import android.content.Context
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.editSpec.EditSpecFragment
import com.spectrum.spectrum.src.activities.main.fragments.editSpec.EditSpecViewModel
import com.spectrum.spectrum.src.adapters.EducationAdapter
import com.spectrum.spectrum.src.adapters.ExperienceAdapter
import com.spectrum.spectrum.src.adapters.LicenseAdapter
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.models.*

object BindingAdapters {

    @BindingAdapter("edit_spec_job_group")
    @JvmStatic
    fun bindJobGroupTextView(textView: TextView, jobGroup: JobGroup?) {
        textView.text = jobGroup?.name ?: Constants.select_your_job_group
    }

    @BindingAdapter("edit_spec_age")
    @JvmStatic
    fun bindAgeTextView(textView: TextView, age: Int?) {
        textView.apply {
            age?.let {
                text = it.toString()
                setTextColor(resources.getColor(R.color.black, null))
            }
        }
    }

    @BindingAdapter("edit_spec_sex", "edit_spec_male", "edit_spec_female", "edit_spec_view_model")
    @JvmStatic
    fun bindSexRadioGroup(radioGroup: RadioGroup, sex: Int?, male: RadioButton, female: RadioButton, viewModel: EditSpecViewModel) {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == male.id) {
                viewModel.mSex = 0
            }
            if (checkedId == female.id) {
                viewModel.mSex = 1
            }
        }
        sex?.let {
            radioGroup.findViewById<RadioButton>(R.id.male_button).isChecked = it == 0
            radioGroup.findViewById<RadioButton>(R.id.female_button).isChecked = it == 1
        }
    }

    @BindingAdapter("edit_spec_educations")
    @JvmStatic
    fun bindEduItems(recyclerView: RecyclerView, items: ObservableArrayList<Education>?) {
        val eduItems = items ?: ObservableArrayList()
        recyclerView.apply {
            visibility = if (eduItems.isEmpty()) View.GONE else View.VISIBLE
            adapter = EducationAdapter(eduItems)
        }
    }

    @BindingAdapter("edit_spec_experiences")
    @JvmStatic
    fun bindExpItems(recyclerView: RecyclerView, items: ObservableArrayList<Experience>?) {
        val expItems = items ?: ObservableArrayList()
        recyclerView.apply {
            visibility = if (expItems.isEmpty()) View.GONE else View.VISIBLE
            adapter = ExperienceAdapter(expItems)
        }
    }

    @BindingAdapter("edit_spec_licenses")
    @JvmStatic
    fun bindCertItems(recyclerView: RecyclerView, items: ObservableArrayList<License>?) {
        val licenses = items ?: ObservableArrayList()
        recyclerView.apply {
            visibility = if (licenses.isEmpty()) View.GONE else View.VISIBLE
            adapter = LicenseAdapter(licenses)
        }
    }

    @BindingAdapter("edit_spec_fragment")
    @JvmStatic
    fun bindCommentEditText(editText: TextInputEditText, fragment: EditSpecFragment) {
        editText.apply { fragment.apply {
            addTextChangedListener { s ->
                val t = s.toString().trim()
                if (t.isEmpty()) {
                    mViewModel.mOtherSpecs = null
                    return@addTextChangedListener
                }
                mViewModel.mOtherSpecs = t
            }
        } }
    }

}