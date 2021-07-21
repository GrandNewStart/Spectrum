package com.spectrum.spectrum.src.activities.main.fragments.home

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentHomeBinding
import com.spectrum.spectrum.src.customs.BaseFragment

class HomeFragment: BaseFragment() {

    lateinit var mBinding: FragmentHomeBinding
    val mViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        mBinding.apply {
            lifecycleOwner = this@HomeFragment
            fragment = this@HomeFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindViews(mBinding)
        }
        return mBinding.root
    }
}