package com.spectrum.spectrum.src.adapters

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Helpers
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.models.Education

class EducationAdapter(private val items: ObservableArrayList<Education>): RecyclerView.Adapter<EducationAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val school: MaterialTextView = itemView.findViewById(R.id.school_text)
        val graduate: MaterialTextView = itemView.findViewById(R.id.status_text)
        val major: MaterialTextView = itemView.findViewById(R.id.major_text)
        val grade: MaterialTextView = itemView.findViewById(R.id.grades_text)
        val delete: MaterialTextView = itemView.findViewById(R.id.delete_text)
    }

    private var mRecyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.top = dp2px(2)
                    outRect.bottom = dp2px(2)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_education, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.apply {
            school.apply {
                var location = ""
                var degree = ""
                val school = item.schoolName ?: ""
                item.location?.let{ if (it.id != 0) location = it.data + " " }
                item.degree?.let{ if (it.id != 0) { it.data?.let { degree = "$it " } } }
                val result = location + degree + school
                text = result
            }
            graduate.apply {
                item.graduate?.apply {
                    if (id != 0) { text = data }
                }
            }
            major.apply {
                visibility = if (item.major == null) View.GONE else View.VISIBLE
                text = item.major
            }
            grade.apply {
                visibility = if (item.grade == null) View.GONE else View.VISIBLE
                text = item.grade.toString()
            }
            delete.setOnClickListener {
                items.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, items.size)
                mRecyclerView?.visibility = if (items.isEmpty()) View.GONE else View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int = items.size
}