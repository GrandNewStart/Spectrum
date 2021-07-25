package com.spectrum.spectrum.src.activities.main.fragments.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentNotificationBinding
import com.spectrum.spectrum.src.customs.BaseFragment

class NotificationFragment: BaseFragment() {

    lateinit var mBinding: FragmentNotificationBinding
    val mViewModel by viewModels<NotificationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false)
        mBinding.apply {
            lifecycleOwner = this@NotificationFragment
            fragment = this@NotificationFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindViews(this@NotificationFragment)
        }
        return mBinding.root
    }

}