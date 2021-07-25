package com.spectrum.spectrum.src.activities.main.fragments.home.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.card.MaterialCardView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.models.JobGroup

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

    var mFirstItem: JobGroup? = null
    var mSecondItem: JobGroup? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.right = dp2px(8)
                outRect.left = dp2px(8)
                outRect.top = dp2px(8)
                outRect.bottom = dp2px(8)
            }
        })
        recyclerView.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_chip, parent, false)
        view.layoutParams.height = dp2px(48)
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
                                return@setOnClickListener
                            }
                            if (mSecondItem == null) {
                                mSecondItem = items[position]
                                items[position].selectIndex = 2
                                notifyItemChanged(position)
                                return@setOnClickListener
                            }
                        }
                        1 -> {
                            items[position].selectIndex = 0
                            mFirstItem = null
                            notifyItemChanged(position)
                            if (mSecondItem != null) {
                                for (i in 0 until items.size) {
                                    if (items[i].selectIndex == 2) {
                                        items[i].selectIndex = 1
                                        mFirstItem = items[i]
                                        mSecondItem = null
                                        notifyItemChanged(i)
                                    }
                                }
                            }
                        }
                        2 -> {
                            items[position].selectIndex = 0
                            mSecondItem = null
                            notifyItemChanged(position)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

}