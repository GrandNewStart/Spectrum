package com.spectrum.spectrum.src.activities.main.fragments.post.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.models.ExpItem

class ExpItemAdapter(private val items: ArrayList<ExpItem>): RecyclerView.Adapter<ExpItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyText: MaterialTextView = itemView.findViewById(R.id.company_text)
        val dutyText: MaterialTextView = itemView.findViewById(R.id.duty_text)
        val periodText: MaterialTextView = itemView.findViewById(R.id.period_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_post_exp_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            companyText.text = items[position].company
            val duty = "${items[position].duty} ${items[position].position}"
            dutyText.text = duty
            val period = "${items[position].startDate} - ${items[position].endDate}"
            periodText.text = period
        }
    }

    override fun getItemCount(): Int = items.size

}