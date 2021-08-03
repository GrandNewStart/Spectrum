package com.spectrum.spectrum.src.activities.main.fragments.post.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.post.models.Comment
import com.spectrum.spectrum.src.config.Helpers.dp2px

class TopFiveAdapter(private val items: ArrayList<Comment>): RecyclerView.Adapter<TopFiveAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rankText: TextView = itemView.findViewById(R.id.rank_text)
        val responseText: TextView = itemView.findViewById(R.id.response_text)
        val countText: TextView = itemView.findViewById(R.id.count_text)
        val noItemText: TextView = itemView.findViewById(R.id.no_item_text)
        val commentView: LinearLayout = itemView.findViewById(R.id.comment_view)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.apply {
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
            if (itemDecorationCount == 0) {
                addItemDecoration(object : RecyclerView.ItemDecoration(){
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val pos = parent.getChildLayoutPosition(view)
                        if (pos == 0) return
                        outRect.top = dp2px(12)
                    }
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_post_top_five, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.apply {
            if (item.count == 0) {

            }
            commentView.visibility = if (item.count == 0) View.GONE else View.VISIBLE
            noItemText.visibility = if (item.count == 0) View.VISIBLE else View.GONE
            rankText.text = (position + 1).toString()
            responseText.text = items[position].name
            countText.text = items[position].count.toString()
        }
    }

    override fun getItemCount(): Int = items.size

}