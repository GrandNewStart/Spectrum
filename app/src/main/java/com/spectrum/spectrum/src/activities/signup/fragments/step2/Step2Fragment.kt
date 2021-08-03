package com.spectrum.spectrum.src.activities.signup.fragments.step2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentSignUpStep2Binding
import com.spectrum.spectrum.src.activities.signup.interfaces.SignUpApi
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.customs.BaseFragment
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.regex.Pattern

class Step2Fragment: BaseFragment() {

    private lateinit var mBinding: FragmentSignUpStep2Binding
    private val mEmailLiveData = MutableLiveData<String?>(null)
    private val mPasswordLiveData = MutableLiveData<String?>(null)
    private val mEmailFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        mBinding.apply {
            emailTextInputLayout.let { layout ->
                emailEditText.let { editText ->
                    if (hasFocus) {
                        layout.hint = null
                        layout.showClearButton(true)
                    }
                    else {
                        layout.setDone(layout.isChecked)
                        if (editText.text.toString().trim().isEmpty()) {
                            layout.hint = Constants.enter_email
                        }
                    }
                }
            }
        }
    }
    private val mEmailTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            mEmailLiveData.value = null
            mBinding.apply {
                emailTextInputLayout.let { layout ->
                    s?.let {
                        if (validateEmail(it.toString())) {
                            layout.error = null
                            emailCheck.isEnabled = true
                        }
                        else {
                            layout.error = Constants.enter_appropriate_email
                            emailCheck.isEnabled = false
                        }
                    }
                }
            }
        }
    }
    private val mPw1FocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        mBinding.pw1TextInputLayout.let { layout ->
            mBinding.pw1EditText.let { editText ->
                if (hasFocus) {
                    layout.hint = null
                    layout.showClearButton(true)
                }
                else {
                    layout.setDone(layout.isChecked)
                    if (editText.text.toString().isEmpty()) {
                        layout.hint = Constants.enter_password
                    }
                }
            }
        }
    }
    private val mPw1TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            mPasswordLiveData.value = null
            mBinding.apply {
                pw1TextInputLayout.let { layout ->
                    val p1 = pw1EditText.text.toString()
                    val p2 = pw2EditText.text.toString()
                    if (validatePassword(p1)) {
                        layout.error = null
                        layout.setDone(true)
                        if (p1 == p2) {
                            mPasswordLiveData.value = p1
                            pw2TextInputLayout.setDone(true)
                            pw2TextInputLayout.helperText = Constants.password_matches
                        }
                        else {
                            pw2TextInputLayout.setDone(false)
                            pw2TextInputLayout.helperText = null
                        }
                    }
                    else {
                        layout.error = Constants.password_error
                        layout.helperText = null
                        layout.setDone(false)
                        layout.showClearButton(true)
                        pw2TextInputLayout.setDone(false)
                        pw2TextInputLayout.helperText = null
                    }
                }
            }
        }
    }
    private val mPw2FocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        mBinding.pw2TextInputLayout.let { layout ->
            mBinding.pw2EditText.let { editText ->
                if (hasFocus) {
                    layout.hint = null
                    layout.showClearButton(true)
                }
                else {
                    layout.setDone(layout.isChecked)
                    if (editText.text.toString().isEmpty()) {
                        layout.hint = Constants.enter_password
                    }
                }
            }
        }
    }
    private val mPw2TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            mBinding.apply {
                mPasswordLiveData.value = null
                pw2TextInputLayout.let { layout ->
                    layout.helperText = null
                    val p1 = pw1EditText.text.toString()
                    val p2 = pw2EditText.text.toString()
                    if (p1 == p2) {
                        if (validatePassword(p1)) {
                            mPasswordLiveData.value = p1
                            layout.helperText = Constants.password_matches
                            layout.setDone(true)
                            return
                        }
                        layout.setDone(false)
                        layout.showClearButton(true)
                    }
                    else {
                        layout.error = Constants.password_match_error
                        layout.helperText = null
                        layout.setDone(false)
                        layout.showClearButton(true)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_sign_up_step2, container, false)
        mBinding.apply {
            lifecycleOwner = this@Step2Fragment
            fragment = this@Step2Fragment
            emailFocusChangeListener = mEmailFocusChangeListener
            emailTextWatcher = mEmailTextWatcher
            pw1FocusChangeListener = mPw1FocusChangeListener
            pw1TextWatcher = mPw1TextWatcher
            pw2FocusChangeListener = mPw2FocusChangeListener
            pw2TextWatcher = mPw2TextWatcher
            next.isEnabled = false
            emailCheck.isEnabled = false
        }
        mEmailLiveData.observe(viewLifecycleOwner, { email ->
            mBinding.next.isEnabled = false
            email?.let {
                mBinding.next.isEnabled = (mPasswordLiveData.value != null)
            }
        })
        mPasswordLiveData.observe(viewLifecycleOwner, { password ->
            mBinding.next.isEnabled = false
            password?.let {
                mBinding.next.isEnabled = (mEmailLiveData.value != null)
            }
        })
        return mBinding.root
    }

    fun backButtonAction() {
        showKeyboard(mBinding.root, false)
        findNavController().popBackStack()
    }

    fun emailCheckButtonAction() {
        mBinding.apply {
            showKeyboard(root, false)
            checkEmail(emailEditText.text.toString().trim())
        }
    }

    fun nextButtonAction() {
        val bundle = bundleOf("email" to mEmailLiveData.value, "password" to mPasswordLiveData.value)
        findNavController().navigate(R.id.step2_to_step3, bundle)
    }

    private fun validateEmail(input: String): Boolean {
        val pattern = android.util.Patterns.EMAIL_ADDRESS
        val matcher = pattern.matcher(input)
        return matcher.matches()
    }

    private fun validatePassword(input: String): Boolean {
        val regex = "(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&*]).{8,}"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(input)
        return matcher.matches()
    }

    private fun checkEmail(email: String?) {
        if (email == null) return
        lifecycleScope.launch {
            try {
                retrofit.create(SignUpApi::class.java).checkEmail(email).apply {
                    if (isSuccess) {
                        Log.d(TAG, "---> CHECK EMAIL SUCCESS")
                        mEmailLiveData.value = email
                        showToast(Constants.valid_email)
                        return@launch
                    }
                    showToast(Constants.invalid_email)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "---> CHECK EMAIL FAILURE: $e")
                showToast(Constants.request_failed)
            }
        }
    }


}