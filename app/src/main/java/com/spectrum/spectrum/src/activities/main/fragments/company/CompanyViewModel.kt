package com.spectrum.spectrum.src.activities.main.fragments.company

import android.view.View
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spectrum.spectrum.databinding.FragmentCompanyBinding
import com.spectrum.spectrum.src.models.Company
import com.spectrum.spectrum.src.models.Duty
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.models.Industry
import com.spectrum.spectrum.src.models.JobGroup

class CompanyViewModel: ViewModel() {

    private val mSearchFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        mSearchHasFocusLiveData.value = hasFocus
    }
    val mSearchHasFocusLiveData = MutableLiveData<Boolean>()
    private var mIndustries = ArrayList<Industry>().apply {
        add(Industry(-1,  "전체"))
        add(Industry(0,  "서비스업"))
        add(Industry(1,  "금융/은행업"))
        add(Industry(2, "IT/정보통신업"))
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

    fun bindViews(binding: FragmentCompanyBinding) {
        binding.apply {
            searchFocusListener = mSearchFocusChangeListener
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

    fun fabAction(fragment: CompanyFragment) {
        fragment.showToast(Constants.under_construction)
    }

}