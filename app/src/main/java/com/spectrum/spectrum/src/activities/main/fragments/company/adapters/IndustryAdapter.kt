package com.spectrum.spectrum.src.activities.main.fragments.company.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Helpers
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
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(object : RecyclerView.ItemDecoration(){
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.left = Helpers.dp2px(4)
                    outRect.right = Helpers.dp2px(4)
                    outRect.top = Helpers.dp2px(8)
                    outRect.bottom = Helpers.dp2px(8)
                }
            })
        }
        items[0].isSelected = true
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
                for (i in 0 until items.size) {
                    items[i].isSelected = (i == position)
                    notifyItemChanged(i)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

}