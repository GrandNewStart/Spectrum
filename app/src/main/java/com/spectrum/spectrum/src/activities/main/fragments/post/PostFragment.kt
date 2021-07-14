package com.spectrum.spectrum.src.activities.main.fragments.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentPostBinding
import com.spectrum.spectrum.src.customs.BaseFragment

class PostFragment: BaseFragment() {

    private lateinit var mBinding: FragmentPostBinding
    private val mViewModel by viewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_post, container, false)
        mBinding.apply {
            lifecycleOwner = this@PostFragment
            fragment = this@PostFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindViews(mBinding)
        }
        return mBinding.root
    }

}