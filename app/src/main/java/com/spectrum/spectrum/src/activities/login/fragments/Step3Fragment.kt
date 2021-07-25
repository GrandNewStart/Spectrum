package com.spectrum.spectrum.src.activities.login.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.LogInActivity
import com.spectrum.spectrum.src.adapters.LicenseAdapter
import com.spectrum.spectrum.src.adapters.EducationAdapter
import com.spectrum.spectrum.src.adapters.ExperienceAdapter
import com.spectrum.spectrum.src.models.License
import com.spectrum.spectrum.src.models.Education
import com.spectrum.spectrum.src.models.Experience
import com.spectrum.spectrum.src.models.JobGroup
import com.spectrum.spectrum.src.activities.main.MainActivity
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.customs.BaseFragment
import com.spectrum.spectrum.src.customs.CustomTextInputLayout
import com.spectrum.spectrum.src.dialogs.*

class Step3Fragment: BaseFragment() {

    private var mAge: Int? = null
    private var mSex: Int? = null
    private var mJobGroup1: JobGroup? = null
    private var mJobGroup2: JobGroup? = null
    private var mJobGroup3: JobGroup? = null
    private var mEducations = ObservableArrayList<Education>()
    private var mExperiences = ObservableArrayList<Experience>()
    private var mLicenses = ObservableArrayList<License>()
    private var mOtherSpecs: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in_step3, container, false)
        view.apply {
            // 건너뛰기
            findViewById<MaterialTextView>(R.id.skip).setOnClickListener {
                activity?.startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()
            }
            // 연령 설정
            findViewById<MaterialCardView>(R.id.age_card).apply {
                val textView = findViewById<MaterialTextView>(R.id.age_text)
               setOnClickListener {
                   AgePickerDialog(context).apply {
                       setOnAgePickedListener { age ->
                           mAge = age
                           textView.apply {
                               text = age.toString()
                               setTextColor(resources.getColor(R.color.spectrumGray3, null))
                           }
                       }
                       show()
                   }
               }
            }
            // 성별 설정
            findViewById<RadioGroup>(R.id.sex_radio_group).apply {
                setOnCheckedChangeListener { _, checkedId ->
                    if (checkedId == R.id.male) mSex = 0
                    if (checkedId == R.id.female) mSex = 1
                }
            }
            // 직군 설정
            findViewById<MaterialCardView>(R.id.group_card).setOnClickListener {
                JobGroupDialog(context)
                    .setPreselectedItems(mJobGroup1, mJobGroup2, mJobGroup3)
                    .setOnSaveListener{ first, second, third ->
                        mJobGroup1 = first
                        mJobGroup2 = second
                        mJobGroup3 = third
                        bindChipGroup(findViewById(R.id.job_group_chip_group))
                    }
                    .show()
            }
            // 학력 설정
            findViewById<ImageButton>(R.id.academic_plus).setOnClickListener {
                EducationDialog(context)
                    .setOnSaveListener { item ->
                        mEducations.add(item)
                        findViewById<RecyclerView>(R.id.academic_recycler_view).apply {
                            visibility = if (mEducations.isEmpty()) View.GONE else View.VISIBLE
                            adapter?.notifyDataSetChanged()
                        }
                    }.show()
            }
            findViewById<RecyclerView>(R.id.academic_recycler_view).apply {
                adapter = EducationAdapter(mEducations)
            }
            // 경력 설정
            findViewById<ImageButton>(R.id.experiences_plus).setOnClickListener {
                ExperienceDialog(context)
                    .setOnSaveListener { item ->
                        mExperiences.add(item)
                        findViewById<RecyclerView>(R.id.experience_recycler_view).apply {
                            visibility = if (mExperiences.isEmpty()) View.GONE else View.VISIBLE
                            adapter?.notifyDataSetChanged()
                        }
                    }.show()
            }
            findViewById<RecyclerView>(R.id.experience_recycler_view).apply {
                adapter = ExperienceAdapter(mExperiences)
            }
            // 자격/어학 설정
            findViewById<ImageButton>(R.id.certification_plus).setOnClickListener {
                LicenseDialog(context)
                    .setOnSaveListener { item ->
                        mLicenses.add(item)
                        findViewById<RecyclerView>(R.id.certification_recycler_view).apply {
                            visibility = if (mLicenses.isEmpty()) View.GONE else View.VISIBLE
                            adapter?.notifyDataSetChanged()
                        }
                    }.show()
            }
            findViewById<RecyclerView>(R.id.certification_recycler_view).apply {
                adapter = LicenseAdapter(mLicenses)
            }
            // 기타 스펙 설정
            findViewById<CustomTextInputLayout>(R.id.others_text_input_layout).apply {
                addOnEditTextAttachedListener { layout ->
                    layout.editText?.apply {
                        addTextChangedListener {
                            val input = it.toString().trim()
                            mOtherSpecs = if (input.isEmpty()) "" else input
                        }
                    }
                }
            }
            // 저장
            findViewById<MaterialButton>(R.id.save).setOnClickListener {
                if (mAge == null || mSex == null || mJobGroup1 == null) {
                    showToast(Constants.enter_required_fields)
                    return@setOnClickListener
                }
                val jobGroupList = ArrayList<Int>().apply {
                    mJobGroup1?.let { add(it.id) }
                    mJobGroup2?.let { add(it.id) }
                    mJobGroup3?.let { add(it.id) }
                }
                (activity as LogInActivity).updateSpecs(mAge, mSex, jobGroupList, mEducations, mExperiences, mLicenses, mOtherSpecs)
            }
        }
        return view
    }

    private fun bindChipGroup(chipGroup: ChipGroup) {
        chipGroup.apply {
            removeAllViews()
            if (mJobGroup1 == null) {
                visibility = View.GONE
                return@apply
            }
            visibility = View.VISIBLE
            mJobGroup1?.let { group ->
                createChip(group).apply {
                    setOnCloseIconClickListener {
                        mJobGroup1 = mJobGroup2
                        mJobGroup2 = mJobGroup3
                        mJobGroup3 = null
                        bindChipGroup(chipGroup)
                    }
                    addView(this)
                }
            }
            mJobGroup2?.let { group ->
                createChip(group).apply {
                    setOnCloseIconClickListener {
                        mJobGroup2 = mJobGroup3
                        mJobGroup3 = null
                        bindChipGroup(chipGroup)
                    }
                    addView(this)
                }
            }
            mJobGroup3?.let { group ->
                createChip(group).apply {
                    setOnCloseIconClickListener {
                        mJobGroup3 = null
                        bindChipGroup(chipGroup)
                    }
                    addView(this)
                }
            }
        }
    }

    private fun createChip(jobGroup: JobGroup?): Chip {
        return Chip(requireContext()).apply {
            jobGroup?.let { group ->
                setChipBackgroundColorResource(R.color.clear)
                setChipStrokeColorResource(R.color.spectrumBlue)
                setChipStrokeWidthResource(R.dimen.default_stroke_width)
                setCloseIconResource(R.drawable.icon_close)
                setCloseIconTintResource(R.color.spectrumBlue)
                isCloseIconVisible = true
                setEnsureMinTouchTargetSize(false)
                text = group.name
            }
        }
    }
}