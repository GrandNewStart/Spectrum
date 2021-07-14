package com.spectrum.spectrum.src.activities.login.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.LogInActivity
import com.spectrum.spectrum.src.activities.login.dialogs.GradePickerDialog
import com.spectrum.spectrum.src.activities.login.models.EduItem
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.customs.BaseFragment
import kotlinx.android.synthetic.main.item_chip.view.*
import okhttp3.internal.notify

class EducationFragment: BaseFragment() {

    private val mLevels = listOf ("학교 구분", "고졸이하", "고등학교", "대학교(2,3년제)", "대학교(4년제)", "석사", "박사", "박사이상")
    private val mLocales = listOf("지역", "수도권", "지방", "해외", "지거국", "선택안함")
    private val mGradStats = listOf("졸업 여부", "졸업", "재학중", "휴학", "중퇴")
    private val mGradeSums = listOf("총점", "4.3", "4.5")

    private var mEduLevel: String? = null
    private var mLocale: String? = null
    private var mGradStat: String? = null
    private var mSchool: String? = null
    private var mMajor: String? = null
    private var mGrade: Double? = null
    private var mGradeSum: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in_education, container, false)
        view.apply {
            // 뒤로가기 (취소)
            findViewById<MaterialButton>(R.id.back).setOnClickListener {
                findNavController().popBackStack()
            }

            // 저장
            findViewById<MaterialTextView>(R.id.save).setOnClickListener {
                (activity as LogInActivity).apply {
                    if (mEduLevel == null || mGradStat == null) {
                        showToast(Constants.required_academic_information)
                        return@setOnClickListener
                    }
                    val item = EduItem(null, null, null, null, null, null, null)
                    mEduLevel?.let { it -> item.level = it.trim() }
                    mLocale?.let { it -> item.locale = it.trim() }
                    mGradStat?.let { it -> item.status = it.trim() }
                    mSchool?.let { it -> item.school = it.trim() }
                    mMajor?.let { it -> item.major = it.trim() }
                    mGrade?.let { it -> item.grade = it }
                    mGradeSum?.let { it -> item.gradeSum = it }
                    mEduItems.add(item)
                    findNavController().popBackStack()
                }
            }

            // 지역
            findViewById<AppCompatSpinner>(R.id.locale_spinner).apply {
                adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, mLocales)
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (position == mLocales.size - 1 || position == 0) {
                            mLocale = null
                            return
                        }
                        mLocale = mLocales[position]
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        mLocale = null
                    }
                }
                setSelection(0)
            }
            findViewById<MaterialButton>(R.id.locale_drop).setOnClickListener {
                findViewById<AppCompatSpinner>(R.id.locale_spinner).performClick()
            }

            // 학교 구분
            findViewById<AppCompatSpinner>(R.id.school_spinner).apply {
                adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, mLevels)
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (position == 0) {
                            mEduLevel = null
                            return
                        }
                        mEduLevel = mLevels[position]
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        mEduLevel = null
                    }
                }
                setSelection(0)
            }
            findViewById<MaterialButton>(R.id.school_drop).setOnClickListener {
                findViewById<AppCompatSpinner>(R.id.school_spinner).performClick()
            }

            // 졸업 여부
            findViewById<AppCompatSpinner>(R.id.graduation_spinner).apply {
                adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, mGradStats)
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (position == 0) {
                            mGradStat = null
                            return
                        }
                        mGradStat = mGradStats[position]
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        mGradStat = null
                    }
                }
                setSelection(0)
            }
            findViewById<MaterialButton>(R.id.graduation_drop).setOnClickListener {
                findViewById<AppCompatSpinner>(R.id.graduation_spinner).performClick()
            }

            // 학교 이름
            findViewById<EditText>(R.id.school_name_edit_text).apply {
                 addTextChangedListener(object : TextWatcher{
                     override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                     override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
                     override fun afterTextChanged(s: Editable?) { mSchool = s.toString() }
                 })
            }

            // 전공
            findViewById<EditText>(R.id.major_edit_text).apply {
                addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
                    override fun afterTextChanged(s: Editable?) { mMajor = s.toString() }
                })
            }

            // 학점
            val textView = findViewById<TextView>(R.id.grade_text)
            findViewById<ConstraintLayout>(R.id.grade_spinner).setOnClickListener {
                if (mGradeSum == null) {
                    showToast("총점을 먼저 선택해주세요")
                    return@setOnClickListener
                }
                mGradeSum?.let {
                    GradePickerDialog(context, it)
                        .setOnGradePickedListener { grade ->
                            textView.text = grade.toString()
                            mGrade = grade
                        }
                        .show()
                }
            }
            findViewById<MaterialButton>(R.id.grade_drop).setOnClickListener {
                if (mGradeSum == null) {
                    showToast("총점을 먼저 선택해주세요")
                    return@setOnClickListener
                }
                mGradeSum?.let {
                    GradePickerDialog(context, it)
                        .setOnGradePickedListener { grade ->
                            textView.text = grade.toString()
                            mGrade = grade
                        }
                        .show()
                }
            }
            findViewById<AppCompatSpinner>(R.id.grade_sum_spinner).apply {
                adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, mGradeSums)
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (position == 0) {
                            mGradeSum = null
                            mGrade = null
                            textView.text = Constants.grade
                        }
                        if (position == 1) {
                            if (mGradeSum != 4.3) {
                                mGradeSum = 4.3
                                mGrade = null
                                textView.text = Constants.grade
                            }
                        }
                        if (position == 2) {
                            if (mGradeSum != 4.5) {
                                mGradeSum = 4.5
                                mGrade = null
                                textView.text = Constants.grade
                            }
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        mGradeSum = null
                        mGrade = null
                        textView.text = Constants.grade
                    }
                }
                setSelection(0)
            }
            findViewById<MaterialButton>(R.id.grade_sum_drop).setOnClickListener {
                findViewById<AppCompatSpinner>(R.id.grade_sum_spinner).performClick()
            }
        }
        return view
    }

}