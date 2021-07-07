package com.spectrum.spectrum.src.activities.login.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.models.CertItem

class CertItemAdapter(private val items: ArrayList<CertItem>): RecyclerView.Adapter<CertItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_certification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            items[position].let { item ->
                findViewById<MaterialTextView>(R.id.name).apply {
                    text = item.name
                }
                findViewById<MaterialTextView>(R.id.score).apply {
                    text = item.score
                }
                setOnClickListener {
                    items.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, items.size)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

}