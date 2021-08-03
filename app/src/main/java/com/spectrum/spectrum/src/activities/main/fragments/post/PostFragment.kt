package com.spectrum.spectrum.src.activities.main.fragments.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentPostBinding
import com.spectrum.spectrum.src.activities.main.MainActivity
import com.spectrum.spectrum.src.customs.BaseFragment

class PostFragment: BaseFragment() {

    lateinit var mBinding: FragmentPostBinding
    val mViewModel by viewModels<PostViewModel>()

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
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_post, container, false)
        mBinding.apply {
            lifecycleOwner = this@PostFragment
            fragment = this@PostFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindViews(this@PostFragment)
        }
        observeCommentSelection()
        return mBinding.root
    }

    private fun observeCommentSelection() {
        mViewModel.apply {
            mEduComment.observe(viewLifecycleOwner) {
            mExpComment.observe(viewLifecycleOwner) {
            mLicComment.observe(viewLifecycleOwner) {
            mOtherComment.observe(viewLifecycleOwner) {
                val myCommentsChanged = mViewModel.checkForCommentChanges()
                mBinding.postResponsesButton.visibility = if (myCommentsChanged) View.VISIBLE else View.GONE
            }
            }
            }
            }
        }
    }

}