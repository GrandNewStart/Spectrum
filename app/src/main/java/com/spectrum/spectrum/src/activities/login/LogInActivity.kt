package com.spectrum.spectrum.src.activities.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.interfaces.LogInApi
import com.spectrum.spectrum.src.activities.login.models.CertItem
import com.spectrum.spectrum.src.activities.login.models.EduItem
import com.spectrum.spectrum.src.activities.login.models.ExpItem
import com.spectrum.spectrum.src.activities.login.models.JobGroup
import com.spectrum.spectrum.src.activities.main.MainActivity
import com.spectrum.spectrum.src.config.Constants.MEDIA_TYPE_JSON
import com.spectrum.spectrum.src.config.Constants.TOKEN
import com.spectrum.spectrum.src.config.Constants.USER_INDEX
import com.spectrum.spectrum.src.config.Constants.request_failed
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.config.Helpers.sharedPreferences
import com.spectrum.spectrum.src.customs.BaseActivity
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class LogInActivity: BaseActivity() {

    private lateinit var mNavController: NavController
    var mAge: Int? = null
    var mSex: String? = null
    var mJobGroup1: JobGroup? = null
    var mJobGroup2: JobGroup? = null
    var mJobGroup3: JobGroup? = null
    var mEduItems = ArrayList<EduItem>()
    var mExpItems = ArrayList<ExpItem>()
    var mCertItems = ArrayList<CertItem>()
    var mComments: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        mNavController = navHostFragment.navController

        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        email?.let { _email ->
            password?.let { _password ->
                autoLogIn(_email, _password)
            }
        }
    }

    private fun autoLogIn(email: String, password: String) {
        val json = JSONObject().apply {
            put("email", email)
            put("password", password)
        }
        val body = json.toString().toRequestBody(MEDIA_TYPE_JSON.toMediaTypeOrNull())

        try {
            lifecycleScope.launch {
                showProgressDialog(true)
                retrofit.create(LogInApi::class.java).logIn(body).apply {
                    if (isSuccess) {
                        showProgressDialog(false)
                        sharedPreferences.edit().apply {
                            putString(TOKEN, result.jwt)
                            putInt(USER_INDEX, result.userIdx)
                            apply()
                        }

                        //TODO: Step3Fragment
                        mNavController.navigate(R.id.step1_to_step3)
                        // or
                        startActivity(Intent(this@LogInActivity, MainActivity::class.java))
                        finish()
                        return@launch
                    }
                }
                showProgressDialog(false)
                showToast(request_failed)
            }
        }
        catch (e: Exception) {
            showProgressDialog(false)
            showToast(request_failed)
            Log.e(TAG, "---> LOG IN FAILURE: $e")
        }
    }

}