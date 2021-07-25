package com.spectrum.spectrum.src.activities.main.fragments.notification.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.notification.models.Notification
import com.spectrum.spectrum.src.config.Helpers.dp2px

class NotificationAdapter(private val items: ArrayList<Notification>): RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val backgroundView: ConstraintLayout = itemView.findViewById(R.id.background_view)
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val typeTextView: TextView = itemView.findViewById(R.id.type_text)
        val contentTextView: TextView = itemView.findViewById(R.id.content_text)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(object : RecyclerView.ItemDecoration(){
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    val pos = parent.getChildAdapterPosition(view)
                    if (pos == 0) return
                    outRect.top = dp2px(8)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.apply {
            item.isRead?.let {
                backgroundView.backgroundTintList =
                imageView.resources.getColorStateList(
                    if (it) R.color.color_light_blue else R.color.color_clear, null
                )
            }
            typeTextView.text = item.typeText
            contentTextView.text = item.content
        }
    }

    fun addItems(newItems: ArrayList<Notification>) {
        newItems.forEach {
            items.add(it)
            notifyItemInserted(items.size)
        }
    }

    override fun getItemCount(): Int = items.size

}