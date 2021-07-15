package com.spectrum.spectrum.src.activities.main.fragments.createPost.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.models.Info

class InfoAdapter(private val items: ArrayList<Info>): RecyclerView.Adapter<InfoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chip = itemView as Chip
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = Chip(parent.context).apply {
            setChipBackgroundColorResource(R.color.spectrumSilver3)
            setTextAppearance(R.style.ChipText)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.chip.text = items[position].name
    }

    override fun getItemCount(): Int = items.size

}