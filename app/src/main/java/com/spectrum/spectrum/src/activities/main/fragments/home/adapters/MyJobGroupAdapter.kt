package com.spectrum.spectrum.src.activities.main.fragments.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.home.HomeFragment
import com.spectrum.spectrum.src.config.Constants
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.models.Duty
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
                    textView.text = items[position-1].name
                }
                2->{
                    imageView.visibility = View.GONE
                    textView.visibility = View.VISIBLE
                    textView.setTextColor(itemView.resources.getColor(R.color.white, null))
                    cardView.strokeColor = itemView.resources.getColor(R.color.clear, null)
                    cardView.setCardBackgroundColor(itemView.resources.getColor(R.color.spectrumGreen, null))
                    textView.text = items[position-1].name
                }
                else->{
                    imageView.visibility = View.GONE
                    textView.visibility = View.VISIBLE
                    textView.setTextColor(itemView.resources.getColor(R.color.white, null))
                    cardView.strokeColor = itemView.resources.getColor(R.color.clear, null)
                    cardView.setCardBackgroundColor(itemView.resources.getColor(R.color.spectrumOrange, null))
                    textView.text = items[position-1].name
                }
            }
            itemView.setOnClickListener {
                if (position == 0) {
                    itemView.findFragment<HomeFragment>().findNavController().navigate(R.id.home_to_job_group)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size + 1
}