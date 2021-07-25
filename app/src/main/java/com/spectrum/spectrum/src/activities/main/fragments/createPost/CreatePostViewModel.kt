package com.spectrum.spectrum.src.activities.main.fragments.createPost

import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.createPost.interfaces.CreatePostApi
import com.spectrum.spectrum.src.activities.main.fragments.createPost.models.Spec
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Constants.MEDIA_TYPE_JSON
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.models.PostEvent
import com.spectrum.spectrum.src.models.RefreshEvent
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import java.lang.Exception

class CreatePostViewModel: ViewModel() {

    // Api
    private val mService = retrofit.create(CreatePostApi::class.java)

    // Data
    var mIsDataLoaded = false
    var mShouldRefresh = false
    private var mMySpec: Spec? = null

    init {
        EventBus.getDefault().register(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.getDefault().unregister(this)
    }

    fun bindViews(fragment: CreatePostFragment) {
        fragment.mBinding.apply {
            spec = mMySpec
        }
        if (mShouldRefresh) {
            mShouldRefresh = false
            refresh(fragment)
        }
        if (!mIsDataLoaded) {
            mIsDataLoaded = true
            getMySpec(fragment)
        }
    }

    fun backButtonAction(fragment: CreatePostFragment) {
        fragment.findNavController().popBackStack()
    }

    fun doneButtonAction(fragment: CreatePostFragment) {
        fragment.mBinding.apply {
            val title = titleEditText.text.toString().trim()
            if (title.isEmpty()) {
                fragment.showToast(Constants.please_enter_title)
                return
            }

            val content = contentEditText.text.toString().trim()
            if (content.isEmpty()) {
                fragment.showToast(Constants.please_enter_content)
                return
            }

            var jobStatus: String? = null
            when (typeSpinner.selectedItemPosition) {
                0 -> {
                    fragment.showToast(Constants.please_select_spec_sort)
                    return
                }
                1 -> { jobStatus = "R" }
                2 -> { jobStatus = "N" }
                3 -> { jobStatus = "F" }
            }

            var category: String? = null
            when (subTypeSpinner.selectedItemPosition) {
                0 -> {
                    fragment.showToast(Constants.please_select_category)
                    return
                }
                1 -> { category = "Q" }
                2 -> { category = "R" }
            }
            if (jobStatus == null) return
            if (category == null) return
            uploadPost(title, content, jobStatus, category, fragment)
        }
    }

    fun proceedToEditSpec(fragment: CreatePostFragment) {
        fragment.findNavController().navigate(R.id.create_post_to_edit_spec)
    }

    fun proceedToCreateSpec(fragment: CreatePostFragment) {
        fragment.findNavController().navigate(R.id.create_post_to_create_spec)
    }

    private fun refresh(fragment: CreatePostFragment) {
        mMySpec = null
        getMySpec(fragment)
    }

    private fun getMySpec(fragment: CreatePostFragment) {
        try {
            fragment.showProgressDialog(true)
            viewModelScope.launch {
                mService.getMySpec().apply {
                    fragment.showProgressDialog(false)
                    if (isSuccess) {
                        mMySpec = result
                        fragment.mBinding.spec = mMySpec
                    }
                }
            }
        }
        catch (e: Exception) {
            Log.e(TAG, "---> MY SPEC FETCH FAILURE: $e")
            fragment.showProgressDialog(false)
            fragment.showToast(Constants.request_failed)
        }
    }

    private fun uploadPost(title: String, content: String, jobStatus:String, category: String, fragment: CreatePostFragment) {
        val json = JSONObject().apply {
            put("title", title)
            put("content", content)
            put("jobStatus", jobStatus)
            put("category", category)
        }
        Log.d(TAG, "---> BODY: $json")
        val body = json.toString().toRequestBody(MEDIA_TYPE_JSON.toMediaType())
        fragment.showProgressDialog(true)
        viewModelScope.launch {
            mService.uploadPost(body).apply {
                fragment.showProgressDialog(false)
                if (isSuccess) {
                    val id = result.postId
                    fragment.showToast(Constants.post_uploaded)
                    fragment.findNavController().popBackStack(R.id.home_fragment, false)
                    EventBus.getDefault().post(PostEvent(id))
                    Log.d(TAG, "---> POST UPLOAD SUCCESS: $id")
                    return@launch
                }
                fragment.showToast(Constants.request_failed)
                Log.e(TAG, "---> POST UPLOAD FAILURE: $message")
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveRefreshEvent(event: RefreshEvent) {
        mShouldRefresh = event.refresh
    }

    companion object {
        val TAG = CreatePostViewModel::class.java.simpleName.toString()
    }

}