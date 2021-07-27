package com.spectrum.spectrum.src.activities.signup.fragments.step3

import android.app.AlertDialog
import android.content.Intent
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
import com.spectrum.spectrum.databinding.FragmentSignUpStep3Binding
import com.spectrum.spectrum.src.activities.login.LogInActivity
import com.spectrum.spectrum.src.activities.signup.interfaces.SignUpApi
import com.spectrum.spectrum.src.activities.splash.SplashActivity
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Constants.MEDIA_TYPE_JSON
import com.spectrum.spectrum.src.config.Constants.TOKEN
import com.spectrum.spectrum.src.config.Constants.USER_EMAIL
import com.spectrum.spectrum.src.config.Constants.USER_INDEX
import com.spectrum.spectrum.src.config.Constants.request_failed
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.config.Helpers.sharedPreferences
import com.spectrum.spectrum.src.customs.BaseFragment
import kotlinx.android.synthetic.main.fragment_log_in_step2.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.lang.Exception
import java.util.regex.Pattern

class Step3Fragment: BaseFragment() {

    private lateinit var mBinding: FragmentSignUpStep3Binding
    private val mNicknameLiveData = MutableLiveData<String?>(null)
    private var mEmail: String? = null
    private var mPassword: String? = null
    private val mNicknameFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        mBinding.nicknameTextInputLayout.let { layout ->
            if (hasFocus) {
                layout.hint = null
                layout.showClearButton(true)
            }
            else {
                layout.setDone(layout.isChecked)
                if (mBinding.nicknameEditText.text.toString().isEmpty()) {
                    layout.hint = Constants.enter_nickname
                }
            }
        }
    }
    private val mNicknameTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            mNicknameLiveData.value = null
            mBinding.nicknameTextInputLayout.let { layout ->
                if (validateNickname(s.toString())) {
                    layout.error = null
                }
                else {
                    layout.error = Constants.nickname_error
                    layout.helperText = null
                    layout.setDone(false)
                    layout.showClearButton(true)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mEmail = it.getString("email")
            mPassword = it.getString("password")
        }
        autoLogIn()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_sign_up_step3, container, false)
        mBinding.apply {
            lifecycleOwner = this@Step3Fragment
            fragment = this@Step3Fragment
            nicknameFocusChangeListener = mNicknameFocusChangeListener
            nicknameTextWatcher = mNicknameTextWatcher
            doneButton.isEnabled = false
        }
        mNicknameLiveData.observe(viewLifecycleOwner, { nickname ->
            mBinding.doneButton.isEnabled = (nickname != null)
        })
        return mBinding.root
    }

    fun backButtonAction() {
        showKeyboard(mBinding.root, false)
        findNavController().popBackStack()
    }

    fun nickNameCheckButtonAction() {
        mBinding.apply {
            nicknameEditText.apply {
                showKeyboard(this, false)
                checkNickname(text.toString())
            }
        }
    }

    fun doneButtonAction() {
        welcomeToSpectrum()
    }

    private fun validateNickname(input: String): Boolean {
        val regex = "[a-zA-Zㄱ-ㅎ|ㅏ-ㅣ|가-힣].{1,10}"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(input)
        return matcher.matches()
    }

    private fun showCompleteDialog() {
        AlertDialog.Builder(context)
            .setMessage(Constants.sign_up_complete)
            .setPositiveButton(Constants.confirm) { dialog, _ ->
                dialog.dismiss()
                autoLogIn()
            }
            .setOnDismissListener {
                autoLogIn()
            }
            .create()
            .show()
    }

    private fun autoLogIn() {
        val intent = Intent(activity, LogInActivity::class.java).apply {
            putExtra("email", mEmail)
            putExtra("password", mPassword)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        activity?.startActivity(intent)
        activity?.finish()
    }

    private fun checkNickname(nickname: String?) {
        if (nickname == null) return
        lifecycleScope.launch {
            retrofit.create(SignUpApi::class.java).checkNickname(nickname).apply {
                if (isSuccess) {
                    showToast(Constants.available_nickname)
                    mNicknameLiveData.value = nickname
                    mBinding.nicknameTextInputLayout.helperText = Constants.nickname_possible
                    mBinding.nicknameTextInputLayout.setDone(true)
                    return@launch
                }
                showToast(Constants.unavailable_nickname)
            }
        }
    }

    private fun welcomeToSpectrum() {
        if (mNicknameLiveData.value == null) return
        val json = JSONObject().apply {
            put("email", mEmail)
            put("password", mPassword)
            put("username", mNicknameLiveData.value)
        }
        val body = json.toString().toRequestBody(MEDIA_TYPE_JSON.toMediaTypeOrNull())
        try {
            lifecycleScope.launch {
                retrofit.create(SignUpApi::class.java).signUp(body).apply {
                    if (isSuccess) {
                        Log.d(TAG, "---> SIGN UP SUCCESS")
                        Log.d(TAG, "    ---> JWT: ${result.jwt}")
                        Log.d(TAG, "    ---> IDX: ${result.userIdx}")
                        Log.d(TAG, "    ---> EMAIL: $mEmail")
                        sharedPreferences.edit().apply {
                            putString(TOKEN, result.jwt)
                            putString(USER_EMAIL, mEmail)
                            putInt(USER_INDEX, result.userIdx)
                            apply()
                        }
                        showCompleteDialog()
                        return@launch
                    }
                }
            }
        }
        catch (e: Exception) {
            Log.e(TAG, "---> SIGN UP FAILURE: $e")
            showToast(request_failed)
        }
    }

}