package com.spectrum.spectrum.src.activities.login.fragments

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.LogInActivity
import com.spectrum.spectrum.src.activities.login.adapters.JobGroupAdapter
import com.spectrum.spectrum.src.activities.login.models.JobGroup
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.customs.BaseFragment

class JobGroupFragment: BaseFragment() {

    private val mList = ArrayList<JobGroup>().apply {
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
    var mFirstItem: JobGroup? = null
    var mSecondItem: JobGroup? = null
    var mThirdItem: JobGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as LogInActivity).apply {
            for (item in mList) {
                mJobGroup1?.let {
                    if (it.id == item.id) {
                        item.selectIndex = 1
                        mFirstItem = item
                    }
                }
                mJobGroup2?.let {
                    if (it.id == item.id) {
                        item.selectIndex = 2
                        mSecondItem = item
                    }
                }
                mJobGroup3?.let {
                    if (it.id == item.id) {
                        item.selectIndex = 3
                        mThirdItem = item
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in_job_group, container, false)
        view.apply {
            findViewById<MaterialButton>(R.id.back).setOnClickListener {
                findNavController().popBackStack()
            }
            findViewById<RecyclerView>(R.id.groups).apply {
                layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.right = dp2px(5)
                        outRect.left = dp2px(5)
                        outRect.top = dp2px(5)
                        outRect.bottom = dp2px(5)
                    }
                })
                adapter = JobGroupAdapter(mList)
            }
            findViewById<MaterialTextView>(R.id.save).setOnClickListener {
                (activity as LogInActivity).mJobGroup1 = mFirstItem
                (activity as LogInActivity).mJobGroup2 = mSecondItem
                (activity as LogInActivity).mJobGroup3 = mThirdItem
                findNavController().popBackStack()
            }
        }
        return view
    }

}