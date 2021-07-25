package com.spectrum.spectrum.src.activities.main.fragments.myPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentMyPageBinding
import com.spectrum.spectrum.src.customs.BaseFragment

class MyPageFragment: BaseFragment() {

    lateinit var mBinding: FragmentMyPageBinding
    val mViewModel by viewModels<MyPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_page, container, false)
        mBinding.apply {
            lifecycleOwner = this@MyPageFragment
            fragment = this@MyPageFragment
            viewModel = mViewModel
        }
        mViewModel.apply {

        }
        return mBinding.root
    }

}