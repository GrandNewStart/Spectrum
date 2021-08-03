package com.spectrum.spectrum.src.activities.main.fragments.post

import android.app.AlertDialog
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.post.interfaces.PostApi
import com.spectrum.spectrum.src.activities.main.fragments.post.models.Comment
import com.spectrum.spectrum.src.activities.main.fragments.post.models.Education
import com.spectrum.spectrum.src.activities.main.fragments.post.models.Experience
import com.spectrum.spectrum.src.activities.main.fragments.post.models.License
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.dialogs.OptionDialog
import com.spectrum.spectrum.src.models.Option
import com.spectrum.spectrum.src.models.RefreshEvent
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray
import org.json.JSONObject

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

    private var mTopFiveComments = ArrayList<Comment>()
    var mEduComments = ArrayList<Comment>()
    var mExpComments = ArrayList<Comment>()
    var mLicComments = ArrayList<Comment>()
    var mOtherComments = ArrayList<Comment>()
    var mMyComments = ArrayList<Int>()

    var mEduComment = MutableLiveData<Comment?>(null)
    var mExpComment = MutableLiveData<Comment?>(null)
    var mLicComment = MutableLiveData<Comment?>(null)
    var mOtherComment = MutableLiveData<Comment?>(null)

    init {
        EventBus.getDefault().register(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.getDefault().unregister(this)
    }

    fun bindViews(fragment: PostFragment) {
        if (mShouldRefresh) {
            refresh(fragment)
            return
        }

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

            topFiveComments = mTopFiveComments
            bindCommentChips(fragment)
        }

        if (!mIsDataLoaded) {
            mIsDataLoaded = true
            getPost(fragment)
            getComments(fragment)
        }
    }

    private fun bindCommentChips(fragment: PostFragment) {
        mMyComments.forEach {
            for (i in 0 until mEduComments.size) {
                if (mEduComments[i].id == it) {
                    mEduComments[i].isSelected = true
                }
            }
            for (i in 0 until mExpComments.size) {
                if (mExpComments[i].id == it) {
                    mExpComments[i].isSelected = true
                }
            }
            for (i in 0 until mLicComments.size) {
                if (mLicComments[i].id == it) {
                    mLicComments[i].isSelected = true
                }
            }
            for (i in 0 until mOtherComments.size) {
                if (mOtherComments[i].id == it) {
                    mOtherComments[i].isSelected = true
                }
            }
        }
        val nonZeroEdu = ArrayList<Comment>()
        val zeroEdu = ArrayList<Comment>()
        mEduComments.forEach {
            if (it.count == 0) zeroEdu.add(it)
            else nonZeroEdu.add(it)
        }
        val nonZeroExp = ArrayList<Comment>()
        val zeroExp = ArrayList<Comment>()
        mExpComments.forEach {
            if (it.count == 0) zeroExp.add(it)
            else nonZeroExp.add(it)
        }
        val nonZeroLic = ArrayList<Comment>()
        val zeroLic = ArrayList<Comment>()
        mLicComments.forEach {
            if (it.count == 0) zeroLic.add(it)
            else nonZeroLic.add(it)
        }
        val nonZeroOthers = ArrayList<Comment>()
        val zeroOthers = ArrayList<Comment>()
        mOtherComments.forEach {
            if (it.count == 0) zeroOthers.add(it)
            else nonZeroOthers.add(it)
        }
        fragment.mBinding.apply {
            nonZeroEducationComments = nonZeroEdu
            zeroEducationComments = zeroEdu
            nonZeroExperienceComments = nonZeroExp
            zeroExperienceComments = zeroExp
            nonZeroLicenseComments = nonZeroLic
            zeroLicenseComments = zeroLic
            nonZeroOtherSpecsComments = nonZeroOthers
            zeroOtherSpecsComments = zeroOthers
        }
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
            fragment.showToast(Constants.under_construction)
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
        Log.d(TAG, "    EDU: ${mEduComment.value?.name}")
        Log.d(TAG, "    EXP: ${mExpComment.value?.name}")
        Log.d(TAG, "    CERT: ${mLicComment.value?.name}")
        Log.d(TAG, "    OTHER: ${mOtherComment.value?.name}")
        val list = ArrayList<Int>().apply {
            mEduComment.value?.let { add(it.id) }
            mExpComment.value?.let { add(it.id) }
            mLicComment.value?.let { add(it.id) }
            mOtherComment.value?.let { add(it.id) }
        }
        postComments(fragment, list)
    }

    fun checkForCommentChanges(): Boolean {
        val list = ArrayList<Int>().apply {
            mEduComment.value?.let { add(it.id) }
            mExpComment.value?.let { add(it.id) }
            mLicComment.value?.let { add(it.id) }
            mOtherComment.value?.let { add(it.id) }
        }
        if (list.size != mMyComments.size) return true
        mMyComments.forEach { before ->
            var changed = true
            list.forEach { after ->
                if (before == after) changed = false
            }
            if (changed) return true
        }
        return false
    }

    private fun refresh(fragment: PostFragment) {
        mIsDataLoaded = true

        mEduComment.value = null
        mExpComment.value= null
        mLicComment.value = null
        mOtherComment.value = null

        getPost(fragment)
        getComments(fragment)
    }

    private fun getPost(fragment: PostFragment) {
        val id = mPostId ?: return
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
                    EventBus.getDefault().post(RefreshEvent(true))
                    return@launch
                }
                Log.e(TAG, "---> DELETE POST FAILURE: $message")
                fragment.showToast(Constants.request_failed)
            }
        }
    }

    private fun getComments(fragment: PostFragment) {
        val id = mPostId ?: return
        viewModelScope.launch {
            mService.getComments(id).apply {
                if (isSuccess) {
                    mTopFiveComments    = result.topComments
                    mEduComments        = result.eduComments
                    mExpComments        = result.expComments
                    mLicComments        = result.licComments
                    mOtherComments      = result.etcComments
                    mMyComments         = result.myComments

                    mMyComments.forEach { id ->
                        mEduComments.forEach { if (it.id == id) { mEduComment.value = it } }
                        mExpComments.forEach { if (it.id == id) { mExpComment.value = it } }
                        mLicComments.forEach { if (it.id == id) { mLicComment.value = it } }
                        mOtherComments.forEach { if (it.id == id) { mOtherComment.value = it } }
                    }

                    fragment.mBinding.postResponsesButton.text = if (mMyComments.isEmpty()) Constants.leave_comment else Constants.edit_comment
                    fragment.mBinding.topFiveComments = mTopFiveComments
                    bindCommentChips(fragment)
                    return@launch
                }
            }
        }
    }

    private fun postComments(fragment: PostFragment, selections: ArrayList<Int>) {
        val id = mPostId ?: return
        val array = JSONArray().apply {
            selections.forEach { put(it) }
        }
        val json = JSONObject().apply {
            put("addCommentList", array)
        }
        val body = json.toString().toRequestBody(Constants.MEDIA_TYPE_JSON.toMediaType())
        viewModelScope.launch {
            fragment.showProgressDialog(true)
            mService.postComment(id, body).apply {
                fragment.showProgressDialog(false)
                if (isSuccess) {
                    refresh(fragment)
                    fragment.showToast(Constants.comment_uploaded)
                    fragment.mBinding.postResponsesButton.visibility = View.GONE
                    return@launch
                }
                Log.e(TAG, "---> POST COMMENT FAILURE: $message")
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