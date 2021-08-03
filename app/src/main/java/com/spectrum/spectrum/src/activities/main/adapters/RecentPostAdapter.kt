package com.spectrum.spectrum.src.activities.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.ItemMainPostBinding
import com.spectrum.spectrum.src.activities.main.models.RecentPost
import com.spectrum.spectrum.src.config.Constants.SCREEN_WIDTH

class RecentPostAdapter(private val items: ArrayList<RecentPost>): RecyclerView.Adapter<RecentPostAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMainPostBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindViews(post: RecentPost) {
            binding.apply {
                titleText.text = post.title
                updateTimeText.text = post.createdAt
                chipGroup.apply {
                    Chip(context).apply {
                        text = post.jobStatus
                        setEnsureMinTouchTargetSize(false)
                        setChipStrokeWidthResource(R.dimen.thin_stroke_width)
                        setChipStrokeColorResource(R.color.spectrumSilver2)
                        setChipBackgroundColorResource(R.color.clear)
                        setTextAppearance(R.style.ChipTextBig)
                        if (post.jobStatus == "취업준비") {
                            setChipStrokeColorResource(R.color.spectrumBlue)
                            setTextColor(resources.getColor(R.color.spectrumBlue, null))
                        }
                        addView(this)
                    }
                    Chip(context).apply {
                        text = "${post.age}세"
                        setEnsureMinTouchTargetSize(false)
                        setChipBackgroundColorResource(R.color.spectrumSilver2)
                        setTextAppearance(R.style.ChipTextBig)
                        addView(this)
                    }
                    Chip(context).apply {
                        text = post.sex
                        setEnsureMinTouchTargetSize(false)
                        setChipBackgroundColorResource(R.color.spectrumSilver2)
                        setTextAppearance(R.style.ChipTextBig)
                        addView(this)
                    }
                    Chip(context).apply {
                        text = post.jobGroupList[0].data
                        setEnsureMinTouchTargetSize(false)
                        setChipBackgroundColorResource(R.color.spectrumSilver2)
                        setTextAppearance(R.style.ChipTextBig)
                        addView(this)
                    }
                }
            }
        }
    }

    private lateinit var mBinding: ItemMainPostBinding
    private lateinit var mRecyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_main_post, parent, false)
        mBinding.root.layoutParams = ViewGroup.LayoutParams((SCREEN_WIDTH * 0.6).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(items[position])
        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int = items.size

}