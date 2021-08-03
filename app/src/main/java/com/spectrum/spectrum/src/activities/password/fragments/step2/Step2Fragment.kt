package com.spectrum.spectrum.src.activities.password.fragments.step2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.customs.BaseFragment
import com.spectrum.spectrum.src.customs.CustomTextInputLayout
import kotlinx.coroutines.launch

class Step2Fragment: BaseFragment() {

    private val mCodeLiveData = MutableLiveData<String?>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_password_step_2, container, false)
        view.apply {
            findViewById<MaterialButton>(R.id.back_button).setOnClickListener {
                findNavController().popBackStack()
            }
            findViewById<CustomTextInputLayout>(R.id.auth_code_input_layout).apply {
                editText?.apply {
                    addTextChangedListener(object : TextWatcher{
                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                        override fun afterTextChanged(s: Editable?) {
                            if (s == null) isHintEnabled = true
                            s?.toString()?.let { code ->
                                isHintEnabled = false
                                mCodeLiveData.value = if (code.length == 6) code else null
                            }
                        }
                    })
                    setOnEditorActionListener { textView, i, keyEvent ->
                        if (i == EditorInfo.IME_ACTION_DONE) { clearFocus() }
                        false
                    }
                }
            }
            findViewById<MaterialButton>(R.id.next_button).apply {
                isEnabled = false
                mCodeLiveData.observe(viewLifecycleOwner) { code ->
                    isEnabled = (code != null)
                }
                setOnClickListener {
                    mCodeLiveData.value?.let { validateCode(it) }
                }
            }
        }
        return view
    }

    private fun validateCode(code: String) {
        lifecycleScope.launch {
            // TODO: Validate code API
            findNavController().navigate(R.id.step2_to_step3)
        }
    }

}