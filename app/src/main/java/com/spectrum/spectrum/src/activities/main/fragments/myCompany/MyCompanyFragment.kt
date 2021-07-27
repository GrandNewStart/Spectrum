package com.spectrum.spectrum.src.activities.main.fragments.myCompany

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentMyCompanyBinding
import com.spectrum.spectrum.src.customs.BaseFragment

class MyCompanyFragment: BaseFragment() {

    lateinit var mBinding: FragmentMyCompanyBinding
    val mViewModel by viewModels<MyCompanyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_company, container ,false)
        mBinding.apply {
            lifecycleOwner = this@MyCompanyFragment
            fragment = this@MyCompanyFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindView(this@MyCompanyFragment)
        }
        return mBinding.root
    }

}