package com.spectrum.spectrum.src.activities.password.fragments.step3

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.customs.BaseFragment
import com.spectrum.spectrum.src.customs.CustomTextInputLayout
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class Step3Fragment: BaseFragment() {

    private val mPassword1LiveData = MutableLiveData<String?>()
    private val mPassword2LiveData = MutableLiveData<String?>()
    private val mIsReady = MutableLiveData<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_password_step_3, container, false)
        view.apply {
            findViewById<MaterialButton>(R.id.back_button).setOnClickListener {
                activity?.finish()
            }
            findViewById<CustomTextInputLayout>(R.id.password_1_input_layout).apply {
                editText?.apply {
                    addTextChangedListener { s ->
                        mPassword1LiveData.value = s.toString()
                        mPassword1LiveData.value.let { text ->
                            if (text == null) {
                                isHintEnabled = true
                                error = Constants.password_error
                                setDone(false)
                                return@addTextChangedListener
                            }
                            isHintEnabled = false
                            if (validatePassword(text)) {
                                error = null
                                setDone(true)
                            }
                            else {
                                error = Constants.password_error
                                setDone(false)
                            }
                        }
                    }
                }
            }
            findViewById<CustomTextInputLayout>(R.id.password_2_input_layout).apply {
                editText?.apply {
                    addTextChangedListener { s ->
                        mPassword2LiveData.value = s.toString()
                        mPassword2LiveData.value.let { text ->
                            if (text == null) {
                                isHintEnabled = true
                                error = Constants.password_match_error
                                helperText = null
                                setDone(false)
                                return@addTextChangedListener
                            }
                            isHintEnabled = false
                            if (mPassword1LiveData.value == text) {
                                error = null
                                helperText = Constants.password_matches
                                setDone(true)
                            } else {
                                error = Constants.password_match_error
                                helperText = null
                                setDone(false)
                            }
                        }
                    }
                    setOnEditorActionListener { _, i, _ ->
                        if (i == EditorInfo.IME_ACTION_DONE) clearFocus()
                        false
                    }
                }
            }
            findViewById<MaterialButton>(R.id.next_button).apply {
                isEnabled = false
                mIsReady.observe(viewLifecycleOwner) { ready -> isEnabled = ready }
                setOnClickListener {
                    mPassword1LiveData.value?.let { updatePassword(it) }
                }
            }
        }
        mPassword1LiveData.observe(viewLifecycleOwner, { pw1 ->
        mPassword2LiveData.observe(viewLifecycleOwner, { pw2 ->
            if (pw1 == null) {
                mIsReady.value = false
                return@observe
            }
            if (pw2 == null) {
                mIsReady.value = false
                return@observe
            }
            mIsReady.value = (pw1 == pw2)
        })
        })
        return view
    }

    private fun validatePassword(input: String): Boolean {
        val regex = "(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&*]).{8,}"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(input)
        return matcher.matches()
    }

    private fun updatePassword(password: String) {
        lifecycleScope.launch {
            // TODO: Change password API
            showFinishDialog()
        }
    }

    private fun showFinishDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(Constants.password_updated)
            .setPositiveButton(Constants.confirm) { d, _ ->  }
            .setOnDismissListener { activity?.finish() }
            .create()
            .show()

    }

}