package com.spectrum.spectrum.src.activities.main.fragments.createSpec

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.src.activities.main.fragments.createSpec.interfaces.CreateSpecApi
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.customs.BaseActivity
import com.spectrum.spectrum.src.dialogs.*
import com.spectrum.spectrum.src.models.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class CreateSpecViewModel: ViewModel() {

    private val mService = retrofit.create(CreateSpecApi::class.java)

    var mAge: Int? = null
    var mSex: Int? = null
    var mJobGroup: JobGroup? = null
    var mEducations = ObservableArrayList<Education>()
    var mExperiences = ObservableArrayList<Experience>()
    var mLicenses = ObservableArrayList<License>()
    var mOtherSpecs: String? = null

    fun bindViews(fragment: CreateSpecFragment) {
        fragment.mBinding.apply {
            age = mAge
            sex = mSex
            jobGroup = mJobGroup
            educations = mEducations
            experiences = mExperiences
            licenses = mLicenses
            otherSpecs = mOtherSpecs
        }
    }

    fun backButtonAction(fragment: CreateSpecFragment) {
        fragment.findNavController().popBackStack()
    }

    fun saveButtonAction(fragment: CreateSpecFragment) {
        if (mAge == null || mSex == null || mJobGroup == null) {
            fragment.showToast(Constants.enter_required_fields)
            return
        }
        val jobGroupList = ArrayList<Int>().apply {
            mJobGroup?.let { add(it.id) }
        }
        val educationList = ArrayList<Education>().apply {
            mEducations.forEach { add(it) }
        }
        val experienceList = ArrayList<Experience>().apply {
            mExperiences.forEach { add(it) }
        }
        val licenseList = ArrayList<License>().apply {
            mLicenses.forEach { add(it) }
        }
        updateMySpecs(mAge, mSex, jobGroupList, educationList, experienceList, licenseList, mOtherSpecs, fragment)
    }

    fun showAgePickerDialog(fragment: CreateSpecFragment) {
        AgePickerDialog(fragment.requireContext())
            .setOnAgePickedListener { age ->
                mAge = age
                fragment.mBinding.age = age
            }.show()
    }

    fun showJobGroup(fragment: CreateSpecFragment) {
        var jobGroup: JobGroup? = null
        mJobGroup?.let { jobGroup = JobGroup(it.id, it.name) }

        JobGroupDialog(fragment.requireContext())
            .setPreselectedItem(jobGroup)
            .setOnSaveListener { item ->
                mJobGroup = item
                fragment.mBinding.jobGroup = mJobGroup
            }
            .show()
    }

    fun showEducationDialog(fragment: CreateSpecFragment) {
        EducationDialog(fragment.requireContext())
            .setOnSaveListener { item ->
                mEducations.add(item)
                fragment.mBinding.educations = mEducations
            }.show()
    }

    fun showExperienceDialog(fragment: CreateSpecFragment) {
        ExperienceDialog(fragment.requireContext())
            .setOnSaveListener { item ->
                mExperiences.add(item)
                fragment.mBinding.experiences = mExperiences
            }.show()
    }

    fun showLicenseDialog(fragment: CreateSpecFragment) {
        LicenseDialog(fragment.requireContext())
            .setOnSaveListener { item ->
                mLicenses.add(item)
                fragment.mBinding.licenses = mLicenses
            }.show()
    }

    private fun updateMySpecs(age: Int?,
                              sex: Int?,
                              groupList: ArrayList<Int>?,
                              educationList: ArrayList<Education>?,
                              experienceList: ArrayList<Experience>?,
                              licenseList: ArrayList<License>?,
                              otherSpec: String?,
                              fragment: CreateSpecFragment) {
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
        Log.d(BaseActivity.TAG, "---> BODY: $json")
        val body = json.toString().toRequestBody(Constants.MEDIA_TYPE_JSON.toMediaType())
        try {
            fragment.showProgressDialog(true)
            viewModelScope.launch {
                mService.uploadMySpecs(body).apply {
                    fragment.showProgressDialog(false)
                    if (isSuccess) {
                        Log.d(TAG, "---> SPEC UPLOAD SUCCESS")
                        fragment.showToast(Constants.spec_updated)
                        fragment.findNavController().popBackStack()
                        EventBus.getDefault().post(RefreshEvent(true))
                        return@launch
                    }
                    Log.e(TAG, "---> SPEC UPLOAD FAILURE: $message")
                    fragment.showToast(message)
                }
            }
        }
        catch (e: Exception){
            Log.e(TAG, "---> SPEC UPLOAD FAILURE: $e")
            fragment.showToast(Constants.request_failed)
        }
    }

    companion object {
        val TAG = CreateSpecViewModel::class.java.simpleName.toString()
    }

}