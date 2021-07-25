package com.spectrum.spectrum.src.activities.main.fragments.myPage

import androidx.lifecycle.ViewModel
import com.spectrum.spectrum.src.activities.main.fragments.myPage.dialogs.EditProfileDialog
import com.spectrum.spectrum.src.activities.main.fragments.myPage.dialogs.MySpecDialog
import com.spectrum.spectrum.src.config.Constants

class MyPageViewModel: ViewModel() {

    fun showEditProfileDialog(fragment: MyPageFragment) {
        EditProfileDialog(fragment.requireContext()).show()
    }

    fun showMySpecDialog(fragment: MyPageFragment) {
        MySpecDialog(fragment).show()
    }

    fun showMyPostsDialog(fragment: MyPageFragment) {
        fragment.showToast(Constants.under_construction)
    }

    fun showMyEvaluationsDialog(fragment: MyPageFragment) {
        fragment.showToast(Constants.under_construction)
    }

    fun showMyScrapsDialog(fragment: MyPageFragment) {
        fragment.showToast(Constants.under_construction)
    }

    fun showMyCompanies(fragment: MyPageFragment) {
        fragment.showToast(Constants.under_construction)
    }

    fun proceedToAppConfiguration(fragment: MyPageFragment) {
        fragment.showToast(Constants.under_construction)
    }

}