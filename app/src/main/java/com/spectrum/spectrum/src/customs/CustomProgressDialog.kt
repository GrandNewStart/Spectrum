package com.spectrum.spectrum.src.customs

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.spectrum.spectrum.R

class CustomProgressDialog(context: Context): Dialog(context) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.apply {
            setBackgroundDrawable(ColorDrawable())
            setDimAmount(0.2f)
            setContentView(R.layout.dialog_progress)
        }
    }

}