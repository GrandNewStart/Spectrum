package com.spectrum.spectrum.src.activities.main.fragments.editpost

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.editpost.interfaces.EditPostApi
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Constants.MEDIA_TYPE_JSON
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.models.RefreshEvent
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

class EditPostViewModel: ViewModel() {

    private val mService = retrofit.create(EditPostApi::class.java)

    var mIsDataLoaded = false
    var mPostId: Int? = null

    fun bindViews(fragment: EditPostFragment) {
        if (mIsDataLoaded) {
            return
        }
        getMySpec(fragment)
        getMyPost(fragment)
    }

    fun backButtonAction(fragment: EditPostFragment) {
        fragment.findNavController().popBackStack()
    }

    fun doneButtonAction(fragment: EditPostFragment) {
        val title = fragment.mBinding.titleEditText.text.toString().trim()
        if (title.isEmpty()) {
            fragment.showToast(Constants.please_enter_title)
            return
        }
        val content = fragment.mBinding.contentEditText.text.toString().trim()
        if (content.isEmpty()) {
            fragment.showToast(Constants.please_enter_content)
            return
        }
        val spec = fragment.mBinding.spec
        if (spec == null) {
            fragment.showToast(Constants.please_register_spec)
            return
        }
        editMyPost(fragment)
    }

    fun proceedToEditSpec(fragment: EditPostFragment) {
        fragment.findNavController().navigate(R.id.edit_post_to_edit_spec)
    }

    fun proceedToCreateSpec(fragment: EditPostFragment) {
        fragment.findNavController().navigate(R.id.edit_post_to_create_spec)
    }

    private fun getMySpec(fragment: EditPostFragment) {
        viewModelScope.launch {
            mService.getMySpec().apply{
                if (isSuccess) {
                    fragment.mBinding.spec = result
                    return@launch
                }
                fragment.mBinding.spec = null
            }
        }
    }

    private fun getMyPost(fragment: EditPostFragment) {
        val id = mPostId ?: return
        viewModelScope.launch {
            mService.getMyPost(id).apply{
                if (isSuccess) {
                    fragment.mBinding.post = result
                    return@launch
                }
                Log.e(TAG, "---> POST FETCH FAILURE: $message")
                fragment.showToast(Constants.request_failed)
            }
        }
    }

    private fun editMyPost(fragment: EditPostFragment) {
        val id = mPostId ?: return

        val title = fragment.mBinding.titleEditText.text.toString().trim()
        val content = fragment.mBinding.contentEditText.text.toString().trim()
        var jobStatus: String? = null
        when (fragment.mBinding.typeSpinner.selectedItemPosition) {
            0-> { jobStatus = "R" } // 취업준비
            1-> { jobStatus = "N" } // n차합격
            2-> { jobStatus = "F" } // 최종합껴
        }
        var category: String? = null
        when (fragment.mBinding.subTypeSpinner.selectedItemPosition) {
            0-> {
                if (jobStatus == "R") category = "Q" // 자유고민
                if (jobStatus == "N") category = "Q" // 자유고민
                if (jobStatus == "F") category = "R" // 자유후기
            }
            1-> {
                if (jobStatus == "N") category = "R" // 자유후기
            }
        }
        val json = JSONObject().apply {
            put("title", title)
            put("content", content)
            put("jobStatus", jobStatus)
            put("category", category)
        }
        Log.d(TAG,"---> BODY: $json")
        val body = json.toString().toRequestBody(MEDIA_TYPE_JSON.toMediaType())

        viewModelScope.launch {
            mService.editMyPost(id, body).apply {
                if (isSuccess) {
                    fragment.showToast(Constants.post_edited)
                    fragment.findNavController().popBackStack()
                    EventBus.getDefault().post(RefreshEvent(true))
                    return@launch
                }
                Log.e(TAG, "---> POST EDIT FAILURE: $message")
                fragment.showToast(Constants.request_failed)
            }
        }
    }

    companion object {
        val TAG = EditPostViewModel::class.java.simpleName.toString()
    }

}