package com.spectrum.spectrum.src.activities.main.fragments.spec

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentSpecBinding
import com.spectrum.spectrum.src.customs.BaseFragment

class SpecFragment: BaseFragment() {

    lateinit var mBinding: FragmentSpecBinding
    val mViewModel by viewModels<SpecViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_spec, container, false)
        mBinding.apply {
            lifecycleOwner = this@SpecFragment
            fragment = this@SpecFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindViews(this@SpecFragment)
        }
        return mBinding.root
    }

}