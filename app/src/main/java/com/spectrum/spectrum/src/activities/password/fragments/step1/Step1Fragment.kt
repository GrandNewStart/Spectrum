package com.spectrum.spectrum.src.activities.password.fragments.step1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.customs.BaseFragment
import com.spectrum.spectrum.src.customs.CustomTextInputLayout
import kotlinx.coroutines.launch

class Step1Fragment: BaseFragment() {


    private val mEmailLiveData = MutableLiveData<String?>(null)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_password_step_1, container, false)
        view.apply {
            findViewById<Button>(R.id.back).setOnClickListener { activity?.finish() }
            findViewById<CustomTextInputLayout>(R.id.email_text_input_layout).apply {
                editText?.apply {
                    setOnFocusChangeListener { _, hasFocus ->
                        if (hasFocus) {
                            hint = null
                        }
                        else {
                            if (text.toString().isEmpty()) {
                                hint = Constants.enter_email
                            }
                        }
                    }
                    addTextChangedListener(object : TextWatcher {
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
                    setOnEditorActionListener { _, i, _ ->
                        if (i == EditorInfo.IME_ACTION_DONE) clearFocus()
                        false
                    }
                }
            }

            findViewById<MaterialButton>(R.id.next).setOnClickListener {
                mEmailLiveData.value?.let { requestCode(it) }
            }
        }
        mEmailLiveData.observe(viewLifecycleOwner, { email ->
            view.findViewById<MaterialButton>(R.id.next).isEnabled = (email != null)
        })
        return view
    }

    private fun validateEmail(input: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(input).matches()
    }

    private fun requestCode(email: String) {
        lifecycleScope.launch {
            // TODO: REQUEST CODE API
            findNavController().navigate(R.id.step1_to_step2)
        }
    }

}