package com.spectrum.spectrum.src.activities.main.fragments.companyInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentCompanyInfoBinding
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.customs.BaseFragment

class CompanyInfoFragment: BaseFragment() {

    lateinit var mBinding: FragmentCompanyInfoBinding
    val mViewModel by viewModels<CompanyInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            mViewModel.mCompanyId = getInt("id")
        }
        if (mViewModel.mCompanyId == null) {
            showToast(Constants.request_failed)
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mViewModel.view?.let { return it }

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_company_info, container, false)
        mBinding.apply {
            lifecycleOwner = this@CompanyInfoFragment
            fragment = this@CompanyInfoFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindViews(this@CompanyInfoFragment)
            view = mBinding.root
        }
        return mBinding.root
    }

}