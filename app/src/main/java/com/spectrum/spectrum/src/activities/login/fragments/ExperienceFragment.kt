package com.spectrum.spectrum.src.activities.login.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.LogInActivity
import com.spectrum.spectrum.src.activities.login.models.ExpItem
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.customs.BaseFragment
import java.text.SimpleDateFormat
import java.util.*

class ExperienceFragment: BaseFragment() {

    private var mCompany: String? = null
    private var mDuty: String? = null
    private var mPosition: String? = null
    private var mStartDate: String? = null
    private var mEndDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in_experience, container, false)
        view.apply {
            // 뒤로가기
            findViewById<MaterialButton>(R.id.back).setOnClickListener {
                findNavController().popBackStack()
            }

            // 저장
            findViewById<MaterialTextView>(R.id.save).setOnClickListener {
                (activity as LogInActivity).apply {
                    if (mCompany == null || mDuty == null || mPosition == null || mStartDate == null || mEndDate == null) {
                        showToast(Constants.enter_every_fields)
                        return@setOnClickListener
                    }
                    if (!compareDates()) {
                        showToast(Constants.period_error)
                        return@setOnClickListener
                    }
                    val item = ExpItem(mCompany, mDuty, mPosition, mStartDate, mEndDate)
                    mExpItems.add(item)
                    findNavController().popBackStack()
                }
            }

            // 기업명
            findViewById<EditText>(R.id.company).apply {
                addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        if (s.toString().trim().isEmpty()) {
                            mCompany = null
                            return
                        }
                        mCompany = s.toString()
                    }
                })
            }

            // 직무
            findViewById<EditText>(R.id.duty).apply {
                addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        if (s.toString().trim().isEmpty()) {
                            mDuty = null
                            return
                        }
                        mDuty = s.toString()
                    }
                })
            }

            // 직급
            findViewById<EditText>(R.id.position).apply {
                addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        if (s.toString().trim().isEmpty()) {
                            mPosition = null
                            return
                        }
                        mPosition = s.toString()
                    }
                })
            }

            // 기간
            val startText = findViewById<TextView>(R.id.period_start_text)
            val endText = findViewById<TextView>(R.id.period_end_text)
            val year = Calendar.getInstance().get(Calendar.YEAR)
            val month = Calendar.getInstance().get(Calendar.MONTH)
            val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            val startDateDialog = DatePickerDialog(
                context,
                { _: DatePicker, y: Int, m: Int, d: Int ->
                    val date = formatDate(y, m, d)
                    startText.text = date
                    mStartDate = date
                },
                year, month, day)
            val endDateDialog =  DatePickerDialog(
                context,
                { _: DatePicker, y: Int, m: Int, d: Int ->
                    val date = formatDate(y, m, d)
                    endText.text = date
                    mEndDate = date
                },
                year , month, day)
            findViewById<ConstraintLayout>(R.id.start_date).setOnClickListener { startDateDialog.show() }
            findViewById<ImageButton>(R.id.period_start_drop).setOnClickListener { startDateDialog.show() }
            findViewById<ConstraintLayout>(R.id.end_date).setOnClickListener { endDateDialog.show() }
            findViewById<ImageButton>(R.id.period_end_drop).setOnClickListener { endDateDialog.show() }
        }
        return view
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(year: Int, month: Int, day: Int): String {
        val formatter = SimpleDateFormat("yyyy.MM.dd")
        val date = Date(year-1900, month, day)
        return formatter.format(date)
    }

    private fun compareDates(): Boolean {
        if (mStartDate == null) return false
        if (mEndDate == null) return false

        val startYear = mStartDate!!.substring(0..3).toInt()
        val endYear = mEndDate!!.substring(0..3).toInt()
        if (startYear > endYear) return false
        if (startYear < endYear) return true

        val startMonth = mStartDate!!.substring(5..6).toInt()
        val endMonth = mEndDate!!.substring(5..6).toInt()
        if (startMonth > endMonth) return false
        if (startMonth < endMonth) return true

        val startDay = mStartDate!!.substring(8..9).toInt()
        val endDay = mEndDate!!.substring(8..9).toInt()
        if (startDay > endDay) return false

        return true
    }

}