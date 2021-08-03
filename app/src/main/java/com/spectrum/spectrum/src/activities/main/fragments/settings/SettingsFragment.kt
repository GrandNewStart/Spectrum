package com.spectrum.spectrum.src.activities.main.fragments.settings

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.password.PasswordActivity
import com.spectrum.spectrum.src.activities.splash.SplashActivity
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.sharedPreferences
import com.spectrum.spectrum.src.customs.BaseFragment

class SettingsFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        view.apply {
            findViewById<MaterialButton>(R.id.back_button).setOnClickListener { backButtonAction() }
            findViewById<TextView>(R.id.find_password_text).setOnClickListener { proceedToFindPassword() }
            findViewById<TextView>(R.id.log_out_text).setOnClickListener { showLogOutDialog() }
            findViewById<TextView>(R.id.sign_out_text).setOnClickListener { proceedToSignOut() }
        }
        return view
    }

    private fun backButtonAction() {
        findNavController().popBackStack()
    }

    private fun proceedToFindPassword() {
        activity?.apply {
            val intent = Intent(this, PasswordActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }

    private fun showLogOutDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(Constants.ask_log_out)
            .setPositiveButton(Constants.yes) { _,_->
                sharedPreferences.edit().apply {
                    clear()
                    apply()
                }
                activity?.apply {
                    startActivity(Intent(this, SplashActivity::class.java))
                    finish()
                }
            }
            .setNegativeButton(Constants.no) { dialog,_->
                dialog.dismiss() }
            .create()
            .show()

    }

    private fun proceedToSignOut() {

    }

}