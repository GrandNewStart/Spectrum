package com.spectrum.spectrum.src.activities.main.fragments.mySpec

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentMySpecBinding
import com.spectrum.spectrum.src.customs.BaseFragment
import com.spectrum.spectrum.src.models.RefreshEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MySpecFragment: BaseFragment() {

    lateinit var mBinding: FragmentMySpecBinding
    val mViewModel by viewModels<MySpecViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_spec, container, false)
        mBinding.apply {
            lifecycleOwner = this@MySpecFragment
            fragment = this@MySpecFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindViews(this@MySpecFragment)
        }
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveRefreshEvent(event: RefreshEvent) {
        mViewModel.refresh(this)
    }

}