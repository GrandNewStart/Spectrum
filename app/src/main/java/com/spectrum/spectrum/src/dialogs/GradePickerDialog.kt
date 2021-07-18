package com.spectrum.spectrum.src.dialogs

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.NumberPicker
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.spectrum.spectrum.R
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class GradePickerDialog(context: Context, private val maxGrade: Double): AlertDialog(context) {

    private var onGradePicked: (grade: Double)->Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_grade_picker)
        findViewById<NumberPicker>(R.id.picker_1).apply {
            wrapSelectorWheel = false
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            maxValue = 4
            minValue = 0
            value = 0
        }
        findViewById<NumberPicker>(R.id.picker_2).apply {
            wrapSelectorWheel = false
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            maxValue = 9
            minValue = 0
            value = 0
        }
        findViewById<NumberPicker>(R.id.picker_3).apply {
            wrapSelectorWheel = false
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            maxValue = 9
            minValue = 0
            value = 0
        }
        findViewById<MaterialButton>(R.id.confirm_button).setOnClickListener {
            val g1 = findViewById<NumberPicker>(R.id.picker_1).value
            val g2 = findViewById<NumberPicker>(R.id.picker_2).value
            val g3 = findViewById<NumberPicker>(R.id.picker_3).value
            val grade = String.format("%.2f", (g1 + g2 * 0.1 + g3 * 0.01)).toDouble()
            if (grade > maxGrade) {
                Toast.makeText(context, "$maxGrade 점 보다 작은 수를 선택해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            onGradePicked(grade)
            dismiss()
        }
    }

    fun setOnGradePickedListener(listener: (grade: Double)->Unit): GradePickerDialog {
        onGradePicked = listener
        return this
    }

}