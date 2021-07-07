package com.spectrum.spectrum.src.activities.login.fragments

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.LogInActivity
import com.spectrum.spectrum.src.activities.login.adapters.CertItemAdapter
import com.spectrum.spectrum.src.activities.login.adapters.EduItemAdapter
import com.spectrum.spectrum.src.activities.login.adapters.ExpItemAdapter
import com.spectrum.spectrum.src.activities.login.dialogs.AgePickerDialog
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.dp2px
import com.spectrum.spectrum.src.customs.BaseFragment
import com.spectrum.spectrum.src.customs.CustomTextInputLayout
import com.spectrum.spectrum.src.dialogs.DatePickerDialog
import java.util.*

class Step3Fragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in_step3, container, false)
        view.apply {
            // 건너뛰기
            findViewById<MaterialTextView>(R.id.skip).setOnClickListener {
                // TODO: to MainActivity
            }
            // 연령 설정
            findViewById<MaterialCardView>(R.id.age_card).apply {
               setOnClickListener {
                   AgePickerDialog(context).apply {
                       setOnAgePickedListener { age ->
                           (activity as LogInActivity).mAge = age
                       }
                       show()
                   }
               }
            }
            // 성별 설정
            findViewById<RadioGroup>(R.id.sex_radio_group).apply {
                setOnCheckedChangeListener { group, checkedId ->
                    if (checkedId == R.id.male)     (activity as LogInActivity).mSex = "M"
                    if (checkedId == R.id.female)   (activity as LogInActivity).mSex = "F"
                }
            }
            // 직군 설정
            findViewById<MaterialCardView>(R.id.group_card).setOnClickListener {
                findNavController().navigate(R.id.step4_to_job_group)
            }
            // 학력 설정
            findViewById<ImageButton>(R.id.academic_plus).setOnClickListener {
                findNavController().navigate(R.id.step3_to_education)
            }
            findViewById<RecyclerView>(R.id.academic_recycler_view).apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = EduItemAdapter((activity as LogInActivity).mEduItems)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.top = dp2px(5)
                        outRect.bottom = dp2px(5)
                    }
                })
            }
            // 경력 설정
            findViewById<RecyclerView>(R.id.experience_recycler_view).apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = ExpItemAdapter((activity as LogInActivity).mExpItems)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.top = dp2px(5)
                        outRect.bottom = dp2px(5)
                    }
                })
            }
            findViewById<ImageButton>(R.id.experiences_plus).setOnClickListener {
                findNavController().navigate(R.id.step3_to_experience)
            }
            // 자격/어학 설정
            findViewById<ImageButton>(R.id.certification_plus).setOnClickListener {
                findNavController().navigate(R.id.log_in_step3_to_certification)
            }
            findViewById<RecyclerView>(R.id.certification_recycler_view).apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = CertItemAdapter((activity as LogInActivity).mCertItems)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.top = dp2px(5)
                        outRect.bottom = dp2px(5)
                    }
                })
            }
            // 기타 스펙 설정
            findViewById<CustomTextInputLayout>(R.id.others_text_input_layout).apply {
                addOnEditTextAttachedListener { layout ->
                    layout.editText?.apply {
                        val input = text.toString().trim()
                        (activity as LogInActivity).mComments = if (input.isEmpty()) null else input
                    }
                }
            }
            // 저장
            findViewById<MaterialButton>(R.id.save).setOnClickListener {
                (activity as LogInActivity).apply {
                    Log.d(TAG, "---> AGE: $mAge")
                    Log.d(TAG, "---> SEX: $mSex")
                    Log.d(TAG, "---> GROUP1: $mJobGroup1")
                    Log.d(TAG, "---> GROUP2: $mJobGroup2")
                    Log.d(TAG, "---> EDUCATIONS")
                    for (item in mEduItems) { Log.d(TAG, "      ㄴ: ${item.school}") }
                    Log.d(TAG, "---> EXPERIENCES")
                    for (item in mExpItems) { Log.d(TAG, "      ㄴ: ${item.company}") }
                    Log.d(TAG, "---> CERTIFICATIONS")
                    for (item in mCertItems) { Log.d(TAG, "      ㄴ: ${item.name}") }
                    Log.d(TAG, "---> COMMENTS: $mComments")
                }
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as LogInActivity).apply {
            view?.apply {
                findViewById<MaterialTextView>(R.id.age_text).apply {
                    if (mAge == null) {
                        text = Constants.select_age
                        setTextColor(resources.getColor(R.color.spectrumSilver3, null))
                    }
                    else {
                        text = "${mAge!!}"
                        setTextColor(resources.getColor(R.color.spectrumGray3, null))
                    }
                }

                findViewById<RadioGroup>(R.id.sex_radio_group).apply {
                    mSex?.let { sex ->
                        if (sex == "M") { check(R.id.male) }
                        if (sex == "F") { check(R.id.female) }
                    }
                }

                findViewById<ChipGroup>(R.id.group_chips).let { group ->
                    group.removeAllViews()
                    group.visibility = if (mJobGroup1 == null) View.GONE else View.VISIBLE
                    (activity as LogInActivity).apply {
                        mJobGroup1?.let {
                            val chip = Chip(context)
                            val drawable = ChipDrawable.createFromAttributes(context, null, 0, R.style.BlueChip)
                            chip.text = it.name
                            chip.setChipDrawable(drawable)
                            chip.setTextColor(resources.getColor(R.color.white, null))
                            chip.isCloseIconVisible = true
                            chip.setOnCloseIconClickListener { group.removeView(chip) }
                            group.addView(chip)
                        }
                        mJobGroup2?.let {
                            val chip = Chip(context)
                            val drawable = ChipDrawable.createFromAttributes(context, null, 0, R.style.GreenChip)
                            chip.text = it.name
                            chip.setChipDrawable(drawable)
                            chip.setTextColor(resources.getColor(R.color.white, null))
                            chip.isCloseIconVisible = true
                            chip.setOnCloseIconClickListener { group.removeView(chip) }
                            group.addView(chip)
                        }
                        mJobGroup3?.let {
                            val chip = Chip(context)
                            val drawable = ChipDrawable.createFromAttributes(context, null, 0, R.style.OrangeChip)
                            chip.text = it.name
                            chip.setChipDrawable(drawable)
                            chip.setTextColor(resources.getColor(R.color.white, null))
                            chip.isCloseIconVisible = true
                            chip.setOnCloseIconClickListener { group.removeView(chip) }
                            group.addView(chip)
                        }
                    }
                }

                findViewById<RecyclerView>(R.id.academic_recycler_view).apply {
                    adapter?.notifyDataSetChanged()
                }

                findViewById<RecyclerView>(R.id.experience_recycler_view).apply {
                    adapter?.notifyDataSetChanged()
                }

                findViewById<RecyclerView>(R.id.certification_recycler_view).apply {
                    adapter?.notifyDataSetChanged()
                }

            }
        }
    }
}