package com.spectrum.spectrum.src.activities.main.fragments.editSpec

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentEditSpecBinding
import com.spectrum.spectrum.src.customs.BaseFragment

class EditSpecFragment: BaseFragment() {

    lateinit var mBinding: FragmentEditSpecBinding
    val mViewModel by viewModels<EditSpecViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_spec, container, false)
        mBinding.apply {
            lifecycleOwner = this@EditSpecFragment
            fragment = this@EditSpecFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindViews(this@EditSpecFragment)
        }
        return mBinding.root
    }


}