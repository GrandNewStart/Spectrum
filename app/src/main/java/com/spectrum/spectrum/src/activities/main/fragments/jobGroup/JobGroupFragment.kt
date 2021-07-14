package com.spectrum.spectrum.src.activities.main.fragments.jobGroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentJobGroupBinding
import com.spectrum.spectrum.src.activities.main.MainActivity
import com.spectrum.spectrum.src.customs.BaseFragment
import com.spectrum.spectrum.src.models.JobGroup

class JobGroupFragment: BaseFragment() {

    private lateinit var mBinding: FragmentJobGroupBinding
    private val mViewModel by viewModels<JobGroupViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_job_group, container, false)

        mBinding.apply {
            fragment = this@JobGroupFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            mFirstItem = (activity as MainActivity).mJobGroup1
            mSecondItem = (activity as MainActivity).mJobGroup2
            mThirdItem = (activity as MainActivity).mJobGroup3
            bindViews(mBinding)
        }
        return mBinding.root
    }

    fun applyChanges(selectedItems: ArrayList<JobGroup>) {
        mViewModel.apply {
            if (selectedItems.size > 0) mFirstItem = selectedItems[0]
            if (selectedItems.size > 1) mSecondItem = selectedItems[1]
            if (selectedItems.size > 2) mThirdItem = selectedItems[2]

        }
    }

}