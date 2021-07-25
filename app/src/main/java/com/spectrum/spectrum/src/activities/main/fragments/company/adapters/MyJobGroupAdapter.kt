package com.spectrum.spectrum.src.activities.main.fragments.company.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.models.JobGroup

class MyJobGroupAdapter(private val items: ArrayList<JobGroup>): RecyclerView.Adapter<MyJobGroupAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cardView = itemView as MaterialCardView
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val textView: TextView = itemView.findViewById(R.id.text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_chip, parent, false)
        view.layoutParams.height = dp2px(30)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            cardView.radius = dp2px(15).toFloat()
            textView.text = items[position].name
            when(position) {
                0->{
                    imageView.visibility = View.VISIBLE
                    textView.visibility = View.GONE
                    cardView.strokeColor = itemView.resources.getColor(R.color.spectrumSilver3, null)
                    cardView.setCardBackgroundColor(itemView.resources.getColor(R.color.clear, null))
                }
                1->{
                    imageView.visibility = View.GONE
                    textView.visibility = View.VISIBLE
                    textView.setTextColor(itemView.resources.getColor(R.color.white, null))
                    cardView.strokeColor = itemView.resources.getColor(R.color.clear, null)
                    cardView.setCardBackgroundColor(itemView.resources.getColor(R.color.spectrumBlue, null))
                }
                2->{
                    imageView.visibility = View.GONE
                    textView.visibility = View.VISIBLE
                    textView.setTextColor(itemView.resources.getColor(R.color.white, null))
                    cardView.strokeColor = itemView.resources.getColor(R.color.clear, null)
                    cardView.setCardBackgroundColor(itemView.resources.getColor(R.color.spectrumGreen, null))
                }
                else->{
                    imageView.visibility = View.GONE
                    textView.visibility = View.VISIBLE
                    textView.setTextColor(itemView.resources.getColor(R.color.white, null))
                    cardView.strokeColor = itemView.resources.getColor(R.color.clear, null)
                    cardView.setCardBackgroundColor(itemView.resources.getColor(R.color.spectrumOrange, null))
                }
            }
            itemView.setOnClickListener {
                if (position == 0) {

                }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}