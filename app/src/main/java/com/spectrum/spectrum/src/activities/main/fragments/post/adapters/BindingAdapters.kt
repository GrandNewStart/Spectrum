package com.spectrum.spectrum.src.activities.main.fragments.post.adapters

import android.graphics.Rect
import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.models.CertItem
import com.spectrum.spectrum.src.models.EduItem
import com.spectrum.spectrum.src.models.Evaluation
import com.spectrum.spectrum.src.models.ExpItem

object BindingAdapters {

    @BindingAdapter("post_user_info")
    @JvmStatic
    fun bindUserInfo(chipGroup: ChipGroup, items: ArrayList<String>?) {
        chipGroup.apply{
            items?.let { items ->
                for (i in 0 until items.size) {
                    Chip(chipGroup.context).apply {
                        setChipBackgroundColorResource(if (i==0) R.color.clear else R.color.spectrumSilver2)
                        setChipStrokeColorResource(R.color.spectrumSilver2)
                        setChipStrokeWidthResource(R.dimen.default_stroke_width)
                        setTextAppearanceResource(R.style.CustomTextView)
                        text = items[i]
                        addView(this)
                    }
                }
            }
        }
    }

    @BindingAdapter("post_edu_items")
    @JvmStatic
    fun bindEducationList(recyclerView: RecyclerView, items: ArrayList<EduItem>?) {
        recyclerView.apply {
            items?.let { items ->
                if (layoutManager == null) {
                    layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
                }
                if (adapter == null) {
                    adapter = EduItemAdapter(items)
                    return@apply
                }
                adapter?.notifyDataSetChanged()
            }
        }
    }

    @BindingAdapter("post_exp_items")
    @JvmStatic
    fun bindExperienceList(recyclerView: RecyclerView, items: ArrayList<ExpItem>?) {
        recyclerView.apply {
            items?.let { items ->
                if (layoutManager == null) {
                    layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
                }
                if (adapter == null) {
                    adapter = ExpItemAdapter(items)
                    return@apply
                }
                adapter?.notifyDataSetChanged()
            }
        }
    }

    @BindingAdapter("post_cert_items")
    @JvmStatic
    fun bindCertificationList(recyclerView: RecyclerView, items: ArrayList<CertItem>?) {
        recyclerView.apply {
            items?.let { items ->
                if (layoutManager == null) {
                    layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
                }
                if (adapter == null) {
                    adapter = CertItemAdapter(items)
                    return@apply
                }
                adapter?.notifyDataSetChanged()
            }
        }
    }

    @BindingAdapter("post_top_five_responses")
    @JvmStatic
    fun bindTopFiveResponses(recyclerView: RecyclerView, items: ArrayList<Evaluation>?) {
        recyclerView.apply {
            items?.let { items ->
                if (layoutManager == null) {
                    layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
                    addItemDecoration(object : RecyclerView.ItemDecoration(){
                        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                            super.getItemOffsets(outRect, view, parent, state)
                            val spacing = resources.getDimensionPixelSize(R.dimen.default_margin)
                            outRect.bottom += spacing
                            outRect.top += spacing
                        }
                    })
                }
                if (adapter == null) {
                    adapter = TopFiveAdapter(items)
                    return@apply
                }
                adapter?.notifyDataSetChanged()
            }
        }
    }

    @BindingAdapter("post_responses")
    @JvmStatic
    fun bindResponses(recyclerView: RecyclerView, items: ArrayList<Evaluation>?) {
        recyclerView.apply {
            items?.let { items ->
                if (layoutManager == null) {
                    layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
                    addItemDecoration(object : RecyclerView.ItemDecoration(){
                        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                            super.getItemOffsets(outRect, view, parent, state)
                            val spacing = resources.getDimensionPixelSize(R.dimen.default_margin)
                            outRect.right += spacing
                        }
                    })
                }
                if (adapter == null) {
                    adapter = ResponseAdapter(items)
                        .setOnClickListener {  }
                        .setSelectable(false)
                    return@apply
                }
                adapter?.notifyDataSetChanged()
            }
        }
    }

    @BindingAdapter("post_response_options")
    @JvmStatic
    fun bindResponseOptions(recyclerView: RecyclerView, items: ArrayList<Evaluation>?) {
        recyclerView.apply {
            items?.let { items ->
                if (layoutManager == null) {
                    layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
                    addItemDecoration(object : RecyclerView.ItemDecoration(){
                        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                            super.getItemOffsets(outRect, view, parent, state)
                            val spacing = resources.getDimensionPixelSize(R.dimen.default_margin)
                            outRect.right += spacing
                        }
                    })
                }
                if (adapter == null) {
                    adapter = ResponseAdapter(items)
                        .setOnClickListener { item ->
                            Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
                        }
                        .setSelectable(true)
                    return@apply
                }
                adapter?.notifyDataSetChanged()
            }
        }
    }

}