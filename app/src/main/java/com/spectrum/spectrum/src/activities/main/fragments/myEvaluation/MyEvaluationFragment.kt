package com.spectrum.spectrum.src.activities.main.fragments.myEvaluation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentMyEvaluationBinding
import com.spectrum.spectrum.src.customs.BaseFragment

class MyEvaluationFragment: BaseFragment() {


    lateinit var mBinding: FragmentMyEvaluationBinding
    val mViewModel by viewModels<MyEvaluationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_evaluation, container, false)
        mBinding.apply {
            lifecycleOwner = this@MyEvaluationFragment
            fragment = this@MyEvaluationFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindViews(this@MyEvaluationFragment)
        }
        return mBinding.root
    }

}