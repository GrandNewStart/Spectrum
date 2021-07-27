package com.spectrum.spectrum.src.activities.main.fragments.myCompany

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.src.activities.main.fragments.myCompany.adapters.CompanyAdapter
import com.spectrum.spectrum.src.activities.main.fragments.myCompany.models.Company
import kotlinx.coroutines.launch

class MyCompanyViewModel: ViewModel() {

    private var mIsDataLoaded =false
    private var mCompanies = ArrayList<Company>()

    fun bindView(fragment: MyCompanyFragment) {
        fragment.mBinding.apply {
            companyRecyclerView.adapter = CompanyAdapter(mCompanies)
        }

        if (!mIsDataLoaded) {
            mIsDataLoaded = true
            getCompanies(fragment)
        }
    }

    fun backButtonAction(fragment: MyCompanyFragment) {
        fragment.findNavController().popBackStack()
    }

    private fun getCompanies(fragment: MyCompanyFragment) {
        viewModelScope.launch {
            // TEST CODE START
                val com1 = Company(0, "카카오", "IT/정보통신업", 20)
                val com2 = Company(0, "네이버", "IT/정보통신업", 30)
                mCompanies.add(com1)
                mCompanies.add(com2)
                fragment.mBinding.companyRecyclerView.adapter?.notifyDataSetChanged()
            // TEST CODE END
        }
    }

}