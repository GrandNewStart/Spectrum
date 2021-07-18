package com.spectrum.spectrum.src.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.NumberPicker
import com.google.android.material.button.MaterialButton
import com.spectrum.spectrum.R

class AgePickerDialog(context: Context): AlertDialog(context) {

    private var onAgePicked: (age: Int)->Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCanceledOnTouchOutside(true)
        window?.apply {
            setBackgroundDrawable(ColorDrawable())
            setDimAmount(0.5f)
            setGravity(Gravity.CENTER)
        }
        setContentView(R.layout.dialog_age_picker)
        findViewById<NumberPicker>(R.id.age_picker).apply {
            wrapSelectorWheel = false
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            minValue = 10
            maxValue = 80
            value = 30
        }
        findViewById<MaterialButton>(R.id.confirm_button).setOnClickListener {
            val age = findViewById<NumberPicker>(R.id.age_picker).value
            onAgePicked(age)
            dismiss()
        }
    }

    fun setOnAgePickedListener(listener: (age: Int)->Unit): AgePickerDialog {
        onAgePicked = listener
        return this
    }

}