package com.spectrum.spectrum.src.config

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView

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

}