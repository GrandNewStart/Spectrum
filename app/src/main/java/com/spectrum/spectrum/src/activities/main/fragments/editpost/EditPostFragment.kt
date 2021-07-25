package com.spectrum.spectrum.src.activities.main.fragments.editpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentEditPostBinding
import com.spectrum.spectrum.src.customs.BaseFragment

class EditPostFragment: BaseFragment() {

    lateinit var mBinding: FragmentEditPostBinding
    val mViewModel by viewModels<EditPostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            mViewModel.mPostId = getInt("id")
        }
        if (mViewModel.mPostId == null) {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_post ,container, false)
        mBinding.apply {
            lifecycleOwner = this@EditPostFragment
            fragment = this@EditPostFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindViews(this@EditPostFragment)
        }
        return mBinding.root
    }

}