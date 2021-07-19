package com.spectrum.spectrum.src.dialogs

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.config.Helpers.formatDate
import com.spectrum.spectrum.src.models.JobGroup
import org.w3c.dom.Text

object BindingAdapters {

    // JOB GROUP DIALOG
    @BindingAdapter("dialog_job_group_items", "dialog_job_group")
    @JvmStatic
    fun bindChipGroup(chipGroup: ChipGroup, items: ArrayList<JobGroup>?, dialog: JobGroupDialog) {
        chipGroup.apply {
            setChipSpacing(0)
            items?.forEach {
                val chip = Chip(context).apply {
                    text = it.name
                    val drawable = ChipDrawable.createFromAttributes(context, null, 0, R.style.CustomChip)
                    setChipDrawable(drawable)
                    setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            if (dialog.mFirstItem == null) {
                                dialog.mFirstItem = it
                                return@setOnCheckedChangeListener
                            }
                            if (dialog.mSecondItem == null) {
                                dialog.mSecondItem = it
                                return@setOnCheckedChangeListener
                            }
                            setChecked(false)
                        }
                        else {
                            dialog.mSecondItem?.apply {
                                if (id == it.id) {
                                    dialog.mSecondItem = null
                                    return@setOnCheckedChangeListener
                                }
                            }
                            dialog.mFirstItem?.apply {
                                if (id == it.id) {
                                    dialog.mFirstItem = dialog.mSecondItem
                                    dialog.mSecondItem = null
                                    return@setOnCheckedChangeListener
                                }
                            }
                        }
                    }
                }
                addView(chip)
            }
        }
    }

    // EDUCATION DIALOG
    @BindingAdapter("education_dialog", "education_dialog_grade_text")
    @JvmStatic
    fun bindEducationDialogSpinners(spinner: Spinner, dialog: EducationDialog, textView: TextView) {
        spinner.apply { dialog.apply {
            when (spinner.id) {
                R.id.locale_spinner -> {
                    spinner.apply {
                        adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, mLocales)
                        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                if (position == dialog.mLocales.size - 1 || position == 0) {
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
                }
                R.id.school_spinner -> {
                    spinner.apply {
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
                }
                R.id.graduation_spinner -> {
                    spinner.apply {
                        adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, mGradStats)
                        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                if (position == 0) {
                                    mGradStat = null
                                    return
                                }
                                mGradStat = dialog.mGradStats[position]
                            }
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                mGradStat = null
                            }
                        }
                        setSelection(0)
                    }
                }
                R.id.grade_sum_spinner -> {
                    spinner.apply {
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
                }
            }
        } }
    }

    @BindingAdapter("education_dialog", "education_dialog_grade_text")
    @JvmStatic
    fun bindGradeSpinner(view:View, dialog: EducationDialog, textView: TextView) {
        dialog.apply { view.apply {
            setOnClickListener {
                if (dialog.mGradeSum == null) {
                    Toast.makeText(context, "총점을 먼저 선택해주세요", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                dialog.mGradeSum?.let {
                    GradePickerDialog(context, it)
                        .setOnGradePickedListener { grade ->
                            textView.text = grade.toString()
                            mGrade = grade
                        }
                        .show()
                }
            }
        }
    } }

    @BindingAdapter("education_dialog")
    @JvmStatic
    fun bindEducationDialogEditText(editText: EditText, dialog: EducationDialog) {
        dialog.apply { editText.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
                override fun afterTextChanged(s: Editable?) {
                    when(id) {
                        R.id.school_name_edit_text -> { mSchool = s.toString() }
                        R.id.major_edit_text -> { mMajor = s.toString() }
                    }
                }
            })
        } }
    }

    // EXPERIENCE DIALOG
    @BindingAdapter("experience_dialog")
    @JvmStatic
    fun bindExperienceDialogEditText(editText: EditText, dialog: ExperienceDialog) {
        editText.apply { dialog.apply {
            addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    val text = s.toString().trim()
                    when(id) {
                        R.id.company_edit_text -> {
                            if (text.isEmpty()) {
                                mCompany = null
                                return
                            }
                            mCompany = s.toString()
                        }
                        R.id.duty_edit_text -> {
                            if (text.isEmpty()) {
                                mDuty = null
                                return
                            }
                            mDuty = s.toString()
                        }
                        R.id.position_edit_text -> {
                            if (text.isEmpty()) {
                                mPosition = null
                                return
                            }
                            mPosition = s.toString()
                        }
                    }
                }
            })
        }}
    }

    @BindingAdapter("experience_dialog", "experience_start_text", "experience_end_text")
    @JvmStatic
    fun bindExperienceDateView(view: View, dialog: ExperienceDialog, startText: TextView?, endText: TextView?) {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        view.apply { dialog.apply {
            val startDateDialog = DatePickerDialog(
                context,
                { _: DatePicker, y: Int, m: Int, d: Int ->
                    val date = formatDate(y, m, d)
                    startText?.text = date
                    mStartDate = date
                },
                year, month, day)
            val endDateDialog =  DatePickerDialog(
                context,
                { _: DatePicker, y: Int, m: Int, d: Int ->
                    val date = formatDate(y, m, d)
                    endText?.text = date
                    mEndDate = date
                },
                year , month, day)
            setOnClickListener {
                startText?.let { startDateDialog.show() }
                endText?.let { endDateDialog.show() }
            }
        } }
    }

    // CERTIFICATION DIALOG
    @BindingAdapter("certification_dialog")
    @JvmStatic
    fun bindCertificationEditText(editText: EditText, dialog: CertificationDialog) {
        editText.apply { dialog.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    val text = s.toString().trim()
                    when(id) {
                        R.id.name_edit_text -> {
                            if (text.isEmpty()) {
                                mName = null
                                return
                            }
                            mName = s.toString().trim()
                        }
                        R.id.score_edit_text -> {
                            if (text.isEmpty()) {
                                mScore = null
                                return
                            }
                            mScore = s.toString().trim()
                        }
                    }
                }
            })
        } }
    }
}