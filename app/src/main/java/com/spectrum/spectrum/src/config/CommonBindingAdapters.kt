package com.spectrum.spectrum.src.config

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
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

    @BindingAdapter("post_horizontal")
    @JvmStatic
    fun bindPostHorizontal(view: View, post: Post) {
        when(view.id) {
            R.id.title_text -> { (view as TextView).text = post.title }
            R.id.update_time_text -> { (view as TextView).text = post.date }
            R.id.response_count_text -> { (view as TextView).text = post.commentCount.toString() }
            R.id.mark_count_text -> { (view as TextView).text = post.markCount.toString() }
            R.id.chip_1 -> { (view as Chip).text = post.infos[0].name }
            R.id.chip_2 -> { (view as Chip).text = post.infos[1].name }
            R.id.chip_3 -> { (view as Chip).text = post.infos[2].name }
            R.id.chip_4 -> { (view as Chip).text = post.infos[3].name }
            R.id.chip_5 -> { (view as Chip).text = post.infos[4].name }
        }
    }

    @BindingAdapter("post_vertical")
    @JvmStatic
    fun bindPostVertical(view: View, post: Post) {
        when(view.id) {
            R.id.title_text -> { (view as TextView).text = post.title }
            R.id.update_time_text -> { (view as TextView).text = post.date }
            R.id.response_count_text -> { (view as TextView).text = post.commentCount.toString() }
            R.id.mark_count_text -> { (view as TextView).text = post.markCount.toString() }
            R.id.chip_1 -> { (view as Chip).text = post.infos[0].name }
            R.id.chip_2 -> { (view as Chip).text = post.infos[1].name }
            R.id.chip_3 -> { (view as Chip).text = post.infos[2].name }
            R.id.chip_4 -> { (view as Chip).text = post.infos[3].name }
        }
    }

    @BindingAdapter("bind_text")
    @JvmStatic
    fun bindTextView(textView: TextView, text:String) {
        textView.text = text
    }

}