package com.spectrum.spectrum.src.customs

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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