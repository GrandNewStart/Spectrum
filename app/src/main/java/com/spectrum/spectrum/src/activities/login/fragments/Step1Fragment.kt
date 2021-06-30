package com.spectrum.spectrum.src.activities.login.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.signup.SignUpActivity
import com.spectrum.spectrum.src.customs.BaseFragment

class Step1Fragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_log_in_step1, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        view.apply {
            findViewById<MaterialButton>(R.id.log_in).setOnClickListener {
                findNavController().navigate(R.id.step1_to_step2)
            }
            findViewById<MaterialButton>(R.id.sign_up).setOnClickListener {
                startActivity(Intent(activity, SignUpActivity::class.java))
            }
        }
    }

}