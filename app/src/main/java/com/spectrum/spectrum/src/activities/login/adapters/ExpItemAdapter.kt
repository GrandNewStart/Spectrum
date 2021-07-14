package com.spectrum.spectrum.src.activities.login.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.models.ExpItem

class ExpItemAdapter(private val items: ArrayList<ExpItem>): RecyclerView.Adapter<ExpItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_experience, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            items[position].let { item ->
                findViewById<MaterialTextView>(R.id.company).apply {
                    text = item.company
                }
                findViewById<MaterialTextView>(R.id.duty).apply {
                    var result = ""
                    item.duty?.let { result += "$it " }
                    item.position?.let { result += it }
                    text = result
                }
                findViewById<MaterialTextView>(R.id.period).apply {
                    var result = ""
                    item.startDate?.let { result += "$it - " }
                    item.endDate?.let { result += it }
                    text = result
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