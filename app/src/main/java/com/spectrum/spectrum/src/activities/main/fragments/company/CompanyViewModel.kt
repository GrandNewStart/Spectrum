package com.spectrum.spectrum.src.activities.main.fragments.company

import android.util.Log
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.MainActivity
import com.spectrum.spectrum.src.activities.main.fragments.company.adapters.CompanyAdapter
import com.spectrum.spectrum.src.activities.main.fragments.company.interfaces.CompanyApi
import com.spectrum.spectrum.src.activities.main.fragments.company.models.Company
import com.spectrum.spectrum.src.activities.main.fragments.company.models.Industry
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.retrofit
import kotlinx.coroutines.launch

class CompanyViewModel: ViewModel() {

    private val mService = retrofit.create(CompanyApi::class.java)

    private var mIndustries = ArrayList<Industry>()
    private var mMyCompanies = ArrayList<Company>()
    private var mCompanies = ArrayList<Company>()
    private var mFilterId: Int? = null
    private var mPage = 0
    private var mIsDataLoaded = false
    private var mDidReachEnd = false
    private var mIsLoading = false

    fun bindViews(fragment: CompanyFragment) {
        fragment.mBinding.apply {
            industries = mIndustries
            myCompanies = mMyCompanies
            companies = mCompanies
        }
        if (!mIsDataLoaded) {
            mIsDataLoaded = true
            getIndustries(fragment)
        }
    }

    fun backButtonAction(fragment: CompanyFragment, editText: EditText) {
        if (editText.hasFocus()) {
            fragment.showKeyboard(editText, false)
            editText.clearFocus()
        }
    }

    fun seeAllButtonAction(fragment: CompanyFragment) {
        fragment.showToast(Constants.under_construction)
    }

    fun proceedToSearch(fragment: CompanyFragment, keyword: String) {
        (fragment.activity as MainActivity).hideSearchDialog()
        fragment.findNavController().navigate(R.id.company_to_search, bundleOf("keyword" to keyword))
    }

    fun refresh(fragment: CompanyFragment) {
        mMyCompanies.clear()
        mCompanies.clear()
        mDidReachEnd = false
        mPage = 0
        fragment.mBinding.apply {
            myCompanies = mMyCompanies
            companies = mCompanies
        }
        getIndustries(fragment)
    }

    fun onIndustrySelected(id: Int?, fragment: CompanyFragment) {
        mFilterId = id
        mCompanies.clear()
        fragment.mBinding.companies = mCompanies
        refresh(fragment)
    }

    private fun getIndustries(fragment: CompanyFragment) {
        viewModelScope.launch {
            mService.getIndustries().apply {
                if (isSuccess) {
                    val defaultItem = Industry(null, Constants.all, mFilterId == null)
                    mIndustries.add(defaultItem)
                    result.forEach {
                        it.isSelected = (mFilterId == it.id)
                        mIndustries.add(it)
                    }
                    fragment.mBinding.industries = mIndustries
                    getCompanies(fragment)
                }
                else {
                    Log.e(TAG, "---> INDUSTRY LOAD FAILURE: $message")
                    fragment.showToast(Constants.request_failed)
                }
            }
        }
    }

    fun getCompanies(fragment: CompanyFragment) {
        if (mIndustries.isEmpty()) return
        if (mDidReachEnd) return
        if (mIsLoading) return

        fragment.showProgressDialog(true)
        mIsLoading = true

        viewModelScope.launch {
            mService.getCompanyPage(mPage, mFilterId).apply {
                fragment.showProgressDialog(false)
                mIsLoading = false
                if (isSuccess) {
                    if (result.isEmpty()) {
                        mDidReachEnd = true
                        return@launch
                    }
                    mPage++
                    result.forEach { mCompanies.add(it) }
                    fragment.mBinding.companyRecyclerView.apply {
                        (adapter as CompanyAdapter).addNewItems(result)
                    }
                }
                else {
                    Log.e(TAG, "---> COMPANY PAGE LOAD FAILURE: $message")
                    fragment.showToast(Constants.request_failed)
                }
            }
        }
    }

    companion object {
        val TAG = CompanyViewModel::class.java.simpleName.toString()
    }

}