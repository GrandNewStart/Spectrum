package com.spectrum.spectrum.src.activities.main.fragments.myScrap.adapters

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.ItemMyPostPostBinding
import com.spectrum.spectrum.databinding.ItemMyScrapPostBinding
import com.spectrum.spectrum.src.activities.main.fragments.myScrap.MyScrapFragment
import com.spectrum.spectrum.src.activities.main.fragments.myScrap.models.Post
import com.spectrum.spectrum.src.config.Helpers.dp2px

class PostAdapter(private val items: ArrayList<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMyScrapPostBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindViews(post: Post) {
            binding.apply {
                titleText.text = post.title
                updateTimeText.text = post.createdAt
                responseCountText.text = "0"
                markCountText.text = "0"
                chipGroup.apply {
                    Chip(context).apply {
                        text = post.jobStatus
                        setEnsureMinTouchTargetSize(false)
                        setChipBackgroundColorResource(R.color.clear)
                        setTextAppearance(R.style.ChipTextSmall)
                        setTextColor(resources.getColor(R.color.spectrumBlue, null))
                        setChipStrokeColorResource(R.color.spectrumBlue)
                        setChipStrokeWidthResource(R.dimen.default_stroke_width)
                        if (post.jobStatus == "취업준비") {
                            setTextColor(resources.getColor(R.color.black, null))
                            setChipStrokeColorResource(R.color.spectrumSilver2)
                        }
                        isClickable = false
                        addView(this)
                    }
                    Chip(context).apply {
                        text = "${post.age}세"
                        setEnsureMinTouchTargetSize(false)
                        setChipBackgroundColorResource(R.color.spectrumSilver2)
                        setTextAppearance(R.style.ChipTextSmall)
                        isClickable = false
                        addView(this)
                    }
                    Chip(context).apply {
                        text = post.sex
                        setEnsureMinTouchTargetSize(false)
                        setChipBackgroundColorResource(R.color.spectrumSilver2)
                        setTextAppearance(R.style.ChipTextSmall)
                        isClickable = false
                        addView(this)
                    }
                    post.jobGroupList.forEach {
                        Chip(context).apply {
                            text = it.data
                            setEnsureMinTouchTargetSize(false)
                            setChipBackgroundColorResource(R.color.spectrumSilver2)
                            setTextAppearance(R.style.ChipTextSmall)
                            isClickable = false
                            addView(this)
                        }
                    }
                }
            }
        }
    }

    private lateinit var mBinding: ItemMyScrapPostBinding
    private lateinit var mRecyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(object : RecyclerView.ItemDecoration(){
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.left = dp2px(16)
                    outRect.right = dp2px(16)
                    outRect.top = dp2px(12)
                    outRect.bottom = dp2px(12)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_my_scrap_post, parent, false)
        mBinding.fragment = mRecyclerView.findFragment()
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun proceedToPost(fragment: MyScrapFragment, position: Int) {
        val id = items[position].id
        fragment.findNavController().navigate(R.id.my_scrap_to_post, bundleOf("id" to id))
    }

}