package com.spectrum.spectrum.src.activities.main.fragments.editSpec

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.databinding.FragmentEditSpecBinding
import com.spectrum.spectrum.src.dialogs.*
import com.spectrum.spectrum.src.models.CertItem
import com.spectrum.spectrum.src.models.EduItem
import com.spectrum.spectrum.src.models.ExpItem
import com.spectrum.spectrum.src.models.JobGroup

class EditSpecViewModel: ViewModel() {

    var mAge: Int? = null
    var mSex: String? = null
    var mJobGroup1: JobGroup? = null
    var mJobGroup2: JobGroup? = null
    var mEduItems = ObservableArrayList<EduItem>()
    var mExpItems = ObservableArrayList<ExpItem>()
    var mCertItems = ObservableArrayList<CertItem>()
    var mComments: String? = null

    fun bindViews(binding: FragmentEditSpecBinding) {
        binding.apply {
            // TEST CODE START
                mAge = 30
                mSex = "F"
                mJobGroup1 = JobGroup(0, "IT/인터넷")
                mJobGroup2 = JobGroup(1, "디자인")
                mEduItems.add(EduItem("무슨학교", "수도권", "대학교(4년제)", "졸업", "무슨전공", 4.0, 4.3))
                mEduItems.add(EduItem("무슨학교", "수도권", "대학교(4년제)", "졸업", "무슨전공", 4.0, 4.3))
                mExpItems.add(ExpItem("무슨기업", "무슨직무", "무슨직급", "2000.01.01", "2005.01.01"))
                mExpItems.add(ExpItem("무슨기업", "무슨직무", "무슨직급", "2000.01.01", "2005.01.01"))
                mCertItems.add(CertItem("무슨시험", "몇급몇점"))
                mCertItems.add(CertItem("무슨시험", "몇급몇점"))
                mCertItems.add(CertItem("무슨시험", "몇급몇점"))
                mComments = "어려서부터 우리집은 가난했었고 남들 다 하는 외식 한번 한적이 없었고 일터 나가신 어머니 집에 없으면 언제나 혼자서 끓여먹었던 라면..."
                age = mAge
                sex = mSex
                jobGroup1 = mJobGroup1
                jobGroup2 = mJobGroup2
                eduItems = mEduItems
                expItems = mExpItems
                certItems = mCertItems
                comments = mComments
            // TEST CODE END
        }
    }


    fun backButtonAction(fragment: EditSpecFragment) {
        fragment.findNavController().popBackStack()
    }

    fun saveButtonAction(fragment: EditSpecFragment) {
        Log.d(TAG, "--- TRYING TO SAVE USER SPEC ---")
        Log.d(TAG, "    ---> AGE: $mAge")
        Log.d(TAG, "    ---> SEX: $mSex")
        Log.d(TAG, "    ---> JOB 1: ${mJobGroup1?.name}")
        Log.d(TAG, "    ---> JOB 2: ${mJobGroup2?.name}")
        Log.d(TAG, "    ---> ED")
        mEduItems.forEach { Log.d(TAG, "       -> ${it.level}") }
        Log.d(TAG, "    ---> EX")
        mExpItems.forEach { Log.d(TAG, "       -> ${it.company}") }
        Log.d(TAG, "    ---> CERT")
        mCertItems.forEach { Log.d(TAG, "       -> ${it.name}") }
        Log.d(TAG, "---> COMMENTS: $mComments")
    }

    fun showAgePickerDialog(fragment: EditSpecFragment) {
        AgePickerDialog(fragment.requireContext())
            .setOnAgePickedListener { age ->
                mAge = age
                fragment.mBinding.age = age
            }.show()
    }

    fun showJobGroup(fragment: EditSpecFragment) {
        JobGroupDialog(fragment.requireContext())
            .setOnSaveListener { first, second ->
                val groups = ArrayList<JobGroup>()
                first?.let {
                    Log.d(TAG, "---> FIRST ITEM: ${it.name}")
                    mJobGroup1 = it
                    mJobGroup2 = null
                    groups.add(it)
                }
                second?.let {
                    Log.d(TAG, "---> SECOND ITEM: ${it.name}")
                    mJobGroup2 = it
                    groups.add(it)
                }
                fragment.mBinding.jobGroup1 = mJobGroup1
                fragment.mBinding.jobGroup2 = mJobGroup2
            }
            .show()
    }

    fun showEducationDialog(fragment: EditSpecFragment) {
        EducationDialog(fragment.requireContext())
            .setOnSaveListener { item ->
                mEduItems.add(item)
                fragment.mBinding.eduItems = mEduItems
            }.show()
    }

    fun showExperienceDialog(fragment: EditSpecFragment) {
        ExperienceDialog(fragment.requireContext())
            .setOnSaveListener { item ->
                mExpItems.add(item)
                fragment.mBinding.expItems = mExpItems
            }.show()
    }

    fun showCertificationDialog(fragment: EditSpecFragment) {
        CertificationDialog(fragment.requireContext())
            .setOnSaveListener { item ->
                mCertItems.add(item)
                fragment.mBinding.certItems = mCertItems
            }.show()
    }

    companion object {
        val TAG = EditSpecViewModel::class.java.simpleName.toString()
    }

}