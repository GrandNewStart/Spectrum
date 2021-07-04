package com.spectrum.spectrum.src.activities.signup.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.customs.BaseFragment
import com.spectrum.spectrum.src.customs.CustomTextInputLayout
import java.util.regex.Pattern

class Step2Fragment: BaseFragment() {

    private val mEmailLiveData      = MutableLiveData<String?>(null)
    private val mPasswordLiveData   = MutableLiveData<String?>(null)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_sign_up_step2, container, false)
        initViews(view)
        initLiveData()
        return view
    }

    private fun initViews(view: View) {
        view.apply {
            val back        = findViewById<Button>(R.id.back)
            val next        = findViewById<Button>(R.id.next)
            val emailCheck  = findViewById<MaterialButton>(R.id.email_check)
            val email       = findViewById<TextInputEditText>(R.id.email_edit_text)
            val pw1         = findViewById<TextInputEditText>(R.id.pw1_edit_text)
            val pw2         = findViewById<TextInputEditText>(R.id.pw2_edit_text)

            back.setOnClickListener {
                showKeyboard(this, false)
                findNavController().popBackStack()
            }

            next.isEnabled = false
            next.setOnClickListener {
                findNavController().navigate(R.id.step2_to_find_password)
            }

            emailCheck.setOnClickListener {
                showKeyboard(this, false)
                checkEmail(email.text.toString())
            }

            findViewById<CustomTextInputLayout>(R.id.id_text_input_layout).let { layout ->
                email.setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        layout.hint = null
                        layout.showClearButton(true)
                    }
                    else {
                        layout.setDone(layout.isChecked)
                        if (email.text.toString().isEmpty()) {
                            layout.hint = Constants.enter_email
                        }
                    }
                }
                email.addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
                    override fun afterTextChanged(s: Editable?) { mEmailLiveData.value = null }
                })
            }

            findViewById<CustomTextInputLayout>(R.id.pw1_text_input_layout).let { layout ->
                pw1.setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        layout.hint = null
                        layout.showClearButton(true)
                    }
                    else {
                        layout.setDone(layout.isChecked)
                        if (pw1.text.toString().isEmpty()) {
                            layout.hint = Constants.enter_password
                        }
                    }
                }
                pw1.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        if (validatePassword(s.toString())) {
                            layout.error = null
                            layout.setDone(true)
                        }
                        else {
                            layout.error = Constants.password_error
                            layout.helperText = null
                            layout.setDone(false)
                            layout.showClearButton(true)
                        }
                    }
                })
            }

            findViewById<CustomTextInputLayout>(R.id.pw2_text_input_layout).let { layout ->
                layout.errorIconDrawable = null
                pw2.setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        layout.hint = null
                        layout.showClearButton(true)
                    }
                    else {
                        layout.setDone(layout.isChecked)
                        if (pw2.text.toString().isEmpty()) {
                            layout.hint = Constants.enter_password
                        }
                    }
                }
                pw2.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        val p1 = pw1.text.toString().trim()
                        val p2 = pw2.text.toString().trim()
                        if (p1 == p2) {
                            if (validatePassword(p1)) {
                                mPasswordLiveData.value = p1
                                layout.helperText = Constants.password_matches
                                layout.setDone(true)
                                return
                            }
                            mPasswordLiveData.value = null
                        }
                        else {
                            layout.error = Constants.password_match_error
                            layout.helperText = null
                            layout.setDone(false)
                            layout.showClearButton(true)
                        }
                    }
                })
            }
        }
    }

    private fun initLiveData() {
        mEmailLiveData.observe(viewLifecycleOwner, { email ->
            mPasswordLiveData.value?.let {
                view?.findViewById<MaterialButton>(R.id.next)?.isEnabled = (email != null)
            }
        })
        mPasswordLiveData.observe(viewLifecycleOwner, { password ->
            mEmailLiveData.value?.let {
                view?.findViewById<MaterialButton>(R.id.next)?.isEnabled = (password != null)
            }
        })
    }

    private fun validatePassword(input: String): Boolean {
        val regex = "(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&*]).{8,}"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(input)
        return matcher.matches()
    }

    private fun checkEmail(email: String) {
        //TODO: 이메일 중복 확인 API 호출
        mEmailLiveData.value = email
    }

}