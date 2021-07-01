package com.spectrum.spectrum.src.customs

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    private var progressDialog: CustomProgressDialog? = null

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showProgressDialog(isLoading: Boolean) {
        if (isLoading) {
            if (progressDialog == null) progressDialog = CustomProgressDialog(this)
            progressDialog?.apply {
                setCancelable(false)
                show()
            }
            return
        }
        progressDialog?.apply {
            if (isShowing) dismiss()
        }
    }

    fun showKeyboard(view: View, show: Boolean) {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (show) imm.showSoftInput(view, 0)
        else imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = javaClass.simpleName
        Log.d(TAG, "=== $TAG CREATED ===")
    }

    override fun onStop() {
        super.onStop()
        showProgressDialog(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "=== $TAG DESTROYED ===")
    }

    companion object {
        var TAG = ""
    }

}