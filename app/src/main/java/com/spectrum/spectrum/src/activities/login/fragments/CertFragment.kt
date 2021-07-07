package com.spectrum.spectrum.src.activities.login.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.LogInActivity
import com.spectrum.spectrum.src.activities.login.models.CertItem
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.customs.BaseFragment

class CertFragment: BaseFragment() {

    private var mCertification: String? = null
    private var mScore: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in_cerification, container, false)
        view.apply {
            findViewById<MaterialButton>(R.id.back).setOnClickListener {
                findNavController().popBackStack()
            }
            findViewById<MaterialTextView>(R.id.save).setOnClickListener {
                (activity as LogInActivity).apply {
                    if (mCertification == null || mScore == null) {
                        showToast(Constants.enter_every_fields)
                        return@setOnClickListener
                    }
                    val item = CertItem(mCertification, mScore)
                    mCertItems.add(item)
                    findNavController().popBackStack()
                }
            }
            findViewById<EditText>(R.id.certs_and_exams).addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s.toString().trim().isEmpty()) {
                        mCertification = null
                        return
                    }
                    mCertification = s.toString().trim()
                }
            })
            findViewById<EditText>(R.id.scores_and_grades).addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s.toString().trim().isEmpty()) {
                        mScore = null
                        return
                    }
                    mScore = s.toString().trim()
                }
            })
        }
        return view
    }

}