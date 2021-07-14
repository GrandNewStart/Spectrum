package com.spectrum.spectrum.src.activities.main.fragments.jobGroup

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.databinding.FragmentJobGroupBinding
import com.spectrum.spectrum.src.activities.main.MainActivity
import com.spectrum.spectrum.src.models.JobGroup

class JobGroupViewModel: ViewModel() {

    private val mJobGroups = ArrayList<JobGroup>().apply {
        add(JobGroup(0, "경영/사무", 0))
        add(JobGroup(1, "영업/고객상담", 0))
        add(JobGroup(2, "IT/인터넷", 0))
        add(JobGroup(3, "디자인", 0))
        add(JobGroup(4, "서비스", 0))
        add(JobGroup(5, "의료", 0))
        add(JobGroup(6, "생산/제조", 0))
        add(JobGroup(7, "건설", 0))
        add(JobGroup(8, "유통/무역", 0))
        add(JobGroup(9, "미디어", 0))
        add(JobGroup(10, "교육", 0))
        add(JobGroup(11, "특수계층/공공", 0))
        add(JobGroup(12, "기타", 0))
    }
    var mFirstItem: JobGroup? = null
    var mSecondItem: JobGroup? = null
    var mThirdItem: JobGroup? = null

    fun bindViews(binding: FragmentJobGroupBinding) {
        mFirstItem?.let { mJobGroups.forEach { group -> if (group.id == it.id) group.selectIndex = 1 }}
        mSecondItem?.let { mJobGroups.forEach { group -> if (group.id == it.id) group.selectIndex = 2 }}
        mThirdItem?.let { mJobGroups.forEach { group -> if (group.id == it.id) group.selectIndex = 3 }}
        binding.apply {
            jobGroups = mJobGroups
        }
    }

    fun backButtonAction(fragment: JobGroupFragment) {
        fragment.findNavController().popBackStack()
    }

    fun saveButtonAction(fragment: JobGroupFragment) {
        (fragment.activity as MainActivity).mJobGroup1 = mFirstItem
        (fragment.activity as MainActivity).mJobGroup2 = mSecondItem
        (fragment.activity as MainActivity).mJobGroup3 = mThirdItem
        fragment.findNavController().popBackStack()
    }

}