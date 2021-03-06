package com.spectrum.spectrum.src.customs

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showProgressDialog(show: Boolean) {
        (activity as BaseActivity).showProgressDialog(show)
    }

    fun showKeyboard(view: View, show: Boolean) {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (show) imm.showSoftInput(view, 0)
        else imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "=== $TAG ATTACHED ===")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = javaClass.simpleName
        Log.d(TAG, "=== $TAG CREATED ===")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "=== $TAG VIEW CREATED ===")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        Log.d(TAG, "=== $TAG VIEW DESTROYED ===")
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "=== $TAG DESTROYED ===")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "=== $TAG DETACHED ===")
    }

    companion object {
        var TAG = ""
    }

}