package com.spectrum.spectrum.src.activities.login.fragments

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

class Step3Fragment: BaseFragment() {

    private val mEmailLiveData = MutableLiveData<String?>(null)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_log_in_step3, container, false)
        initViews(view)
        initLiveData()
        return view
    }

    private fun initViews(view: View) {
        view.apply {
            val back = findViewById<Button>(R.id.back)
            val email = findViewById<TextInputEditText>(R.id.email_edit_text)
            val next = findViewById<MaterialButton>(R.id.next)

            back.setOnClickListener { findNavController().popBackStack() }

            findViewById<CustomTextInputLayout>(R.id.email_text_input_layout).let { layout ->
                email.setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        layout.hint = null
                    }
                    else {
                        if (email.text.toString().isEmpty()) {
                            layout.hint = Constants.enter_email
                        }
                    }
                }
                email.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
                    override fun afterTextChanged(s: Editable?) {
                        val input = s.toString().trim()
                        if (validateEmail(input)) {
                            mEmailLiveData.value = input
                        }
                        else {
                            mEmailLiveData.value = null
                        }
                    }
                })
            }

            next.setOnClickListener { findNavController().navigate(R.id.step1_to_step2) }
        }
    }

    private fun initLiveData() {
        mEmailLiveData.observe(viewLifecycleOwner, { email ->
            view?.findViewById<MaterialButton>(R.id.next)?.isEnabled = (email != null)
        })
    }

    private fun validateEmail(input: String): Boolean {
        // TODO: FIX REGEX
        val regex = "[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(input)
        return matcher.matches()
    }

}