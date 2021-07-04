package com.spectrum.spectrum.src.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerDialog: DialogFragment(), DatePickerDialog.OnDateSetListener {

    var onDateSelected: (date: String)->Unit = {}
    var onNothingSelected: ()->Unit = {}
    var mDateSelected = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (!mDateSelected) {
            onNothingSelected()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val newMonth = if (month < 10) "0${month+1}" else "$month"
        val newDay = if (dayOfMonth < 10) "0${dayOfMonth}" else "$dayOfMonth"
        val result = "$year$newMonth$newDay"
        mDateSelected = true
        onDateSelected(result)
    }

}