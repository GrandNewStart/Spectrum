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
import androidx.appcompat.widget.AppCompatSpinner
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.LogInActivity
import com.spectrum.spectrum.src.activities.login.models.EduItem
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.customs.BaseFragment

class EducationFragment: BaseFragment() {

    private val mList1 = listOf ("학교 구분", "고졸이하", "고등학교", "대학교(2,3년제)", "대학교(4년제)", "석사", "박사", "박사이상")
    private val mList2 = listOf("지역", "수도권", "지방", "해외", "지거국", "선택안함")
    private val mList3 = listOf("졸업 여부", "졸업", "재학중", "휴학", "중퇴")

    private var mEduLevel: String? = null
    private var mLocale: String? = null
    private var mGradStat: String? = null
    private var mSchool: String? = null
    private var mMajor: String? = null
    private var mGrades: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in_education, container, false)
        view.apply {
            findViewById<MaterialButton>(R.id.back).setOnClickListener {
                findNavController().popBackStack()
            }
            findViewById<MaterialTextView>(R.id.save).setOnClickListener {
                (activity as LogInActivity).apply {
                    if (mEduLevel == null || mGradStat == null) {
                        showToast(Constants.required_academic_information)
                        return@setOnClickListener
                    }
                    val item = EduItem(null, null, null, null, null, null)
                    mEduLevel?.let { it -> item.level = it.trim() }
                    mLocale?.let { it -> item.locale = it.trim() }
                    mGradStat?.let { it -> item.status = it.trim() }
                    mSchool?.let { it -> item.school = it.trim() }
                    mMajor?.let { it -> item.major = it.trim() }
                    mGrades?.let { it -> item.grades = it.trim() }
                    mEduItems.add(item)
                    findNavController().popBackStack()
                }
            }
            findViewById<AppCompatSpinner>(R.id.school_spinner).apply {
                adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, mList1)
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (position == 0) {
                            mEduLevel = null
                            return
                        }
                        mEduLevel = mList1[position]
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
            findViewById<AppCompatSpinner>(R.id.locale_spinner).apply {
                adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, mList2)
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (position == mList2.size - 1 || position == 0) {
                            mLocale = null
                            return
                        }
                        mLocale = mList2[position]
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
            findViewById<AppCompatSpinner>(R.id.graduation_spinner).apply {
                adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, mList3)
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (position == 0) {
                            mGradStat = null
                            return
                        }
                        mGradStat = mList3[position]
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
            findViewById<EditText>(R.id.school_name_edit_text).apply {
                 addTextChangedListener(object : TextWatcher{
                     override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                     override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
                     override fun afterTextChanged(s: Editable?) { mSchool = s.toString() }
                 })
            }
            findViewById<EditText>(R.id.major_edit_text).apply {
                addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
                    override fun afterTextChanged(s: Editable?) { mMajor = s.toString() }
                })
            }
            findViewById<EditText>(R.id.grades_edit_text).apply {
                addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
                    override fun afterTextChanged(s: Editable?) { mGrades = s.toString() }
                })
            }
        }
        return view
    }

}