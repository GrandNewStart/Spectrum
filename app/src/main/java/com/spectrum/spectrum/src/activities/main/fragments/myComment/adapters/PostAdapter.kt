package com.spectrum.spectrum.src.activities.main.fragments.myComment.adapters

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
import com.spectrum.spectrum.databinding.ItemMyCommentPostBinding
import com.spectrum.spectrum.src.activities.main.fragments.myComment.MyCommentFragment
import com.spectrum.spectrum.src.activities.main.fragments.myComment.models.Post
import com.spectrum.spectrum.src.config.Helpers.dp2px

class PostAdapter(private val items: ArrayList<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMyCommentPostBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindViews(post: Post) {
            binding.apply {
                this.post = post
                titleText.text = post.title
                updateTimeText.text = post.createdAt
                responseCountText.text = "0"
                markCountText.text = "0"
                chipGroup.apply {
                    Chip(context).apply{
                        text = post.jobStatus
                        setEnsureMinTouchTargetSize(false)
                        setTextAppearance(R.style.ChipTextBig)
                        setChipStrokeWidthResource(R.dimen.thin_stroke_width)
                        setChipBackgroundColorResource(R.color.clear)
                        setChipStrokeColorResource(R.color.spectrumSilver3)
                        isClickable = false
                        if (post.jobStatus == "n차합격" || post.jobStatus == "최종합격") {
                            setChipBackgroundColorResource(R.color.clear)
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
                        isClickable = false
                        addView(this)
                    }
                    Chip(context).apply {
                        text = post.sex
                        setEnsureMinTouchTargetSize(false)
                        setChipBackgroundColorResource(R.color.spectrumSilver2)
                        setTextAppearance(R.style.ChipTextBig)
                        isClickable = false
                        addView(this)
                    }
                    Chip(context).apply {
                        text = post.jobGroupList[0].data
                        setEnsureMinTouchTargetSize(false)
                        setChipBackgroundColorResource(R.color.spectrumSilver2)
                        setTextAppearance(R.style.ChipTextBig)
                        isClickable = false
                        addView(this)
                    }
                }
            }
        }
    }

    private lateinit var mBinding: ItemMyCommentPostBinding
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_my_comment_post, parent, false)
        mBinding.fragment = mRecyclerView.findFragment()
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun proceedToPost(fragment: MyCommentFragment, post: Post) {
        fragment.findNavController().navigate(R.id.my_evaluation_to_post, bundleOf("id" to post.id))
    }

}