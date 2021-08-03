package com.spectrum.spectrum.src.activities.main.fragments.search

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.src.activities.main.fragments.search.interfaces.SearchInterface
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.retrofit
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {

    private val mService = retrofit.create(SearchInterface::class.java)

    var mKeyword: String? = null
    private var mIsDataLoaded = false
    private var mPostPage = 0
    private var mCompanyPage = 0
    private var mPostLoading = false
    private var mCompanyLoading = false
    private var mPostEndReached = false
    private var mCompanyEndReached = false

    fun bindView(fragment: SearchFragment) {
        fragment.mBinding.apply {
            searchTextInputEditText.setText(mKeyword)
        }
        if (!mIsDataLoaded) {
            searchPost(fragment)
            searchCompany(fragment)
            mIsDataLoaded = true
        }
    }

    fun backButtonAction(fragment: SearchFragment) {
        fragment.findNavController().popBackStack()
    }

    fun refresh(fragment: SearchFragment) {
        fragment.mPostFragment.clearItems()
        fragment.mCompanyFragment.clearItems()
        mPostPage = 0
        mCompanyPage = 0
        mPostEndReached = false
        mCompanyEndReached = false
        searchPost(fragment)
        searchCompany(fragment)
    }

    fun searchPost(fragment: SearchFragment) {
        if (mPostLoading) return
        if (mPostEndReached) return
        mPostLoading = true
        val keyword = mKeyword ?: return

        viewModelScope.launch {
            mService.searchPost(mPostPage, keyword).apply {
                mPostLoading = false
                if (isSuccess) {
                    if (result.isEmpty()) {
                        mPostEndReached = true
                    }
                    else {
                        fragment.mPostFragment.addItems(result)
                        mPostPage++
                    }
                    fragment.mPostFragment.apply {
                        mRecyclerView?.visibility = if (mPosts.isEmpty()) View.GONE else View.VISIBLE
                        mNoItemsText?.visibility = if (mPosts.isEmpty()) View.VISIBLE else View.GONE
                    }
                    return@launch
                }
                fragment.showToast(Constants.request_failed)
            }
        }
    }

    fun searchCompany(fragment: SearchFragment) {

    }

}