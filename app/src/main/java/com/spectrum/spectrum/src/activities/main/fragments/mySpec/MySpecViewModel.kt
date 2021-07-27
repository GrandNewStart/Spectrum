package com.spectrum.spectrum.src.activities.main.fragments.mySpec

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.mySpec.interfaces.MySpecApi
import com.spectrum.spectrum.src.activities.main.fragments.mySpec.models.Spec
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.dialogs.OptionDialog
import com.spectrum.spectrum.src.models.Option
import com.spectrum.spectrum.src.models.RefreshEvent
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MySpecViewModel: ViewModel() {

    private val mService = retrofit.create(MySpecApi::class.java)
    private var mIsDataLoaded = false
    private var mShouldReload = false
    private var mSpec: Spec? = null

    init {
        EventBus.getDefault().register(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.getDefault().unregister(this)
    }

    fun bindViews(fragment: MySpecFragment) {
        if (mShouldReload) {
            refresh(fragment)
            return
        }
        if (mIsDataLoaded) {
            fragment.mBinding.spec = mSpec
            return
        }
        getMySpec(fragment)
        mIsDataLoaded = true
    }

    fun refresh(fragment: MySpecFragment) {
        mShouldReload = false
        getMySpec(fragment)
    }

    fun backButtonAction(fragment: MySpecFragment) {
        fragment.findNavController().popBackStack()
    }

    fun menuButtonAction(fragment: MySpecFragment) {
        val options = ArrayList<Option>().apply {
            add(Option(Constants.edit, R.drawable.icon_edit))
            add(Option(Constants.delete, R.drawable.icon_trash))
        }
        OptionDialog(fragment.parentFragmentManager)
            .setOptions(options)
            .setOnItemClickListener { position ->
                if (position == 0) { proceedToEditSpec(fragment) }
                if (position == 1) { showDeleteSpecDialog(fragment) }
            }
            .showDialog()
    }

    private fun showDeleteSpecDialog(fragment: MySpecFragment) {
        AlertDialog.Builder(fragment.requireContext())
            .setMessage(Constants.ask_delete_spec)
            .setPositiveButton(Constants.yes) { _, _ -> deleteMySpec(fragment) }
            .create()
            .show()
    }

    fun proceedToEditSpec(fragment: MySpecFragment) {
        fragment.findNavController().navigate(R.id.my_spec_to_edit_spec)
    }

    fun proceedToCreateSpec(fragment: MySpecFragment) {
        fragment.findNavController().navigate(R.id.my_spec_to_create_spec)
    }

    private fun getMySpec(fragment: MySpecFragment) {
        viewModelScope.launch {
            mService.getMySpec().apply {
                if (isSuccess) {
                    Log.d(TAG, "---> MY SPEC FETCH SUCCESS")
                    mSpec = result
                    fragment.mBinding.spec = mSpec
                    return@launch
                }
                else {
                    fragment.mBinding.spec = null
                }
            }
        }
    }

    private fun deleteMySpec(fragment: MySpecFragment) {
        viewModelScope.launch {
            mService.deleteMySpec().apply {
                if (isSuccess) {
                    Log.e(TAG, "---> MY SPEC DELETE SUCCESS")
                    getMySpec(fragment)
                    return@launch
                }
                Log.e(TAG, "---> MY SPEC DELETE FAILURE: $message")
                fragment.showToast(Constants.request_failed)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveRefreshEvent(event: RefreshEvent) {
        mShouldReload = true
    }

    companion object {
        val TAG = MySpecViewModel::class.java.simpleName.toString()
    }

}