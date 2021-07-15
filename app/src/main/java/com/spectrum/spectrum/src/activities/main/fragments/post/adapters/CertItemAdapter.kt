package com.spectrum.spectrum.src.activities.main.fragments.post.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.models.CertItem

class CertItemAdapter(private val items: ArrayList<CertItem>): RecyclerView.Adapter<CertItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: MaterialTextView = itemView.findViewById(R.id.name_text)
        val scoreText: MaterialTextView = itemView.findViewById(R.id.score_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_post_cert_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            nameText.text = items[position].name
            scoreText.text = items[position].score
        }
    }

    override fun getItemCount(): Int = items.size

}