package com.spectrum.spectrum.src.activities.login.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.button.MaterialButton
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.adapters.JobGroupAdapter
import com.spectrum.spectrum.src.config.Helpers
import com.spectrum.spectrum.src.models.JobGroup

class JobGroupDialog(context: Context): Dialog(context, R.style.AppTheme) {

    private var mItems = ArrayList<JobGroup>().apply {
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
        findViewById<RecyclerView>(R.id.job_group_recycler_view).apply {
            layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.right = Helpers.dp2px(4)
                    outRect.left = Helpers.dp2px(4)
                    outRect.top = Helpers.dp2px(4)
                    outRect.bottom = Helpers.dp2px(4)
                }
            })
            adapter = JobGroupAdapter(mItems)
        }
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

}