package com.spectrum.spectrum.src.activities.main.fragments.myScrap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentMyScrapBinding
import com.spectrum.spectrum.src.customs.BaseFragment

class MyScrapFragment: BaseFragment() {

    lateinit var mBinding: FragmentMyScrapBinding
    val mViewModel by viewModels<MyScrapViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_scrap, container, false)
        mBinding.apply {
            lifecycleOwner = this@MyScrapFragment
            fragment = this@MyScrapFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindViews(this@MyScrapFragment)
        }
        return mBinding.root
    }

}