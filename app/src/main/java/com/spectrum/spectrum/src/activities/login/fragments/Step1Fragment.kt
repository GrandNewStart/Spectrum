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
            val logIn = findViewById<MaterialButton>(R.id.log_in)
            val signUp = findViewById<TextView>(R.id.sign_up)

            logIn.setOnClickListener {
                showKeyboard(this, false)
                findNavController().navigate(R.id.step1_to_step2)
            }

            signUp.setOnClickListener {
                showKeyboard(this, false)
                activity?.apply {
                    startActivity(Intent(this, SignUpActivity::class.java))
                }
            }
        }
    }

}