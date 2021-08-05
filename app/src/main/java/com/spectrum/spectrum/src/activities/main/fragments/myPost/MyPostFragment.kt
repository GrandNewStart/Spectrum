package com.spectrum.spectrum.src.activities.main.fragments.myPost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentMyPostBinding
import com.spectrum.spectrum.src.customs.BaseFragment
import com.spectrum.spectrum.src.models.RefreshEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MyPostFragment: BaseFragment() {

    lateinit var mBinding: FragmentMyPostBinding
    val mViewModel by viewModels<MyPostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_post, container, false)
        mBinding.apply {
            lifecycleOwner = this@MyPostFragment
            fragment = this@MyPostFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindViews(this@MyPostFragment)
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