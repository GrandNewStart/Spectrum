package com.spectrum.spectrum.src.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.DialogEducationBinding
import com.spectrum.spectrum.src.models.EduItem

class EducationDialog(context: Context): Dialog(context, R.style.AppTheme) {

    private lateinit var mBinding: DialogEducationBinding
    private var onSaveListener: (eduItem: EduItem)->Unit = {}
    val mLevels = listOf ("학교 구분", "고졸이하", "고등학교", "대학교(2,3년제)", "대학교(4년제)", "석사", "박사", "박사이상")
    val mLocales = listOf("지역", "수도권", "지방", "해외", "지거국", "선택안함")
    val mGradStats = listOf("졸업 여부", "졸업", "재학중", "휴학", "중퇴")
    val mGradeSums = listOf("총점", "4.3", "4.5")
    var mEduLevel: String? = null
    var mLocale: String? = null
    var mGradStat: String? = null
    var mSchool: String? = null
    var mMajor: String? = null
    var mGrade: Double? = null
    var mGradeSum: Double? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_education, null, false)
        mBinding.apply {
            dialog = this@EducationDialog
        }
        setCanceledOnTouchOutside(false)
        window?.apply {
            setBackgroundDrawable(ColorDrawable())
            setDimAmount(0.0f)
            attributes.let { params ->
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = WindowManager.LayoutParams.MATCH_PARENT
                params.windowAnimations = R.style.DialogSlideAnimation
            }
            setGravity(Gravity.END)
        }
        setContentView(mBinding.root)
    }

    fun setOnSaveListener(onSaveListener: (eduItem: EduItem)->Unit): EducationDialog {
        this.onSaveListener = onSaveListener
        return this
    }

    fun saveButtonAction() {
        val item = EduItem(null, null, null, null, null, null, null)
        mEduLevel?.let { it -> item.level = it.trim() }
        mLocale?.let { it -> item.locale = it.trim() }
        mGradStat?.let { it -> item.status = it.trim() }
        mSchool?.let { it -> item.school = it.trim() }
        mMajor?.let { it -> item.major = it.trim() }
        mGrade?.let { it -> item.grade = it }
        mGradeSum?.let { it -> item.gradeSum = it }
        onSaveListener(item)
        dismiss()
    }

}