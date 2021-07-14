package com.spectrum.spectrum.src.activities.main.fragments.post.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.models.Evaluation

class TopFiveAdapter(private val items: ArrayList<Evaluation>): RecyclerView.Adapter<TopFiveAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rankText: TextView = itemView.findViewById(R.id.rank_text)
        val responseText: TextView = itemView.findViewById(R.id.response_text)
        val countText: TextView = itemView.findViewById(R.id.count_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_post_top_five, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            rankText.text = (position + 1).toString()
            responseText.text = items[position].name
            countText.text = items[position].count.toString()
        }
    }

    override fun getItemCount(): Int = items.size

}