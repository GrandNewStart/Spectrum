package com.spectrum.spectrum.src.activities.main.fragments.post.adapters

import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Helpers
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.models.Education

class EduItemAdapter(private val items: ArrayList<Education>): RecyclerView.Adapter<EduItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val schoolText: MaterialTextView = itemView.findViewById(R.id.school_text)
        val statusText: MaterialTextView = itemView.findViewById(R.id.status_text)
        val majorText: MaterialTextView = itemView.findViewById(R.id.major_text)
        val gradeText: MaterialTextView = itemView.findViewById(R.id.grades_text)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    val pos = parent.getChildLayoutPosition(view)
                    if (pos != 0) outRect.top += dp2px(4)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_post_edu_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.apply {
            schoolText.apply {
                var location = ""
                var degree = ""
                val school = item.schoolName ?: ""
                item.location?.let{ if (it.id != 0) location = it.data + " " }
                item.degree?.let{ if (it.id != 0) degree = it.data }
                val result = location + degree + school
                text = result
            }
            item.graduate?.apply {
                statusText.text = if (id == 0) "" else data
            }
            majorText.text = items[position].major
            gradeText.text = items[position].grade.toString()
        }
    }

    override fun getItemCount(): Int = items.size
}