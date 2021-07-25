package com.spectrum.spectrum.src.activities.main.fragments.post.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.post.models.Experience
import com.spectrum.spectrum.src.config.Helpers.dp2px

class ExperienceAdapter(private val items: ArrayList<Experience>): RecyclerView.Adapter<ExperienceAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyText: MaterialTextView = itemView.findViewById(R.id.company_text)
        val dutyText: MaterialTextView = itemView.findViewById(R.id.duty_text)
        val periodText: MaterialTextView = itemView.findViewById(R.id.period_text)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    val pos = parent.getChildLayoutPosition(view)
                    if (pos != 0) outRect.top += dp2px(8)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_post_exp_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            companyText.text = items[position].companyName
            val duty = "${items[position].jobGroup} ${items[position].jobPosition}"
            dutyText.text = duty
            val period = "${items[position].jobStart} - ${items[position].jobEnd}"
            periodText.text = period
        }
    }

    override fun getItemCount(): Int = items.size

}