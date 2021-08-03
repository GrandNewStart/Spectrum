package com.spectrum.spectrum.src.activities.main.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentSearchBinding
import com.spectrum.spectrum.src.activities.main.fragments.search.fragments.searchCompany.SearchCompanyFragment
import com.spectrum.spectrum.src.activities.main.fragments.search.fragments.searchPost.SearchPostFragment
import com.spectrum.spectrum.src.customs.BaseFragment

class SearchFragment: BaseFragment() {

    lateinit var mBinding: FragmentSearchBinding
    val mViewModel by viewModels<SearchViewModel>()
    var mPostFragment = SearchPostFragment()
    var mCompanyFragment = SearchCompanyFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            mViewModel.mKeyword = getString("keyword")
        }
        if (mViewModel.mKeyword == null) {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        mBinding.apply {
            lifecycleOwner = this@SearchFragment
            fragment = this@SearchFragment
            viewModel = mViewModel
        }
        mViewModel.apply {
            bindView(this@SearchFragment)
        }
        return mBinding.root
    }


}