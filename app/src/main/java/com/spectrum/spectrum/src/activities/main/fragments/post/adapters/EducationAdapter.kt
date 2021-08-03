package com.spectrum.spectrum.src.activities.main.fragments.post.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.post.PostFragment
import com.spectrum.spectrum.src.activities.main.fragments.post.models.Education
import com.spectrum.spectrum.src.config.Helpers.dp2px

class EducationAdapter(private val items: ArrayList<Education>): RecyclerView.Adapter<EducationAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val locationText: MaterialTextView = itemView.findViewById(R.id.location_text)
        val schoolText: MaterialTextView = itemView.findViewById(R.id.school_text)
        val degreeText: MaterialTextView = itemView.findViewById(R.id.degree_text)
        val statusText: MaterialTextView = itemView.findViewById(R.id.status_text)
        val majorText: MaterialTextView = itemView.findViewById(R.id.major_text)
        val gradeText: MaterialTextView = itemView.findViewById(R.id.grades_text)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.apply {
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
            if (itemDecorationCount == 0) {
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val pos = parent.getChildLayoutPosition(view)
                        if (pos != 0) outRect.top += dp2px(8)
                    }
                })
            }
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
            locationText.apply {
                visibility = if (item.location == null) View.GONE else View.VISIBLE    
                text = item.location?.data
            }
            degreeText.apply {
                visibility = if (item.degree == null) View.GONE else View.VISIBLE
                text = item.degree?.data
            }
            schoolText.apply {
                visibility = if (item.school == null) View.GONE else View.VISIBLE
                text = item.school
            }
            statusText.apply {
                visibility = if (item.graduate == null) View.GONE else View.VISIBLE
                text = item.graduate?.data
            }
            majorText.apply {
                visibility = if (item.major == null) View.GONE else View.VISIBLE
                text = items[position].major
            }
            gradeText.apply {
                visibility = if (item.grade == null) View.GONE else View.VISIBLE
                text = items[position].grade.toString()
            }
        }
    }

    override fun getItemCount(): Int = items.size
}