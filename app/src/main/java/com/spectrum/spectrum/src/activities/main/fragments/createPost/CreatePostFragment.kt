package com.spectrum.spectrum.src.activities.main.fragments.createPost

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentCreatePostBinding
import com.spectrum.spectrum.src.customs.BaseFragment

class CreatePostFragment: BaseFragment() {

    val mViewModel by viewModels<CreatePostViewModel>()
    lateinit var mBinding: FragmentCreatePostBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_create_post, container, false)
        mBinding.apply {
            lifecycleOwner = this@CreatePostFragment
            fragment = this@CreatePostFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindViews(this@CreatePostFragment)
        }
        return mBinding.root
    }

}