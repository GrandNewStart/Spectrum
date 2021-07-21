package com.spectrum.spectrum.src.activities.login.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.adapters.JobGroupAdapter
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers
import com.spectrum.spectrum.src.models.JobGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.GET
import java.lang.Exception

class JobGroupDialog(context: Context): Dialog(context, R.style.AppTheme) {

    private var mItems = ArrayList<JobGroup>()
    private var mOnSaveListener: (first: JobGroup?, second: JobGroup?, third: JobGroup?)->Unit = {_,_,_->}
    private var mFirstItem: JobGroup? = null
    private var mSecondItem: JobGroup? = null
    private var mThirdItem: JobGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_log_in_job_group)
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
        findViewById<MaterialButton>(R.id.back_button).setOnClickListener { dismiss() }
        findViewById<TextView>(R.id.save_text).setOnClickListener { saveButtonAction() }
        getJobGroups()
    }

    fun saveButtonAction() {
        findViewById<RecyclerView>(R.id.job_group_recycler_view)?.apply {
            mFirstItem = (adapter as JobGroupAdapter).mFirstItem
            mSecondItem = (adapter as JobGroupAdapter).mSecondItem
            mThirdItem = (adapter as JobGroupAdapter).mThirdItem
            mOnSaveListener(mFirstItem, mSecondItem, mThirdItem)
            dismiss()
        }
    }

    fun setOnSaveListener(onSaveListener: (first: JobGroup?, second: JobGroup?, third: JobGroup?)->Unit): JobGroupDialog {
        this.mOnSaveListener = onSaveListener
        return this
    }

    fun setPreSelectedItems(first: JobGroup?, second: JobGroup?, third: JobGroup?): JobGroupDialog {
        for (i in 0 until mItems.size) {
            first?.let {
                if (it.id == mItems[i].id) {
                    mItems[i].selectIndex = 1
                }
            }
            second?.let {
                if (it.id == mItems[i].id) {
                    mItems[i].selectIndex = 2
                }
            }
            third?.let {
                if (it.id == mItems[i].id) {
                    mItems[i].selectIndex = 3
                }
            }
        }
        findViewById<RecyclerView>(R.id.job_group_recycler_view)?.adapter?.notifyDataSetChanged()
        return this
    }

    private fun getJobGroups() {
        try {
            CoroutineScope(Dispatchers.Main).launch {
                Helpers.retrofit.create(JobGroupApi::class.java).getJobGroups().apply {
                    if (isSuccess) {
                        Log.d(TAG, "---> JOB GROUP FETCH SUCCESS")
                        mItems = result.jobGroupList
                        mItems.removeAt(0)
                        findViewById<RecyclerView>(R.id.job_group_recycler_view).apply {
                            adapter = JobGroupAdapter(mItems)
                        }
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