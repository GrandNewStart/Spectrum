package com.spectrum.spectrum.src.activities.login.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.models.EduItem

class EduItemAdapter(private val items: ArrayList<EduItem>): RecyclerView.Adapter<EduItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_education, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            items[position].let { item ->
                findViewById<MaterialTextView>(R.id.school).apply {
                    var result = ""
                    item.locale?.let { result += "$it " }
                    item.school?.let { result += "$it " }
                    item.level?.let { result += it }
                    text = result
                }
                findViewById<MaterialTextView>(R.id.status).apply {
                    text = item.status
                }
                findViewById<MaterialTextView>(R.id.major).apply {
                    visibility = if (item.major == null) View.GONE else View.VISIBLE
                    text = item.major
                }
                findViewById<MaterialTextView>(R.id.grades).apply {
                    visibility = if (item.grade == null) View.GONE else View.VISIBLE
                    if (item.gradeSum == null) visibility = View.GONE
                    text = "${item.grade}/${item.gradeSum}"
                }
                findViewById<MaterialTextView>(R.id.delete).setOnClickListener {
                    items.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, items.size-1)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

}