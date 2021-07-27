package com.spectrum.spectrum.src.activities.main.fragments.createSpec.adapters

import android.content.Context
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.createSpec.CreateSpecFragment
import com.spectrum.spectrum.src.activities.main.fragments.createSpec.CreateSpecViewModel
import com.spectrum.spectrum.src.adapters.EducationAdapter
import com.spectrum.spectrum.src.adapters.ExperienceAdapter
import com.spectrum.spectrum.src.adapters.LicenseAdapter
import com.spectrum.spectrum.src.models.Education
import com.spectrum.spectrum.src.models.Experience
import com.spectrum.spectrum.src.models.JobGroup
import com.spectrum.spectrum.src.models.License

object BindingAdapters {

    @BindingAdapter("create_spec_job_group_1", "create_spec_job_group_2", "create_spec_job_group_3", "create_spec_fragment")
    @JvmStatic
    fun bindJobGroups(chipGroup: ChipGroup, job1: JobGroup?, job2: JobGroup?, job3: JobGroup?, fragment: CreateSpecFragment) {
        chipGroup.apply {
            visibility = if (job1 == null && job2 == null && job3 == null) View.GONE else View.VISIBLE
            removeAllViews()
            job1?.let {
                val chip = createChip(context).apply {
                    text = it.name
                    setOnCloseIconClickListener {
                        fragment.apply {
                            mViewModel.mJobGroup1 = mViewModel.mJobGroup2
                            mViewModel.mJobGroup2 = mViewModel.mJobGroup3
                            mViewModel.mJobGroup3 = null
                            mBinding.jobGroup1 = mViewModel.mJobGroup1
                            mBinding.jobGroup2 = mViewModel.mJobGroup2
                            mBinding.jobGroup3 = mViewModel.mJobGroup3
                        }
                    }
                }
                addView(chip)
            }
            job2?.let {
                val chip = createChip(context).apply {
                    text = it.name
                    setOnCloseIconClickListener {
                        fragment.apply {
                            mViewModel.mJobGroup2 = mViewModel.mJobGroup3
                            mViewModel.mJobGroup3= null
                            mBinding.jobGroup1 = mViewModel.mJobGroup1
                            mBinding.jobGroup2 = mViewModel.mJobGroup2
                            mBinding.jobGroup3 = mViewModel.mJobGroup3
                        }
                    }
                }
                addView(chip)
            }
            job3?.let {
                val chip = createChip(context).apply {
                    text = it.name
                    setOnCloseIconClickListener {
                        fragment.apply {
                            mViewModel.mJobGroup3 = null
                            mBinding.jobGroup1 = mViewModel.mJobGroup1
                            mBinding.jobGroup2 = mViewModel.mJobGroup2
                            mBinding.jobGroup3 = mViewModel.mJobGroup3
                        }
                    }
                }
                addView(chip)
            }
        }
    }

    private fun createChip(context: Context) : Chip {
        return Chip(context).apply {
            setChipBackgroundColorResource(R.color.clear)
            setChipStrokeColorResource(R.color.spectrumBlue)
            setChipStrokeWidthResource(R.dimen.default_stroke_width)
            setTextAppearance(R.style.ChipTextSmall)
            setTextColor(resources.getColor(R.color.spectrumBlue, null))
            setCloseIconResource(R.drawable.icon_close)
            setCloseIconTintResource(R.color.spectrumBlue)
            isCloseIconVisible = true
        }
    }

    @BindingAdapter("create_spec_age")
    @JvmStatic
    fun bindAgeTextView(textView: TextView, age: Int?) {
        textView.apply {
            age?.let {
                text = it.toString()
                setTextColor(resources.getColor(R.color.black, null))
            }
        }
    }

    @BindingAdapter("create_spec_sex", "create_spec_male", "create_spec_female", "create_spec_view_model")
    @JvmStatic
    fun bindSexRadioGroup(radioGroup: RadioGroup, sex: Int?, male: RadioButton, female: RadioButton, viewModel: CreateSpecViewModel) {
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

    @BindingAdapter("create_spec_educations")
    @JvmStatic
    fun bindEduItems(recyclerView: RecyclerView, items: ObservableArrayList<Education>?) {
        val eduItems = items ?: ObservableArrayList()
        recyclerView.apply {
            visibility = if (eduItems.isEmpty()) View.GONE else View.VISIBLE
            adapter = EducationAdapter(eduItems)
        }
    }

    @BindingAdapter("create_spec_experiences")
    @JvmStatic
    fun bindExpItems(recyclerView: RecyclerView, items: ObservableArrayList<Experience>?) {
        val expItems = items ?: ObservableArrayList()
        recyclerView.apply {
            visibility = if (expItems.isEmpty()) View.GONE else View.VISIBLE
            adapter = ExperienceAdapter(items ?: ObservableArrayList())
        }
    }

    @BindingAdapter("create_spec_licenses")
    @JvmStatic
    fun bindCertItems(recyclerView: RecyclerView, items: ObservableArrayList<License>?) {
        val licenses = items ?: ObservableArrayList()
        recyclerView.apply {
            visibility = if (licenses.isEmpty()) View.GONE else View.VISIBLE
            adapter = LicenseAdapter(licenses)
        }
    }

    @BindingAdapter("create_spec_fragment")
    @JvmStatic
    fun bindCommentEditText(editText: TextInputEditText, fragment: CreateSpecFragment) {
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