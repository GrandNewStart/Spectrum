package com.spectrum.spectrum.src.activities.main.fragments.home

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentHomeBinding
import com.spectrum.spectrum.src.activities.main.MainActivity
import com.spectrum.spectrum.src.customs.BaseFragment
import com.spectrum.spectrum.src.models.JobGroup

class HomeFragment: BaseFragment() {

    private lateinit var mBinding: FragmentHomeBinding
    private val mViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        mBinding.apply {
            lifecycleOwner = this@HomeFragment
            fragment = this@HomeFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            mSearchHasFocusLiveData.observe(viewLifecycleOwner, { hasFocus ->
                if (hasFocus) {
                    mBinding.appBarButton.setImageResource(R.drawable.icon_back)
                }
                else {
                    mBinding.appBarButton.setImageResource(R.drawable.icon_logo_small)
                    mBinding.searchEditText.clearFocus()
                    showKeyboard(mBinding.root, false)
                }
            })
            bindViews(mBinding)
        }
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        mBinding.apply {
            val groups = ArrayList<JobGroup>()
            (activity as MainActivity).mJobGroup1?.let { groups.add(it) }
            (activity as MainActivity).mJobGroup2?.let { groups.add(it) }
            (activity as MainActivity).mJobGroup3?.let { groups.add(it) }
            myGroups = groups
        }
    }

}