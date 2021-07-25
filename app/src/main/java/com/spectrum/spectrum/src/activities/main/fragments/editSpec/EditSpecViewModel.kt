package com.spectrum.spectrum.src.activities.main.fragments.editSpec

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.src.activities.main.fragments.createPost.CreatePostViewModel
import com.spectrum.spectrum.src.activities.main.fragments.editSpec.interfaces.EditSpecApi
import com.spectrum.spectrum.src.activities.main.fragments.editSpec.models.*
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.customs.BaseActivity
import com.spectrum.spectrum.src.dialogs.*
import com.spectrum.spectrum.src.models.*
import com.spectrum.spectrum.src.models.Degree
import com.spectrum.spectrum.src.models.Graduate
import com.spectrum.spectrum.src.models.JobGroup
import com.spectrum.spectrum.src.models.Location
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class EditSpecViewModel: ViewModel() {

    // Api
    private val mService = retrofit.create(EditSpecApi::class.java)

    var mIsDataLoaded = false
    var mAge: Int? = null
    var mSex: Int? = null
    var mJobGroup1: JobGroup? = null
    var mJobGroup2: JobGroup? = null
    var mJobGroup3: JobGroup? = null
    var mEducations = ObservableArrayList<Education>()
    var mExperiences = ObservableArrayList<Experience>()
    var mLicenses = ObservableArrayList<License>()
    var mOtherSpecs: String? = null

    fun bindViews(fragment: EditSpecFragment) {
        fragment.mBinding.apply {
            age = mAge
            sex = mSex
            jobGroup1 = mJobGroup1
            jobGroup2 = mJobGroup2
            educations = mEducations
            experiences = mExperiences
            licenses = mLicenses
            otherSpecs = mOtherSpecs
        }
        if (!mIsDataLoaded) {
            mIsDataLoaded = true
            getMySpec(fragment)
        }
    }


    fun backButtonAction(fragment: EditSpecFragment) {
        fragment.findNavController().popBackStack()
    }

    fun saveButtonAction(fragment: EditSpecFragment) {
        if (mAge == null || mSex == null || mJobGroup1 == null) {
            fragment.showToast(Constants.enter_required_fields)
            return
        }
        val jobGroupList = ArrayList<Int>().apply {
            mJobGroup1?.let { add(it.id) }
            mJobGroup2?.let { add(it.id) }
            mJobGroup3?.let { add(it.id) }
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

    fun showAgePickerDialog(fragment: EditSpecFragment) {
        AgePickerDialog(fragment.requireContext())
            .setOnAgePickedListener { age ->
                mAge = age
                fragment.mBinding.age = age
            }.show()
    }

    fun showJobGroup(fragment: EditSpecFragment) {
        var firstItem: JobGroup? = null
        var secondItem: JobGroup? = null
        var thirdItem: JobGroup? = null

        mJobGroup1?.let { firstItem = JobGroup(it.id, it.name) }
        mJobGroup2?.let { secondItem = JobGroup(it.id, it.name) }
        mJobGroup3?.let { thirdItem = JobGroup(it.id, it.name) }

        JobGroupDialog(fragment.requireContext())
            .setPreselectedItems(firstItem, secondItem, thirdItem)
            .setOnSaveListener { first, second, third ->
                first?.let {
                    mJobGroup1 = JobGroup(it.id, it.name)
                    mJobGroup2 = null
                    mJobGroup3 = null
                }
                second?.let {
                    mJobGroup2 = JobGroup(it.id, it.name)
                    mJobGroup3 = null
                }
                third?.let {
                    mJobGroup3 = JobGroup(it.id, it.name)
                }
                fragment.mBinding.jobGroup1 = mJobGroup1
                fragment.mBinding.jobGroup2 = mJobGroup2
                fragment.mBinding.jobGroup3 = mJobGroup3
            }
            .show()
    }

    fun showEducationDialog(fragment: EditSpecFragment) {
        EducationDialog(fragment.requireContext())
            .setOnSaveListener { item ->
                mEducations.add(item)
                fragment.mBinding.educations = mEducations
            }.show()
    }

    fun showExperienceDialog(fragment: EditSpecFragment) {
        ExperienceDialog(fragment.requireContext())
            .setOnSaveListener { item ->
                mExperiences.add(item)
                fragment.mBinding.experiences = mExperiences
            }.show()
    }

    fun showCertificationDialog(fragment: EditSpecFragment) {
        LicenseDialog(fragment.requireContext())
            .setOnSaveListener { item ->
                mLicenses.add(item)
                fragment.mBinding.licenses = mLicenses
            }.show()
    }

    private fun getMySpec(fragment: EditSpecFragment) {
        try {
            viewModelScope.launch {
                fragment.showProgressDialog(true)
                mService.getMySpec().apply {
                    fragment.showProgressDialog(false)
                    if (isSuccess) {
                        Log.d(TAG,"---> $result")
                        mAge = result.age
                        mSex = if (result.sex == "남자") 0 else 1
                        result.jobGroups?.let {
                            if (it.size > 0) { mJobGroup1 = JobGroup(it[0].id, it[0].name) }
                            if (it.size > 1) { mJobGroup2 = JobGroup(it[1].id, it[1].name) }
                            if (it.size > 2) { mJobGroup3 = JobGroup(it[2].id, it[2].name) }
                        }
                        mEducations.clear()
                        result.educations?.forEach {
                            val item = Education(null, null, null, it.school, it.major, it.grade)
                            it.location?.apply { item.location = Location(id, data) }
                            it.graduate?.apply { item.graduate = Graduate(id, data) }
                            it.degree?.apply { item.degree = Degree(id, data) }
                            mEducations.add(item)
                        }
                        mExperiences.clear()
                        result.experiences?.forEach {
                            mExperiences.add(Experience(it.company, it.jobGroup, it.jobPosition, it.startDate, it.endDate))
                        }
                        mLicenses.clear()
                        result.licenses?.forEach {
                            mLicenses.add(License(it.name, it.score))
                        }
                        result.others?.let { mOtherSpecs = it[0].content }

                        fragment.mBinding.apply {
                            age = mAge
                            sex = mSex
                            jobGroup1 = mJobGroup1
                            jobGroup2 = mJobGroup2
                            jobGroup3 = mJobGroup3
                            otherSpecs = mOtherSpecs
                        }
                    }
                }
            }
        }
        catch (e: Exception) {
            Log.e(CreatePostViewModel.TAG, "---> MY SPEC FETCH FAILURE: $e")
            fragment.showProgressDialog(false)
            fragment.showToast(Constants.request_failed)
        }
    }

    private fun updateMySpecs(age: Int?,
                            sex: Int?,
                            groupList: ArrayList<Int>?,
                            educationList: ArrayList<Education>?,
                            experienceList: ArrayList<Experience>?,
                            licenseList: ArrayList<License>?,
                            otherSpec: String?,
                            fragment: EditSpecFragment) {
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
                mService.updateMySpecs(body).apply {
                    fragment.showProgressDialog(false)
                    if (isSuccess) {
                        Log.d(TAG, "---> SPEC UPDATE SUCCESS")
                        fragment.showToast(Constants.spec_updated)
                        fragment.findNavController().popBackStack()
                        EventBus.getDefault().post(RefreshEvent(true))
                        return@launch
                    }
                    Log.e(TAG, "---> SPEC UPDATE FAILURE: $message")
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
        val TAG = EditSpecViewModel::class.java.simpleName.toString()
    }

}