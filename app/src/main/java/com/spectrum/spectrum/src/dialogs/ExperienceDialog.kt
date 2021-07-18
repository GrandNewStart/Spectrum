package com.spectrum.spectrum.src.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.DialogExperienceBinding
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.compareDates
import com.spectrum.spectrum.src.models.ExpItem

class ExperienceDialog(context: Context): Dialog(context, R.style.AppTheme) {

    private lateinit var mBinding: DialogExperienceBinding
    private var onSaveListener: (expItem: ExpItem)->Unit = {}
    var mCompany: String? = null
    var mDuty: String? = null
    var mPosition: String? = null
    var mStartDate: String? = null
    var mEndDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_experience, null, false)
        mBinding.apply {
            dialog = this@ExperienceDialog
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

    fun saveButtonAction() {
        if (mCompany == null || mDuty == null || mPosition == null || mStartDate == null || mEndDate == null) {
            Toast.makeText(context, Constants.enter_every_fields, Toast.LENGTH_SHORT).show()
            return
        }

        if (!compareDates(mStartDate, mEndDate)) {
            Toast.makeText(context, Constants.period_error, Toast.LENGTH_SHORT).show()
            return
        }

        val item = ExpItem(null ,null, null, null, null)
        mCompany?.let { item.company = it }
        mDuty?.let { item.duty = it }
        mPosition?.let { item.position = it }
        mStartDate?.let { item.startDate = it }
        mEndDate?.let { item.endDate = it }
        onSaveListener(item)

        dismiss()
    }

    fun setOnSaveListener(onSaveListener: (expItem: ExpItem)->Unit): ExperienceDialog {
        this.onSaveListener = onSaveListener
        return this
    }

}