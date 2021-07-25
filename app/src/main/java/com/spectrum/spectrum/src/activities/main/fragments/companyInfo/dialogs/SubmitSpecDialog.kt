package com.spectrum.spectrum.src.activities.main.fragments.companyInfo.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.google.android.material.chip.Chip
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.DialogSubmitSpecBinding
import com.spectrum.spectrum.src.activities.main.fragments.companyInfo.interfaces.CompanyInfoApi
import com.spectrum.spectrum.src.config.Helpers.retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubmitSpecDialog(context: Context): Dialog(context, R.style.AppTheme) {

    private lateinit var mBinding: DialogSubmitSpecBinding
    private var mOnDoneListener: (didEnter: Boolean)->Unit = {_->}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_submit_spec, null, false)
        mBinding.dialog = this
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
        getMySpec()
        setContentView(mBinding.root)
    }

    @SuppressLint("SetTextI18n")
    private fun getMySpec() {
        CoroutineScope(Dispatchers.Main).launch {
            retrofit.create(CompanyInfoApi::class.java).getMySpec().apply {
                if (isSuccess) {
                    mBinding.yourSpecText.text = "${result.username} 님의 스펙"
                    mBinding.updateTimeText.text = "${result.updatedAt} 업데이트됨"
                    mBinding.userInfoChipGroup.apply {
                        Chip(context).apply {
                            text = "${result.age}세"
                            setTextAppearance(R.style.ChipText)
                            setChipBackgroundColorResource(R.color.spectrumSilver2)
                            setEnsureMinTouchTargetSize(false)
                            addView(this)
                        }
                        Chip(context).apply {
                            text = result.sex
                            setTextAppearance(R.style.ChipText)
                            setChipBackgroundColorResource(R.color.spectrumSilver2)
                            setEnsureMinTouchTargetSize(false)
                            addView(this)
                        }
                        result.jobGroups.forEach {
                            Chip(context).apply {
                                text = it.name
                                setTextAppearance(R.style.ChipText)
                                setChipBackgroundColorResource(R.color.spectrumSilver2)
                                setEnsureMinTouchTargetSize(false)
                                addView(this)
                            }
                        }
                    }
                    return@launch
                }
            }
        }
    }

    fun doneButtonAction() {
        mOnDoneListener(mBinding.checkBox.isChecked)
        dismiss()
    }

    fun setOnDoneListener(onDoneListener: (didEnter: Boolean)->Unit): SubmitSpecDialog {
        mOnDoneListener = onDoneListener
        return this
    }

    companion object {
        val TAG = SubmitSpecDialog::class.java.simpleName.toString()
    }

}