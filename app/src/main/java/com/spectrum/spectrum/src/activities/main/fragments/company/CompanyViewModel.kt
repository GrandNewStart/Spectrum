package com.spectrum.spectrum.src.activities.main.fragments.company

import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.FragmentCompanyBinding
import com.spectrum.spectrum.src.activities.main.MainActivity
import com.spectrum.spectrum.src.activities.main.fragments.company.adapters.CompanyAdapter
import com.spectrum.spectrum.src.models.Company
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.models.Industry
import kotlinx.coroutines.launch

class CompanyViewModel: ViewModel() {

    private var mIndustries = ArrayList<Industry>().apply {
        add(Industry(0,  "전체", true))
        add(Industry(1,  "서비스업"))
        add(Industry(2,  "금융/은행업"))
        add(Industry(3, "IT/정보통신업"))
        add(Industry(4,  "서비스업"))
        add(Industry(5,  "금융/은행업"))
        add(Industry(6, "IT/정보통신업"))
    }
    private var mMyCompanies = ArrayList<Company>().apply {
        add(Company("카카오", "IT/정보통신업", 20))
        add(Company("카카오", "IT/정보통신업", 20))
        add(Company("카카오", "IT/정보통신업", 20))
        add(Company("카카오", "IT/정보통신업", 20))
        add(Company("카카오", "IT/정보통신업", 20))
    }
    private var mCompanies = ArrayList<Company>().apply {
        add(Company("구글 코리아", "IT/정보통신업", 20))
        add(Company("네이버웹툰컴퍼니",  "IT/정보통신업", 20))
        add(Company("네이버", "IT/정보통신업", 20))
        add(Company("당근마켓", "IT/정보통신업", 20))
        add(Company("정승 네트워크", "IT/정보통신업", 20))
    }
    private var mFilterId = 0
    private var mPage = 0
    private var mIsDataLoaded = false
    private var mDidReachEnd = false
    private var mIsLoading = false

    fun bindViews(binding: FragmentCompanyBinding) {
        binding.apply {
            industries = mIndustries
            myCompanies = mMyCompanies
            companies = mCompanies
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
        fragment.mBinding.apply {
            myCompanies = mMyCompanies
            mCompanies = mCompanies
        }
    }

    fun onIndustrySelected(id: Int, fragment: CompanyFragment) {
        mFilterId = id
        mCompanies.clear()
        fragment.mBinding.companies = mCompanies
        getCompanies(fragment)
    }


    fun getCompanies(fragment: CompanyFragment) {
        if (mDidReachEnd) return
        if (mIsLoading) return
        fragment.showProgressDialog(true)
        mIsLoading = true
        viewModelScope.launch {
            // TEST CODE START
            val items = ArrayList<Company>().apply {
                add(Company("구글 코리아", "IT/정보통신업", 20))
                add(Company("네이버웹툰컴퍼니",  "IT/정보통신업", 20))
                add(Company("네이버", "IT/정보통신업", 20))
                add(Company("당근마켓", "IT/정보통신업", 20))
                add(Company("정승 네트워크", "IT/정보통신업", 20))
            }
            items.forEach { mCompanies.add(it) }
            fragment.mBinding.companyRecyclerView.apply {
                (adapter as CompanyAdapter).addNewItems(items)
            }
            // TEST CODE END
            fragment.showProgressDialog(false)
            mIsLoading = false
        }
    }

}