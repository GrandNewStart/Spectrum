package com.spectrum.spectrum.src.adapters

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Helpers
import com.spectrum.spectrum.src.models.Experience

class ExperienceAdapter(private val items: ObservableArrayList<Experience>): RecyclerView.Adapter<ExperienceAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var mRecyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.top = Helpers.dp2px(2)
                    outRect.bottom = Helpers.dp2px(2)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_experience, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            items[position].let { item ->
                findViewById<MaterialTextView>(R.id.company_text).apply {
                    text = item.companyName
                }
                findViewById<MaterialTextView>(R.id.duty_text).apply {
                    var result = ""
                    item.jobGroup?.let { result += "$it " }
                    item.jobPosition?.let { result += it }
                    text = result
                }
                findViewById<MaterialTextView>(R.id.period_text).apply {
                    var result = ""
                    item.startDate?.let { result += "$it - " }
                    item.endDate?.let { result += it }
                    text = result
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