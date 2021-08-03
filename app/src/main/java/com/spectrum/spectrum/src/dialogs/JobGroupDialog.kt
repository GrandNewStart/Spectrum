package com.spectrum.spectrum.src.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.DialogJobGroupBinding
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.retrofit
import com.spectrum.spectrum.src.models.JobGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.GET
import java.lang.Exception

class JobGroupDialog(context: Context): Dialog(context, R.style.AppTheme) {

    private lateinit var mBinding: DialogJobGroupBinding
    private var mItems = ArrayList<JobGroup>()
    private var onSaveListener: (item: JobGroup?)->Unit = {_->}
    var mSelectedItem: JobGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_job_group, null, false)
        mBinding.apply {
            dialog = this@JobGroupDialog
            groups = mItems
        }
        setCanceledOnTouchOutside(false)
        window?.apply {
            setBackgroundDrawable(ColorDrawable())
            setDimAmount(0.0f)
            attributes.let { params ->
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = WindowManager.LayoutParams.MATCH_PARENT
                params.windowAnimations = R.style.DialogSlideAnimation
            }
            setGravity(Gravity.END)
        }
        setContentView(mBinding.root)
        getJobGroups()
    }

    fun saveButtonAction() {
        onSaveListener(mSelectedItem)
        dismiss()
    }

    fun setOnSaveListener(onSaveListener: (item: JobGroup?)->Unit): JobGroupDialog {
        this.onSaveListener = onSaveListener
        return this
    }

    fun setPreselectedItem(item: JobGroup?): JobGroupDialog {
        mSelectedItem = item
        return this
    }

    private fun getJobGroups() {
        try {
            CoroutineScope(Dispatchers.Default).launch {
                retrofit.create(JobGroupApi::class.java).getJobGroups().apply {
                    if (isSuccess) {
                        Log.d(TAG, "---> JOB GROUP FETCH SUCCESS")
                        mItems = result.jobGroupList
                        mItems.removeAt(0)
                        mBinding.groups = mItems
                        return@launch
                    }
                    Log.e(TAG, "---> JOB GROUP FETCH FAILURE: $message")
                    Toast.makeText(context, Constants.request_failed, Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
        }
        catch (e: Exception) {
            Log.e(TAG, "---> JOB GROUP FETCH FAILURE: $e")
            Toast.makeText(context, Constants.request_failed, Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    companion object {
        val TAG = JobGroupDialog::class.java.simpleName.toString()
    }

}

interface JobGroupApi {
    @GET("/app/datas/jobgroup")
    suspend fun getJobGroups(): JobGroupResponse
}

data class JobGroupResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: Result
) {
    data class Result(var jobGroupList: ArrayList<JobGroup>)
}