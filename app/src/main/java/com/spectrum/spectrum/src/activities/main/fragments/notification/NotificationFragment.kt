package com.spectrum.spectrum.src.activities.main.fragments.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.customs.BaseFragment

class NotificationFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = View(context)
        view.setBackgroundColor(resources.getColor(R.color.spectrumGreen, null))
        return view
    }

}