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
import com.spectrum.spectrum.databinding.DialogCerificationBinding
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.models.CertItem

class CertificationDialog(context: Context): Dialog(context, R.style.AppTheme) {

    private lateinit var mBinding: DialogCerificationBinding
    private var onSaveListener: (item: CertItem)->Unit = {}
    var mName: String? = null
    var mScore: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_cerification, null, false)
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

    fun saveButtonAction() {
        if(mName == null || mScore == null) {
            Toast.makeText(context, Constants.enter_every_fields, Toast.LENGTH_SHORT).show()
            return
        }
        onSaveListener(CertItem(mName, mScore))
        dismiss()
    }

    fun setOnSaveListener(onSaveListener: (item: CertItem)->Unit): CertificationDialog {
        this.onSaveListener = onSaveListener
        return this
    }

}