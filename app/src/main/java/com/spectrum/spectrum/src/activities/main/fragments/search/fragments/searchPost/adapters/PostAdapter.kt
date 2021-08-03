package com.spectrum.spectrum.src.activities.main.fragments.search.fragments.searchPost.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.ItemSearchPostBinding
import com.spectrum.spectrum.src.activities.main.fragments.search.SearchFragment
import com.spectrum.spectrum.src.activities.main.fragments.search.fragments.searchPost.SearchPostFragment
import com.spectrum.spectrum.src.activities.main.fragments.search.models.Post

class PostAdapter(private val items: ArrayList<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder: RecyclerView.ViewHolder(mBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bindViews(post: Post) {
            mBinding.apply {
                root.setOnClickListener {
                    val fragment = mRecyclerView.findFragment<SearchPostFragment>()
                    val parent = fragment.parentFragment as SearchFragment
                    parent.findNavController().navigate(R.id.search_to_post, bundleOf("id" to post.id))
                }
                titleText.text = post.title
                updateTimeText.text = post.createdAt
                chipGroup.apply {
                    removeAllViews()
                    Chip(context).apply {
                        text = post.jobStatus
                        setEnsureMinTouchTargetSize(false)
                        setTextAppearance(R.style.ChipTextBig)
                        setChipStrokeWidthResource(R.dimen.thin_stroke_width)
                        setChipBackgroundColorResource(R.color.clear)
                        setChipStrokeColorResource(R.color.spectrumSilver2)
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

    private lateinit var mBinding: ItemSearchPostBinding
    private lateinit var mRecyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_search_post, parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun addItems(newItems: ArrayList<Post>) {
        newItems.forEach {
            items.add(it)
            notifyItemInserted(items.size-1)
        }
    }


}