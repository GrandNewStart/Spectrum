package com.spectrum.spectrum.src.activities.login.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.NumberPicker
import com.google.android.material.button.MaterialButton
import com.spectrum.spectrum.R

class AgePickerDialog(context: Context) {

    private val dialog: AlertDialog
    private var onAgePicked: (age: Int)->Unit = {}

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_age_picker, null)
        view.apply {
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
        dialog = AlertDialog.Builder(context)
            .setView(view)
            .create()
    }

    fun setOnAgePickedListener(listener: (age: Int)->Unit) {
        onAgePicked = listener
    }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }

}