package com.spectrum.spectrum.src.activities.main.fragments.jobGroup.adapters

import android.graphics.Rect
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.spectrum.spectrum.src.config.Helpers
import com.spectrum.spectrum.src.models.JobGroup

object BindingAdapters {

    @BindingAdapter("job_group_job_group")
    @JvmStatic
    fun bindJobGroupRecyclerView(recyclerView: RecyclerView, items: ArrayList<JobGroup>?) {
        recyclerView.apply {
            if (layoutManager == null) {
                layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.right = Helpers.dp2px(5)
                        outRect.left = Helpers.dp2px(5)
                        outRect.top = Helpers.dp2px(5)
                        outRect.bottom = Helpers.dp2px(5)
                    }
                })
            }
            if (adapter == null) {
                adapter = JobGroupAdapter(items ?: ArrayList())
            }
            adapter?.notifyDataSetChanged()
        }
    }

}