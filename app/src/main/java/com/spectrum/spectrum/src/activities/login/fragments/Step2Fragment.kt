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
import com.google.android.material.textfield.TextInputEditText
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.signup.SignUpActivity
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.customs.BaseFragment
import com.spectrum.spectrum.src.customs.CustomTextInputLayout

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
            val close = findViewById<Button>(R.id.close)
            val logIn = findViewById<MaterialButton>(R.id.log_in_button)
            val findPassword = findViewById<TextView>(R.id.find_password)
            val signUp = findViewById<TextView>(R.id.sign_up)
            val id = findViewById<TextInputEditText>(R.id.id_edit_text)
            val pw = findViewById<TextInputEditText>(R.id.pw_edit_text)

            close.setOnClickListener {
                showKeyboard(this, false)
                findNavController().popBackStack()
            }

            logIn.setOnClickListener {
                showKeyboard(this, false)
                findNavController().navigate(R.id.step2_to_step3)
            }

            findPassword.setOnClickListener {
                showKeyboard(this, false)
                findNavController().navigate(R.id.step2_to_find_password)
            }

            signUp.setOnClickListener {
                showKeyboard(this, false)
                activity?.apply {
                    startActivity(Intent(this, SignUpActivity::class.java))
                }
            }

            findViewById<CustomTextInputLayout>(R.id.id_text_input_layout).let { layout ->
                id.setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        layout.showClearButton(true)
                        layout.hint = null
                    }
                    else {
                        layout.setDone(layout.isChecked)
                        if (id.text.toString().isEmpty()) {
                            layout.hint = Constants.enter_email
                        }
                    }
                }
            }

            findViewById<CustomTextInputLayout>(R.id.password_text_input_layout).let { layout ->
                pw.setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        layout.showClearButton(true)
                        layout.hint = null
                    }
                    else {
                        layout.setDone(layout.isChecked)
                        if (pw.text.toString().isEmpty()) {
                            layout.hint = Constants.enter_password
                        }
                    }
                }
            }
        }
    }

}