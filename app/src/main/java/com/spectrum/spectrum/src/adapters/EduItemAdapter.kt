package com.spectrum.spectrum.src.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.models.EduItem

class EduItemAdapter(private val items: ObservableArrayList<EduItem>): RecyclerView.Adapter<EduItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var mRecyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_education, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            items[position].let { item ->
                findViewById<MaterialTextView>(R.id.school_text).apply {
                    var result = ""
                    item.locale?.let { result += "$it " }
                    item.school?.let { result += "$it " }
                    item.level?.let { result += it }
                    text = result
                }
                findViewById<MaterialTextView>(R.id.status_text).apply {
                    text = item.status
                }
                findViewById<MaterialTextView>(R.id.major_text).apply {
                    visibility = if (item.major == null) View.GONE else View.VISIBLE
                    text = item.major
                }
                findViewById<MaterialTextView>(R.id.grades_text).apply {
                    visibility = if (item.grade == null) View.GONE else View.VISIBLE
                    if (item.gradeSum == null) visibility = View.GONE
                    val t = "${item.grade}/${item.gradeSum}"
                    text = t
                }
                findViewById<MaterialTextView>(R.id.delete_text).setOnClickListener {
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, items.size-1)
                    items.removeAt(position)
                    mRecyclerView?.visibility = if (items.isEmpty()) View.GONE else View.VISIBLE
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

}