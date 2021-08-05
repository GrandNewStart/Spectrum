package com.spectrum.spectrum.src.activities.main.fragments.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentCompanyBinding
import com.spectrum.spectrum.src.customs.BaseFragment

class CompanyFragment: BaseFragment() {

    lateinit var mBinding: FragmentCompanyBinding
    val mViewModel by viewModels<CompanyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_company, container, false)
        mBinding.apply {
            lifecycleOwner = this@CompanyFragment
            fragment = this@CompanyFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindViews(this@CompanyFragment)
        }
        return mBinding.root
    }

}