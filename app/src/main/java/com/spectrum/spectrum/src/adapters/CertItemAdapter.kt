package com.spectrum.spectrum.src.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.models.CertItem

class CertItemAdapter(private val items: ObservableArrayList<CertItem>): RecyclerView.Adapter<CertItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private var mRecyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_certification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            items[position].let { item ->
                findViewById<MaterialTextView>(R.id.name_text).apply {
                    text = item.name
                }
                findViewById<MaterialTextView>(R.id.score_text).apply {
                    text = item.score
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