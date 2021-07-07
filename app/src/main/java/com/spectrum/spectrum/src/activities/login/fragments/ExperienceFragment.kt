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
import com.spectrum.spectrum.src.activities.login.models.ExpItem
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.customs.BaseFragment

class ExperienceFragment: BaseFragment() {

    private var mCompany: String? = null
    private var mDuty: String? = null
    private var mPosition: String? = null
    private var mPeriod: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in_experience, container, false)
        view.apply {
            findViewById<MaterialButton>(R.id.back).setOnClickListener {
                findNavController().popBackStack()
            }
            findViewById<MaterialTextView>(R.id.save).setOnClickListener {
                (activity as LogInActivity).apply {
                    if (mCompany == null || mDuty == null || mPosition == null || mPeriod == null) {
                        showToast(Constants.enter_every_fields)
                        return@setOnClickListener
                    }
                    val item = ExpItem(mCompany, mDuty, mPosition, mPeriod)
                    mExpItems.add(item)
                    findNavController().popBackStack()
                }
            }
            findViewById<EditText>(R.id.company).apply {
                addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        if (s.toString().trim().isEmpty()) {
                            mCompany = null
                            return
                        }
                        mCompany = s.toString()
                    }
                })
            }
            findViewById<EditText>(R.id.duty).apply {
                addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        if (s.toString().trim().isEmpty()) {
                            mDuty = null
                            return
                        }
                        mDuty = s.toString()
                    }
                })
            }
            findViewById<EditText>(R.id.position).apply {
                addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        if (s.toString().trim().isEmpty()) {
                            mPosition = null
                            return
                        }
                        mPosition = s.toString()
                    }
                })
            }
            findViewById<EditText>(R.id.period).apply {
                addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        if (s.toString().trim().isEmpty()) {
                            mPeriod = null
                            return
                        }
                        mPeriod = s.toString()
                    }
                })
            }
        }
        return view
    }

}