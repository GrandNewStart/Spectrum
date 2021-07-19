package com.spectrum.spectrum.src.activities.main.fragments.companyInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentCompanyInfoBinding
import com.spectrum.spectrum.src.customs.BaseFragment

class CompanyInfoFragment: BaseFragment() {

    lateinit var mBinding: FragmentCompanyInfoBinding
    val mViewModel by viewModels<CompanyInfoViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_company_info, container, false)
        mBinding.apply {
            lifecycleOwner = this@CompanyInfoFragment
            fragment = this@CompanyInfoFragment
            viewModel = mViewModel
        }
        mViewModel.apply {

        }
        return mBinding.root
    }

}