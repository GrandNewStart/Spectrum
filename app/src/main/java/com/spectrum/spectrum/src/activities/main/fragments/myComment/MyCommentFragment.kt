package com.spectrum.spectrum.src.activities.main.fragments.myComment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentMyCommentBinding
import com.spectrum.spectrum.src.customs.BaseFragment

class MyCommentFragment: BaseFragment() {


    lateinit var mBinding: FragmentMyCommentBinding
    val mViewModel by viewModels<MyCommentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_comment, container, false)
        mBinding.apply {
            lifecycleOwner = this@MyCommentFragment
            fragment = this@MyCommentFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindViews(this@MyCommentFragment)
        }
        return mBinding.root
    }

}