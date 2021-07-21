package com.spectrum.spectrum.src.activities.signup.fragments.step1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.customs.BaseFragment

class Step1Fragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_sign_up_step1, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        view.apply {
            findViewById<Button>(R.id.close_button).setOnClickListener {
                activity?.finish()
            }
            findViewById<MaterialButton>(R.id.agree_button).setOnClickListener {
                findNavController().navigate(R.id.step1_to_step2)
            }
            findViewById<WebView>(R.id.web_view).apply {
                loadUrl(privacy_page)
            }
        }
    }

    companion object {
        const val privacy_page = "http://spectrum-app.shop:9000/docs.html"
    }

}