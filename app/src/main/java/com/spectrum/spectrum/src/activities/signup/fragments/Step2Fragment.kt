package com.spectrum.spectrum.src.activities.signup.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.customs.BaseFragment

class Step2Fragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_sign_up_step2, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        view.apply {
            findViewById<Button>(R.id.back).setOnClickListener {
                findNavController().popBackStack()
            }
            findViewById<Button>(R.id.next).setOnClickListener {
                findNavController().navigate(R.id.step2_to_step3)
            }
        }
    }

}