package com.spectrum.spectrum.src.activities.signup.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.customs.BaseFragment

class Step3Fragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_sign_up_step3, container, false)
        return view
    }

}