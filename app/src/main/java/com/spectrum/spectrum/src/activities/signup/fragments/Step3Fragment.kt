package com.spectrum.spectrum.src.activities.signup.fragments

import android.app.AlertDialog
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

    private val mNicknameLiveData = MutableLiveData<String?>(null)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_sign_up_step3, container, false)
        initViews(view)
        initLiveData()
        return view
    }

    private fun initViews(view: View) {
        view.apply {
            val back            = findViewById<Button>(R.id.back)
            val nickname        = findViewById<TextInputEditText>(R.id.nickname_edit_text)
            val nicknameCheck   = findViewById<MaterialButton>(R.id.nickname_check)
            val done            = findViewById<MaterialButton>(R.id.done)

            back.setOnClickListener {
                showKeyboard(this, false)
                findNavController().popBackStack()
            }

            findViewById<CustomTextInputLayout>(R.id.nickname_text_input_layout).let{ layout ->
                nickname.setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus) {
                        layout.hint = null
                        layout.showClearButton(true)
                    }
                    else {
                        layout.setDone(layout.isChecked)
                        if (nickname.text.toString().isEmpty()) {
                            layout.hint = Constants.enter_nickname
                        }
                    }
                }
                nickname.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        if (validateNickname(s.toString())) {
                            layout.error = null
                            layout.helperText = Constants.nickname_possible
                            layout.setDone(true)
                        }
                        else {
                            layout.error = Constants.nickname_error
                            layout.helperText = null
                            layout.setDone(false)
                            layout.showClearButton(true)
                        }
                    }
                })
            }

            nicknameCheck.setOnClickListener {
                showKeyboard(nickname, false)
                checkNickname(nickname.text.toString())
            }

            done.isEnabled = false
            done.setOnClickListener { showCompleteDialog() }
        }
    }

    private fun initLiveData() {
        mNicknameLiveData.observe(viewLifecycleOwner, { nickname ->
            view?.findViewById<MaterialButton>(R.id.done)?.isEnabled = (nickname != null)
        })
    }

    private fun validateNickname(input: String): Boolean {
        val regex = "[a-zA-Zㄱ-ㅎ|ㅏ-ㅣ|가-힣].{1,10}"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(input)
        return matcher.matches()
    }

    private fun checkNickname(nickname: String) {
        // TODO: 닉네임 중복 확인 API 호출
        mNicknameLiveData.value = nickname
    }

    private fun showCompleteDialog() {
        AlertDialog.Builder(context)
            .setMessage(Constants.sign_up_complete)
            .setPositiveButton(Constants.confirm) { dialog, _ ->
                dialog.dismiss()
                saveUserInfo()
                activity?.finish()
            }
            .setOnDismissListener {
                saveUserInfo()
                activity?.finish()
            }
            .create()
            .show()
    }

    private fun saveUserInfo() {
        // TODO: 로그인 API 호출
    }

}