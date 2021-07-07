package com.spectrum.spectrum.src.customs

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout
import com.spectrum.spectrum.R

class CustomTextInputLayout constructor(context: Context, attrs: AttributeSet): TextInputLayout(context, attrs) {

    var isChecked = false

    init {
        errorIconDrawable = null
        isErrorEnabled = true
        isHelperTextEnabled = true
        setEndIconTintList(null)
    }

    fun showClearButton(show: Boolean) {
        if (show) {
            isEndIconVisible = true
            setEndIconDrawable(R.drawable.ic_baseline_cancel_24)
            setEndIconOnClickListener { editText?.text?.clear() }
        }
        else {
            isEndIconVisible = false
            endIconDrawable = null
        }
    }

    fun setDone(done: Boolean) {
        if (done) {
            this.isChecked = true
            isEndIconVisible = true
            error = null
            setEndIconDrawable(R.drawable.ic_baseline_check_circle_24)
            setEndIconOnClickListener {  }
        }
        else {
            this.isChecked = false
            endIconDrawable = null
        }
    }

}