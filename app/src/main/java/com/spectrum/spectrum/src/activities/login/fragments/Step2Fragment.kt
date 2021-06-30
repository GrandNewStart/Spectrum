package com.spectrum.spectrum.src.activities.login.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.signup.SignUpActivity
import com.spectrum.spectrum.src.customs.BaseFragment

class Step2Fragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_log_in_step2, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        view.apply {
            findViewById<Button>(R.id.close).setOnClickListener {
                findNavController().popBackStack()
            }
            findViewById<MaterialButton>(R.id.log_in_button).setOnClickListener {
                showToast("LOG IN")
            }
            findViewById<TextView>(R.id.find_password).setOnClickListener {
                findNavController().navigate(R.id.step2_to_step3)
            }
            findViewById<TextView>(R.id.sign_up).setOnClickListener {
                startActivity(Intent(activity, SignUpActivity::class.java))
                findNavController().popBackStack()
            }
        }
    }

}