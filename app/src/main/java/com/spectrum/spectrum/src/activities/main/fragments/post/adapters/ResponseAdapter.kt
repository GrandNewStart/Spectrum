package com.spectrum.spectrum.src.activities.main.fragments.post.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.models.Evaluation

class ResponseAdapter(private val items: ArrayList<Evaluation>): RecyclerView.Adapter<ResponseAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: MaterialCardView = itemView.findViewById(R.id.card_view)
        val nameText: TextView = itemView.findViewById(R.id.response_text)
        val countText: TextView = itemView.findViewById(R.id.count_text)
    }

    private var onClickListener: (item: Evaluation)->Unit = {}
    private var selectable = false

    fun setOnClickListener(onClickListener: (item: Evaluation)->Unit): ResponseAdapter {
        this.onClickListener = onClickListener
        return this
    }

    fun setSelectable(selectable: Boolean): ResponseAdapter {
        this.selectable = selectable
        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_post_chip, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            nameText.text = items[position].name
            countText.text = items[position].count.toString()
            if (selectable) {
                nameText.setTextColor(itemView.resources.getColor(R.color.spectrumSilver3, null))
                countText.visibility = View.GONE
            }
            else {
                nameText.setTextColor(itemView.resources.getColor(R.color.spectrumGray3, null))
                countText.visibility = View.VISIBLE
            }
            itemView.setOnClickListener {
                onClickListener(items[position])
            }
        }
    }

    override fun getItemCount(): Int = items.size

}