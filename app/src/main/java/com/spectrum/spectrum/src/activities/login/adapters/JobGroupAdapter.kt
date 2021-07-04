package com.spectrum.spectrum.src.activities.login.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.login.LogInActivity
import com.spectrum.spectrum.src.activities.login.fragments.JobGroupFragment
import com.spectrum.spectrum.src.activities.login.models.JobGroup

class JobGroupAdapter(private val items: ArrayList<JobGroup>): RecyclerView.Adapter<JobGroupAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mCardView: MaterialCardView = itemView.findViewById(R.id.card)
        var mTextView: TextView = itemView.findViewById(R.id.text)
        fun makeFirstSelection() {
            mCardView.strokeColor = itemView.resources.getColor(R.color.spectrumBlue, null)
            mCardView.setCardBackgroundColor(itemView.resources.getColor(R.color.spectrumBlue, null))
            mTextView.setTextColor(itemView.resources.getColor(R.color.white, null))
        }
        fun makeSecondSelection() {
            mCardView.strokeColor = itemView.resources.getColor(R.color.spectrumGreen, null)
            mCardView.setCardBackgroundColor(itemView.resources.getColor(R.color.spectrumGreen, null))
            mTextView.setTextColor(itemView.resources.getColor(R.color.white, null))
        }
        fun deselect() {
            mCardView.strokeColor = itemView.resources.getColor(R.color.spectrumGray3, null)
            mCardView.setCardBackgroundColor(itemView.resources.getColor(R.color.clear, null))
            mTextView.setTextColor(itemView.resources.getColor(R.color.spectrumGray3, null))
        }
    }

    private var mFirstItem: JobGroup? = null
    private var mSecondItem: JobGroup? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_chip, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            items[position].let { item ->
                mTextView.text = item.name
                when(item.selectIndex) {
                    0-> { deselect() }
                    1-> {
                        makeFirstSelection()
                        mFirstItem = item
                    }
                    2-> {
                        makeSecondSelection()
                        mSecondItem = item
                    }
                }
                mCardView.setOnClickListener {
                    when(item.selectIndex) {
                        0 -> {
                            if (mFirstItem == null) {
                                mFirstItem = items[position]
                                items[position].selectIndex = 1
                                notifyItemChanged(position)
                                applyChanges(itemView)
                                return@setOnClickListener
                            }
                            if (mSecondItem == null) {
                                mSecondItem = items[position]
                                items[position].selectIndex = 2
                                notifyItemChanged(position)
                                applyChanges(itemView)
                                return@setOnClickListener
                            }
                        }
                        1 -> {
                            items[position].selectIndex = 0
                            mFirstItem = null
                            applyChanges(itemView)
                            notifyItemChanged(position)
                            if (mSecondItem != null) {
                                for (i in 0 until items.size) {
                                    if (items[i].selectIndex == 2) {
                                        mFirstItem = items[i]
                                        mSecondItem = null
                                        items[i].selectIndex = 1
                                        applyChanges(itemView)
                                        notifyItemChanged(i)
                                    }
                                }
                            }
                        }
                        2 -> {
                            items[position].selectIndex = 0
                            mSecondItem = null
                            applyChanges(itemView)
                            notifyItemChanged(position)
                        }
                    }
                }
            }
        }
    }

    private fun applyChanges(itemView: View) {
        val fragment = itemView.findFragment<JobGroupFragment>()
        val activity = fragment.activity as LogInActivity
        activity.mJobGroup1 = mFirstItem
        activity.mJobGroup2 = mSecondItem
    }

    override fun getItemCount(): Int = items.size

}