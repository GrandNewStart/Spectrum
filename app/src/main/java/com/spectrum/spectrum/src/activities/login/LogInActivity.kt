package com.spectrum.spectrum.src.activities.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.interfaces.LogInApi
import com.spectrum.spectrum.src.activities.main.MainActivity
import com.spectrum.spectrum.src.config.Constants.MEDIA_TYPE_JSON
import com.spectrum.spectrum.src.config.Constants.TOKEN
import com.spectrum.spectrum.src.config.Constants.USER_INDEX
import com.spectrum.spectrum.src.config.Constants.request_failed
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.config.Helpers.sharedPreferences
import com.spectrum.spectrum.src.customs.BaseActivity
import com.spectrum.spectrum.src.models.License
import com.spectrum.spectrum.src.models.Education
import com.spectrum.spectrum.src.models.Experience
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class LogInActivity: BaseActivity() {

    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        mNavController = navHostFragment.navController

        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        email?.let { _email -> password?.let { _password ->
            Log.d(TAG, "---> AUTO LOG IN: $email, $password")
            logIn(_email, _password)
        } }
    }

    fun logIn(email: String, password: String) {
        val json = JSONObject().apply {
            put("email", email)
            put("password", password)
        }
        val body = json.toString().toRequestBody(MEDIA_TYPE_JSON.toMediaTypeOrNull())

        try {
            lifecycleScope.launch {
                showProgressDialog(true)
                retrofit.create(LogInApi::class.java).logIn(body).apply {
                    showProgressDialog(false)
                    if (isSuccess) {
                        Log.d(TAG, "---> LOG IN SUCCESS")
                        Log.d(TAG, "    ---> JWT: ${result.jwt}")
                        Log.d(TAG, "    ---> IDX: ${result.userIdx}")
                        sharedPreferences.edit().apply {
                            putString(TOKEN, result.jwt)
                            putInt(USER_INDEX, result.userIdx)
                            apply()
                        }
                        if (result.hasSpec == "Y") {
                            Log.d(TAG, "---> SPEC EXISTS, PROCEEDING TO MAIN")
                            startActivity(Intent(this@LogInActivity, MainActivity::class.java))
                            finish()
                        }
                        else {
                            Log.d(TAG, "---> SPEC DOESN'T EXIST, PROCEEDING TO STEP 3")
                            if (mNavController.currentDestination?.id == R.id.logInStep1) {
                                mNavController.navigate(R.id.step1_to_step3)
                            }
                            if (mNavController.currentDestination?.id == R.id.logInStep2) {
                                mNavController.navigate(R.id.step2_to_step3)
                            }
                        }
                    }
                    else {
                        showToast(message)
                    }
                }
            }
        }
        catch (e: Exception) {
            showProgressDialog(false)
            showToast(request_failed)
            Log.e(TAG, "---> LOG IN FAILURE: $e")
        }
    }

    fun updateSpecs(age: Int?,
                    sex: Int?,
                    groupList: ArrayList<Int>?,
                    educationList: ArrayList<Education>?,
                    experienceList: ArrayList<Experience>?,
                    licenseList: ArrayList<License>?,
                    etcList: String?) {

    }

}