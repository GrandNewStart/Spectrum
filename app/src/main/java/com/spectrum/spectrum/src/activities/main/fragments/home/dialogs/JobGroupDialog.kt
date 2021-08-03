package com.spectrum.spectrum.src.activities.main.fragments.home.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.models.JobGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.GET
import java.lang.Exception

class JobGroupDialog(context: Context): Dialog(context, R.style.AppTheme) {

    private var mItems = ArrayList<JobGroup>()
    private var mOnSaveListener: (first: JobGroup?, second: JobGroup?)->Unit = { _,_->}
    private var mFirstItem: JobGroup? = null
    private var mSecondItem: JobGroup? = null
    private lateinit var mChipGroup: ChipGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_home_job_group)
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
        mChipGroup = findViewById(R.id.chip_group)
        findViewById<MaterialButton>(R.id.back_button).setOnClickListener { dismiss() }
        findViewById<TextView>(R.id.save_text).setOnClickListener { saveButtonAction() }
        getJobGroups()
    }

    fun saveButtonAction() {
        mOnSaveListener(mFirstItem, mSecondItem)
        dismiss()
    }

    fun setOnSaveListener(onSaveListener: (first: JobGroup?, second: JobGroup?)->Unit): JobGroupDialog {
        this.mOnSaveListener = onSaveListener
        return this
    }

    fun setPreSelectedItems(first: JobGroup?, second: JobGroup?): JobGroupDialog {
        mFirstItem = first 
        mSecondItem = second
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
                        initChipGroup(mChipGroup)
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

    private fun initChipGroup(chipGroup: ChipGroup) {
        chipGroup.apply {
            mItems.forEach { group ->
                Chip(context).apply {
                    text = group.name
                    setEnsureMinTouchTargetSize(false)
                    setChipBackgroundColorResource(R.color.clear)
                    setChipStrokeColorResource(R.color.spectrumSilver3)
                    setChipStrokeWidthResource(R.dimen.thin_stroke_width)
                    setTextAppearance(R.style.ChipTextBig)
                    chipMinHeight = dp2px(28).toFloat()
                    setOnClickListener {
                        when (group.id) {
                            mFirstItem?.id -> {
                                if (mSecondItem == null) {
                                    mFirstItem = null
                                    bindChipGroup(chipGroup)
                                    return@setOnClickListener
                                }
                                mFirstItem = mSecondItem
                                mSecondItem = null
                                bindChipGroup(chipGroup)
                                return@setOnClickListener
                            }
                            mSecondItem?.id -> {
                                mSecondItem = null
                                bindChipGroup(chipGroup)
                                return@setOnClickListener
                            }
                            else -> {
                                if (mFirstItem == null) {
                                    mFirstItem = group
                                    bindChipGroup(chipGroup)
                                    return@setOnClickListener
                                }
                                if (mSecondItem == null) {
                                    mSecondItem = group
                                    bindChipGroup(chipGroup)
                                    return@setOnClickListener
                                }
                            }
                        }
                    }
                    addView(this)
                }
            }
            bindChipGroup(this)
        }
    }

    private fun bindChipGroup(chipGroup: ChipGroup) {
        chipGroup.apply {
            for (i in 0 until mItems.size) {
                val item = mItems[i]
                val chip = getChildAt(i) as Chip
                when (item.id) {
                    mFirstItem?.id -> {
                        chip.setChipBackgroundColorResource(R.color.spectrumBlue)
                        chip.setTextColor(resources.getColor(R.color.white, null))
                        chip.chipStrokeWidth = 0f
                    }
                    mSecondItem?.id -> {
                        chip.setChipBackgroundColorResource(R.color.spectrumGreen)
                        chip.setTextColor(resources.getColor(R.color.white, null))
                        chip.chipStrokeWidth = 0f
                    }
                    else -> {
                        chip.setChipBackgroundColorResource(R.color.clear)
                        chip.setTextColor(resources.getColor(R.color.spectrumGray1, null))
                        chip.setChipStrokeWidthResource(R.dimen.default_stroke_width)
                    }
                }
            }
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