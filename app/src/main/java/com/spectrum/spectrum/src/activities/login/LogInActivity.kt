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
import com.spectrum.spectrum.src.config.Constants
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
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class LogInActivity: BaseActivity() {

    private lateinit var mNavController: NavController
    private val mService = retrofit.create(LogInApi::class.java)

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
                mService.logIn(body).apply {
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
                    otherSpec: String) {
        val json = JSONObject().apply {
            put("age", age)
            put("sex", sex)
            val jobs = JSONArray().apply {
                groupList?.forEach {
                    val item = JSONObject()
                    item.put("jobGroupId", it)
                    put(item)
                }
            }
            put("groupList", jobs)
            val educations = JSONArray().apply {
                educationList?.forEach { edu ->
                    val item = JSONObject()
                    edu.graduate?.id?.let{ item.put("graduate", it) }
                    edu.degree?.id?.let{ item.put("degree", it) }
                    edu.schoolName?.let { item.put("schoolName", it) }
                    edu.major?.let { item.put("major", it) }
                    edu.grade?.let { item.put("grade", it) }
                    edu.location?.id?.let { item.put("location", it) }
                    put(item)
                }
            }
            put("educationList", educations)
            val experiences = JSONArray().apply {
                experienceList?.forEach { exp ->
                    val item = JSONObject()
                    exp.companyName?.let { item.put("companyName", it) }
                    exp.jobGroup?.let { item.put("jobGroup", it) }
                    exp.jobPosition?.let { item.put("jobPosition", it) }
                    exp.startDate?.let { item.put("jobStart", it) }
                    exp.endDate?.let { item.put("jobEnd", it) }
                    put(item)
                }
            }
            put("experienceList", experiences)
            val licenses = JSONArray().apply {
                licenseList?.forEach { license ->
                    val item = JSONObject()
                    license.certification?.let { item.put("certification", it) }
                    license.score?.let { item.put("score", it) }
                    put(item)
                }
            }
            put("licenseList", licenses)
            val others = JSONArray().apply {
                val item = JSONObject()
                item.put("content", otherSpec)
                put(item)
            }
            put("etcList", others)
        }
        Log.d(TAG, "---> BODY: $json")
        val body = json.toString().toRequestBody(MEDIA_TYPE_JSON.toMediaType())
        try {
            lifecycleScope.launch {
                mService.updateSpecs(body).apply {
                    if (isSuccess) {
                        Log.d(TAG, "---> SPEC UPLOAD SUCCESS")
                        showToast(Constants.spec_updated)
                        startActivity(Intent(this@LogInActivity, MainActivity::class.java))
                        finish()
                        return@launch
                    }
                    Log.e(TAG, "---> SPEC UPLOAD FAILURE: $message")
                    showToast(message)
                }
            }
        }
        catch (e: java.lang.Exception){
            Log.e(TAG, "---> SPEC UPLOAD FAILURE: $e")
            showToast(request_failed)
        }
    }

}