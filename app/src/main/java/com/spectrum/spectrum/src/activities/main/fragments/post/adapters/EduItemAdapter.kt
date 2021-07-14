package com.spectrum.spectrum.src.activities.main.fragments.post.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.models.EduItem

class EduItemAdapter(private val items: ArrayList<EduItem>): RecyclerView.Adapter<EduItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val schoolText: MaterialTextView = itemView.findViewById(R.id.school_text)
        val statusText: MaterialTextView = itemView.findViewById(R.id.status_text)
        val majorText: MaterialTextView = itemView.findViewById(R.id.major_text)
        val gradeText: MaterialTextView = itemView.findViewById(R.id.grades_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_post_edu_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            var school = ""
            items[position].locale?.let { school += "$it " }
            items[position].school?.let { school += "$it "}
            items[position].level?.let { school += "$it " }
            schoolText.text = school
            statusText.text = items[position].status
            majorText.text = items[position].major
            gradeText.text = items[position].grade.toString()
        }
    }

    override fun getItemCount(): Int = items.size
}