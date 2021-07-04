package com.spectrum.spectrum.src.activities.login.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.LogInActivity
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.customs.BaseFragment
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
                   DatePickerDialog().apply{
                       val text = findViewById<MaterialTextView>(R.id.age_text)
                       onDateSelected = { date ->
                           (activity as LogInActivity).mAge = date
                           text.text = "${estimateAge(date)}"
                           text.setTextColor(resources.getColor(R.color.spectrumGray2, null))
                       }
                       onNothingSelected = {
                           if ((activity as LogInActivity).mAge == null) {
                               text.text = Constants.select_age
                               text.setTextColor(resources.getColor(R.color.spectrumSilver3, null))
                           }
                       }
                       show(this@Step3Fragment.parentFragmentManager, null)
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
            findViewById<ImageButton>(R.id.academic_plus).apply {
                setOnClickListener {
                    findNavController().navigate(R.id.step3_to_education)
                }
            }
            // 경력 설정
            // 자격/어학 설정
            // 기타 스펙 설정
            // 저장
            findViewById<MaterialButton>(R.id.save).setOnClickListener {
                Log.d(TAG, "---> AGE: ${(activity as LogInActivity).mAge}")
                Log.d(TAG, "---> SEX: ${(activity as LogInActivity).mSex}")
                Log.d(TAG, "---> GROUP1: ${(activity as LogInActivity).mJobGroup1}")
                Log.d(TAG, "---> GROUP2: ${(activity as LogInActivity).mJobGroup1}")
            }
        }
        return view
    }

    private fun estimateAge(date: String): Int {
        var result: Int
        val birthYear = date.substring(0..3).toInt()
        val birthMonth = date.substring(4..5).toInt()
        val birthDay = date.substring(6..7).toInt()
        Calendar.getInstance().apply {
            val year = get(Calendar.YEAR)
            val month = get(Calendar.MONTH) + 1
            val day = get(Calendar.DAY_OF_MONTH)

            result = year - birthYear

            if (month > birthMonth) {
                return result
            }
            if (month == birthMonth) {
                if (day >= birthDay) {
                    return result
                }
            }

            result -= 1;
            return result
        }
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
                        text = "${estimateAge(mAge!!)}"
                        setTextColor(resources.getColor(R.color.spectrumGray3, null))
                    }
                }
                findViewById<RadioGroup>(R.id.sex_radio_group).apply {
                    mSex?.let { sex ->
                        if (sex == "M") { check(R.id.male) }
                        if (sex == "F") { check(R.id.female) }
                    }
                }

                findViewById<MaterialTextView>(R.id.group_text).apply {
                    if (mJobGroup1 == null) {
                        text = Constants.select_your_job_group
                        setTextColor(resources.getColor(R.color.spectrumSilver3, null))
                    }
                    else {
                        setTextColor(resources.getColor(R.color.spectrumGray3, null))
                        text = if (mJobGroup2 == null) {
                            mJobGroup1?.name
                        } else {
                            "${mJobGroup1?.name}, ${mJobGroup2?.name}"
                        }
                    }
                }
            }
        }
    }
}