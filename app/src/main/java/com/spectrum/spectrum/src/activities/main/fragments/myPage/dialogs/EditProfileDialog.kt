package com.spectrum.spectrum.src.activities.main.fragments.myPage.dialogs

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
import com.spectrum.spectrum.databinding.DialogEditProfileBinding
import com.spectrum.spectrum.src.config.Constants

class EditProfileDialog(context: Context): Dialog(context, R.style.AppTheme) {

    private lateinit var mBinding: DialogEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_edit_profile, null, false)
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
        setContentView(mBinding.root)
    }

    fun cameraButtonAction() {
        Toast.makeText(context, Constants.under_construction, Toast.LENGTH_SHORT).show()
    }

    fun doneButtonAction() {
        Toast.makeText(context, Constants.under_construction, Toast.LENGTH_SHORT).show()
    }

}