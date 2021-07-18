package com.spectrum.spectrum.src.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.DialogJobGroupBinding
import com.spectrum.spectrum.src.models.JobGroup

class JobGroupDialog(context: Context): Dialog(context, R.style.AppTheme) {

    private lateinit var mBinding: DialogJobGroupBinding
    private var items = ArrayList<JobGroup>().apply {
        add(JobGroup(0, "경영/사무", 0))
        add(JobGroup(1, "영업/고객상담", 0))
        add(JobGroup(2, "IT/인터넷", 0))
        add(JobGroup(3, "디자인", 0))
        add(JobGroup(4, "서비스", 0))
        add(JobGroup(5, "의료", 0))
        add(JobGroup(6, "생산/제조", 0))
        add(JobGroup(7, "건설", 0))
        add(JobGroup(8, "유통/무역", 0))
        add(JobGroup(9, "미디어", 0))
        add(JobGroup(10, "교육", 0))
        add(JobGroup(11, "특수계층/공공", 0))
        add(JobGroup(12, "기타", 0))
    }
    private var onSaveListener: (first: JobGroup?, second: JobGroup?)->Unit = {_,_->}
    var mFirstItem: JobGroup? = null
    var mSecondItem: JobGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_job_group, null, false)
        mBinding.apply {
            dialog = this@JobGroupDialog
            groups = items
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
    }

    fun saveButtonAction() {
        onSaveListener(mFirstItem, mSecondItem)
        dismiss()
    }

    fun setOnSaveListener(onSaveListener: (first: JobGroup?, second: JobGroup?)->Unit): JobGroupDialog {
        this.onSaveListener = onSaveListener
        return this
    }

}