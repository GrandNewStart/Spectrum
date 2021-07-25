package com.spectrum.spectrum.src.activities.main.fragments.post

import android.app.AlertDialog
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.post.interfaces.PostApi
import com.spectrum.spectrum.src.activities.main.fragments.post.models.Education
import com.spectrum.spectrum.src.activities.main.fragments.post.models.Experience
import com.spectrum.spectrum.src.activities.main.fragments.post.models.License
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.dialogs.OptionDialog
import com.spectrum.spectrum.src.models.Evaluation
import com.spectrum.spectrum.src.models.Option
import com.spectrum.spectrum.src.models.RefreshEvent
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PostViewModel: ViewModel() {

    // Api
    private val mService = retrofit.create(PostApi::class.java)

    // Data
    var mPostId: Int? = null
    var mIsDataLoaded = false
    private var mShouldRefresh = false
    private var mIsMine = false
    private var mUserName: String? = null
    private var mUserThumbnail: String? = null
    private var mDate: String? = null
    private var mTitle: String? = null
    private var mContent: String? = null
    private var mEducations = ArrayList<Education>()
    private var mExperiences = ArrayList<Experience>()
    private var mLicenses = ArrayList<License>()
    private var mOtherSpecs: String? = null
    private var mUserInfo = ArrayList<String>()

    private var mTopFiveResponses = ArrayList<Evaluation>()
    private var mEduResponses = ArrayList<Evaluation>()
    private var mExpResponses = ArrayList<Evaluation>()
    private var mCertResponses = ArrayList<Evaluation>()
    private var mOtherResponses = ArrayList<Evaluation>()

    var mEduResponse: Evaluation? = null
    var mExpResponse: Evaluation? = null
    var mCertResponse: Evaluation? = null
    var mOtherResponse: Evaluation? = null

    init {
        EventBus.getDefault().register(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.getDefault().unregister(this)
    }

    fun bindViews(fragment: PostFragment) {
        if (mShouldRefresh) {
            mIsDataLoaded = true
            mPostId?.let { getPost(it, fragment) }
            return
        }

        if (mIsDataLoaded) {
            fragment.mBinding.apply {
                isMine = mIsMine
                userName = mUserName
                userThumbnail = mUserThumbnail
                date = mDate
                userInfo = mUserInfo
                postTitle = mTitle
                postContent = mContent

                educations = mEducations
                experiences = mExperiences
                licenses = mLicenses
                otherSpecs = mOtherSpecs

                topFiveResponses = mTopFiveResponses
                educationResponses = mEduResponses
                experienceResponses = mExpResponses
                certificationResponses = mCertResponses
                otherSpecsResponses = mOtherResponses
            }
            return
        }

        mIsDataLoaded = true
        mPostId?.let { getPost(it, fragment) }
    }

    fun backButtonAction(fragment: PostFragment) {
        fragment.findNavController().popBackStack()
    }

    fun trailingButtonAction(fragment: PostFragment) {
        if (mIsMine) {
            val options = ArrayList<Option>().apply {
                add(Option(Constants.edit, R.drawable.icon_edit))
                add(Option(Constants.delete, R.drawable.icon_trash))
            }
            OptionDialog(fragment.parentFragmentManager)
                .setOptions(options)
                .setOnItemClickListener { position ->
                    if (position == 0) { proceedToEditPost(fragment) }
                    if (position == 1) { showDeleteDialog(fragment) }
                }
                .showDialog()
        }
        else {
            fragment.findNavController().navigate(R.id.post_to_edit_post, bundleOf("id" to mPostId))
        }
    }

    private fun showDeleteDialog(fragment: PostFragment) {
        AlertDialog.Builder(fragment.requireContext())
            .setMessage(Constants.ask_delete)
            .setPositiveButton(Constants.confirm) { _,_ -> deletePost(fragment) }
            .setNegativeButton(Constants.cancel) { _,_ -> }
            .create()
            .show()
    }

    fun leaveResponseAction(fragment: PostFragment) {
        Log.d(TAG, "--- LEAVE MY RESPONSE ---")
        Log.d(TAG, "    EDU: ${mEduResponse?.name}")
        Log.d(TAG, "    EXP: ${mExpResponse?.name}")
        Log.d(TAG, "    CERT: ${mCertResponse?.name}")
        Log.d(TAG, "    OTHER: ${mOtherResponse?.name}")
    }

    private fun getPost(id: Int, fragment: PostFragment) {
        fragment.showProgressDialog(true)
        viewModelScope.launch {
            mService.getPost(id).apply {
                fragment.showProgressDialog(false)
                if (isSuccess) {
                    result.apply {
                        mIsMine = mine ?: false
                        mTitle = title
                        mContent = content
                        mUserName = username
                        mDate = createdAt
                        mUserInfo.clear()
                        jobStatus?.let { status -> status.id?.let { mUserInfo.add(it) } }
                        spec?.age.let { mUserInfo.add("${it}세") }
                        spec?.sex?.let { mUserInfo.add(it) }
                        spec?.jobGroups?.forEach { group -> group.name?.let { mUserInfo.add(it) } }
                        mEducations = spec?.educations ?: arrayListOf()
                        mExperiences = spec?.experiences ?: arrayListOf()
                        mLicenses = spec?.licenses ?: arrayListOf()
                        spec?.otherSpecs?.let { mOtherSpecs = it[0].content }
                    }
                    fragment.mBinding.apply {
                        isMine = mIsMine
                        postTitle = mTitle
                        postContent = mContent
                        userName = mUserName
                        date = mDate
                        userInfo = mUserInfo
                        educations = mEducations
                        experiences = mExperiences
                        licenses = mLicenses
                        otherSpecs = mOtherSpecs
                    }
                    Log.d(TAG, "---> POST FETCH SUCCESS")
                    return@launch
                }
                Log.e(TAG, "---> POST FETCH FAILURE: $message")
                fragment.showToast(Constants.request_failed)
                fragment.showProgressDialog(false)
            }
        }
    }

    private fun deletePost(fragment: PostFragment) {
        val id = mPostId ?: return
        viewModelScope.launch {
            mService.deleteMyPosts(id).apply {
                if (isSuccess) {
                    fragment.showToast(Constants.post_deleted)
                    fragment.findNavController().popBackStack()
                    return@launch
                }
                Log.e(TAG, "---> DELETE POST FAILURE: $message")
                fragment.showToast(Constants.request_failed)
            }
        }
    }

    private fun proceedToEditPost(fragment: PostFragment) {
        fragment.findNavController().navigate(R.id.post_to_edit_post, bundleOf("id" to mPostId))
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveRefreshEvent(event: RefreshEvent) {
        mShouldRefresh = event.refresh
    }

    companion object {
        val TAG = PostViewModel::class.java.simpleName.toString()
    }

}