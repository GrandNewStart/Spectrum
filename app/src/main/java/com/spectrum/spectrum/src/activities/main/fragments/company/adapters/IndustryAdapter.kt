package com.spectrum.spectrum.src.activities.main.fragments.company.adapters

import android.graphics.drawable.ColorStateListDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.company.CompanyFragment
import com.spectrum.spectrum.src.models.Industry

class IndustryAdapter(private val items: ArrayList<Industry>): RecyclerView.Adapter<IndustryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: MaterialCardView = itemView.findViewById(R.id.card)
        val textView: TextView = itemView.findViewById(R.id.text)
    }

    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_chip, parent, false)
        view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            val clear = itemView.resources.getColor(R.color.clear, null)
            val gray = itemView.resources.getColor(R.color.spectrumSilver3, null)
            val darkGray = itemView.resources.getColor(R.color.spectrumGray1, null)
            val white = itemView.resources.getColor(R.color.white, null)
            val blue = itemView.resources.getColor(R.color.spectrumBlue, null)
            val item = items[position]

            textView.text = item.name
            textView.setTextColor(if (item.isSelected) white else darkGray)
            cardView.strokeColor = if (item.isSelected) blue else gray
            cardView.setCardBackgroundColor(if (item.isSelected) blue else clear)

            itemView.setOnClickListener {
                if (position == 0) {
                    for (i in 1 until items.size) {
                        items[i].isSelected = true
                        notifyItemChanged(i)
                    }
                    onAllSelected()
                }
                else {
                    items[position].isSelected = !item.isSelected
                    notifyItemChanged(position)
                    if (items[position].isSelected){ onItemSelected(position) } else { onItemDeselected(position) }
                }
            }
        }
    }

    private fun onItemSelected(position: Int) {
        val fragment = recyclerView?.findFragment<CompanyFragment>()
        val item = items[position]
        fragment?.apply {
            showToast("${item.name} SELECT")
        }
    }

    private fun onItemDeselected(position: Int) {
        val fragment = recyclerView?.findFragment<CompanyFragment>()
        val item = items[position]
        fragment?.apply {
            showToast("${item.name} DESELECT")
        }
    }

    private fun onAllSelected() {
        val fragment = recyclerView?.findFragment<CompanyFragment>()
        fragment?.apply {
            showToast("ALL SELECTED")
        }
    }

    override fun getItemCount(): Int = items.size

}