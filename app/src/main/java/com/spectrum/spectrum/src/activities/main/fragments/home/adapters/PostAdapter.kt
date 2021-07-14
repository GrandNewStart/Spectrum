package com.spectrum.spectrum.src.activities.main.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.databinding.ItemHomePostBinding
import com.spectrum.spectrum.src.activities.main.fragments.home.HomeFragment
import com.spectrum.spectrum.src.config.Constants.SCREEN_WIDTH
import com.spectrum.spectrum.src.models.Post

class PostAdapter(private val fragment: HomeFragment,
                  private val items: ArrayList<Post>,
                  private val orientation: Int): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemHomePostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post, fragment: HomeFragment) {
            post.apply {
                binding.title = title
                binding.date = date
                binding.commentCount = commentCount
                binding.markCount = markCount
                binding.specs = specs
                binding.fragment = fragment
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemHomePostBinding = DataBindingUtil.inflate(inflater, R.layout.item_home_post, parent, false)
        binding.root.apply {
            if (orientation == VERTICAL) {
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            }
            if (orientation == HORIZONTAL) {
                layoutParams.width = (SCREEN_WIDTH * 0.7).toInt()
            }
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], fragment)
    }

    override fun getItemCount(): Int = items.size

    companion object {
        const val VERTICAL = 0
        const val HORIZONTAL = 1
    }

}