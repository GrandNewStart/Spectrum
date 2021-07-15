package com.spectrum.spectrum.src.activities.main.fragments.createPost.adapters

import android.graphics.Rect
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.models.Info

object BindingAdapters {

    @BindingAdapter("create_post_user_info")
    @JvmStatic
    fun bindUserInfo(recyclerView: RecyclerView, items: ArrayList<Info>?) {
        recyclerView.apply {
            items?.let { items ->
                if (layoutManager == null) {
                    layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
                    addItemDecoration(object : RecyclerView.ItemDecoration() {
                        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                            super.getItemOffsets(outRect, view, parent, state)
                            outRect.right += resources.getDimensionPixelSize(R.dimen.default_half_margin)
                        }
                    })
                }
                if (adapter == null) {
                    adapter = InfoAdapter(items)
                    return@apply
                }
                adapter?.notifyDataSetChanged()
            }
        }
    }

}