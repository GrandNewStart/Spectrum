package com.spectrum.spectrum.src.activities.login.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.customs.BaseFragment

class Step3Fragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_log_in_step3, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        view.apply {
            findViewById<Button>(R.id.back).setOnClickListener { findNavController().popBackStack() }
            findViewById<MaterialButton>(R.id.next).setOnClickListener { findNavController().navigate(R.id.step1_to_step2) }
        }
    }

}