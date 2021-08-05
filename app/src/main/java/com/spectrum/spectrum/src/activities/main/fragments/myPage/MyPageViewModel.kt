package com.spectrum.spectrum.src.activities.main.fragments.myPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.myPage.dialogs.EditProfileDialog
import com.spectrum.spectrum.src.activities.main.fragments.myPage.interfaces.MyPageApi
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.retrofit
import kotlinx.coroutines.launch

class MyPageViewModel: ViewModel() {

    private val mService = retrofit.create(MyPageApi::class.java)

    fun showEditProfileDialog(fragment: MyPageFragment) {
        EditProfileDialog(fragment.requireContext()).show()
    }

    fun proceedToMySpec(fragment: MyPageFragment) {
        fragment.findNavController().navigate(R.id.my_page_to_my_spec)
    }

    fun proceedToMyPost(fragment: MyPageFragment) {
        fragment.findNavController().navigate(R.id.my_page_to_my_post)
    }

    fun proceedToMyEvaluation(fragment: MyPageFragment) {
        fragment.findNavController().navigate(R.id.my_page_to_my_evaluation)
    }

    fun proceedToMyScrap(fragment: MyPageFragment) {
        fragment.findNavController().navigate(R.id.my_page_to_my_scrap)
    }

    fun proceedToMyCompany(fragment: MyPageFragment) {
        fragment.findNavController().navigate(R.id.my_page_to_my_company)
    }

    fun proceedToAppConfiguration(fragment: MyPageFragment) {
        fragment.findNavController().navigate(R.id.my_page_to_settings)
    }

    fun getUserInfo(fragment: MyPageFragment) {
        viewModelScope.launch {
            mService.getMyInfo().apply {
                if (isSuccess) {
                    fragment.mBinding.apply {
                        myNameButton.text = result.name
                        myEmailText.text = result.email
                    }
                }
                else {

                }
            }
        }
    }

}