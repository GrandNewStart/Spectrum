package com.spectrum.spectrum.src.config

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.makeramen.roundedimageview.RoundedImageView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.models.Post

object CommonBindingAdapters {

    @BindingAdapter("image_url")
    @JvmStatic
    fun bindImageView(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context)
                .load(url)
                .into(imageView)
        }
    }

    @BindingAdapter("image_url")
    @JvmStatic
    fun bindImageView(imageView: RoundedImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context)
                .load(url)
                .into(imageView)
        }
    }

    @BindingAdapter("post_item")
    @JvmStatic
    fun bindPostHorizontal(view: View, post: Post) {
        when(view.id) {
            R.id.title_text -> { (view as TextView).text = post.title }
            R.id.update_time_text -> { (view as TextView).text = post.date }
            R.id.response_count_text -> { (view as TextView).text = post.commentCount.toString() }
            R.id.mark_count_text -> { (view as TextView).text = post.markCount.toString() }
            R.id.chip_group -> {
                (view as ChipGroup).apply {
                    removeAllViews()
                    post.infos.forEach {
                        Chip(context).apply {
                            text = it.name
                            setEnsureMinTouchTargetSize(false)
                            setChipBackgroundColorResource(R.color.spectrumSilver2)
                            setTextAppearance(R.style.ChipText)
                            addView(this)
                        }
                    }
                }
            }
        }
    }

    @BindingAdapter("bind_text")
    @JvmStatic
    fun bindTextView(textView: TextView, text:String) {
        textView.text = text
    }

}