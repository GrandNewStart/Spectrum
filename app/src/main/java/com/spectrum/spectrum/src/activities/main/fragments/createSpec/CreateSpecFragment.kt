package com.spectrum.spectrum.src.activities.main.fragments.createSpec

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentCreateSpecBinding
import com.spectrum.spectrum.src.customs.BaseFragment

class CreateSpecFragment: BaseFragment() {

    lateinit var mBinding: FragmentCreateSpecBinding
    val mViewModel by viewModels<CreateSpecViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_spec, container, false)
        mBinding.apply {
            lifecycleOwner = this@CreateSpecFragment
            fragment = this@CreateSpecFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindViews(this@CreateSpecFragment)
        }
        return mBinding.root
    }

}