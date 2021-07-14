package com.spectrum.spectrum.src.activities.main.fragments.company

import android.view.View
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spectrum.spectrum.databinding.FragmentCompanyBinding
import com.spectrum.spectrum.src.models.Company
import com.spectrum.spectrum.src.models.Duty
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.models.JobGroup

class CompanyViewModel: ViewModel() {

    private val mSearchFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        mSearchHasFocusLiveData.value = hasFocus
    }
    val mSearchHasFocusLiveData = MutableLiveData<Boolean>()
    private var mMyJobGroups = ArrayList<JobGroup>().apply {
        add(JobGroup(0,  "프론트엔드"))
        add(JobGroup(1,  "UI/UX"))
        add(JobGroup(2, "아무거나"))
    }
    private var mMyCompanies = ArrayList<Company>().apply {
        val duties = arrayListOf(Duty(0, 0, "프론트엔드"), Duty(1, 0, "UI/UX"), Duty(2, 0, "아무거나"),)
        add(Company("카카오", 20, duties))
        add(Company("카카오", 20, duties))
        add(Company("카카오", 20, duties))
        add(Company("카카오", 20, duties))
        add(Company("카카오", 20, duties))
    }
    private var mcompanies = ArrayList<Company>().apply {
        val duties = arrayListOf(Duty(0, 0, "프론트엔드"), Duty(1, 0, "UI/UX"), Duty(2, 0, "아무거나"),)
        add(Company("카카오 모빌리티", 20, duties))
        add(Company("우아한 형제들", 20, duties))
        add(Company("네이버", 20, duties))
        add(Company("당근마켓", 20, duties))
        add(Company("정승 네트워크", 20, duties))
    }

    fun bindViews(binding: FragmentCompanyBinding) {
        binding.apply {
            searchFocusListener = mSearchFocusChangeListener
            myJobGroups = mMyJobGroups
            myCompanies = mMyCompanies
            companies = mcompanies
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