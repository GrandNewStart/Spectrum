package com.spectrum.spectrum.src.activities.main.fragments.companyInfo.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.DialogSubmitSpecBinding
import com.spectrum.spectrum.src.activities.main.fragments.companyInfo.CompanyInfoFragment
import com.spectrum.spectrum.src.activities.main.fragments.companyInfo.interfaces.CompanyInfoApi
import com.spectrum.spectrum.src.config.Helpers.retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubmitSpecDialog(private val fragment: CompanyInfoFragment): Dialog(fragment.requireContext(), R.style.AppTheme) {

    private lateinit var mBinding: DialogSubmitSpecBinding
    private var mOnDoneListener: (didEnter: Boolean)->Unit = {_->}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_submit_spec, null, false)
        mBinding.apply {
            dialog = this@SubmitSpecDialog
            val text = addSpecText.text.toString()
            val spannable = SpannableString(text).apply {
                val blue = addSpecText.resources.getColor(R.color.spectrumBlue, null)
                setSpan(ForegroundColorSpan(blue), 0, 6, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            addSpecText.text = spannable
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
        getMySpec()
        setContentView(mBinding.root)
    }

    @SuppressLint("SetTextI18n")
    private fun getMySpec() {
        CoroutineScope(Dispatchers.Main).launch {
            retrofit.create(CompanyInfoApi::class.java).getMySpec().apply {
                if (isSuccess) {
                    mBinding.specView.visibility = View.VISIBLE
                    mBinding.doneButton.visibility = View.VISIBLE
                    mBinding.addSpecView.visibility = View.GONE
                    mBinding.yourSpecText.text = "${result.username} 님의 스펙"
                    mBinding.updateTimeText.text = "${result.updatedAt} 업데이트됨"
                    mBinding.userInfoChipGroup.apply {
                        Chip(context).apply {
                            text = "${result.age}세"
                            setTextAppearance(R.style.ChipTextSmall)
                            setChipBackgroundColorResource(R.color.spectrumSilver2)
                            setEnsureMinTouchTargetSize(false)
                            addView(this)
                        }
                        Chip(context).apply {
                            text = result.sex
                            setTextAppearance(R.style.ChipTextSmall)
                            setChipBackgroundColorResource(R.color.spectrumSilver2)
                            setEnsureMinTouchTargetSize(false)
                            addView(this)
                        }
                        result.jobGroups.forEach {
                            Chip(context).apply {
                                text = it.name
                                setTextAppearance(R.style.ChipTextSmall)
                                setChipBackgroundColorResource(R.color.spectrumSilver2)
                                setEnsureMinTouchTargetSize(false)
                                addView(this)
                            }
                        }
                    }
                }
                else {
                    mBinding.specView.visibility = View.GONE
                    mBinding.doneButton.visibility = View.GONE
                    mBinding.addSpecView.visibility = View.VISIBLE
                }
            }
        }
    }

    fun addSpecButton() {
        fragment.findNavController().navigate(R.id.company_info_to_create_spec)
        dismiss()
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