package com.spectrum.spectrum.src.activities.main.fragments.post

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.google.android.material.chip.Chip
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.post.models.Comment

@SuppressLint("ViewConstructor")
class CommentChip(context: Context, private var comment: Comment): Chip(context) {

    private val blue = resources.getColor(R.color.spectrumBlue, null)
    private val silver = resources.getColor(R.color.spectrumSilver3, null)
    private val gray = resources.getColor(R.color.spectrumGray3, null)

    init {
        checkedIcon = null
        isClickable = true
        isCheckable = false
        setChipBackgroundColorResource(R.color.clear)
        setChipStrokeWidthResource(R.dimen.thin_stroke_width)
        setChipStrokeColorResource(R.color.spectrumSilver3)
        setTextAppearance(R.style.ChipTextBig)
        setTextColor(if (comment.count == 0) silver else gray)
        updateUI(comment)
    }

    fun updateUI(comment: Comment) {
        this.comment = comment
        if (comment.isSelected) {
            setChipBackgroundColorResource(R.color.spectrumLightBlue)
            setChipStrokeWidthResource(R.dimen.thin_stroke_width)
            setChipStrokeColorResource(R.color.spectrumLightBlue)
            setTextColor(gray)
        }
        else {
            setChipBackgroundColorResource(R.color.clear)
            setChipStrokeWidthResource(R.dimen.thin_stroke_width)
            setChipStrokeColorResource(R.color.spectrumSilver3)
            setTextColor(if (comment.count == 0) silver else gray)
        }
        updateCommentText()
    }

    private fun updateCommentText() {
        if (comment.count == 0) {
            text = comment.name
            return
        }
        val t = "${comment.name} ${comment.count}"
        val spannable = SpannableStringBuilder(t)
        spannable.setSpan(
            ForegroundColorSpan(blue),
            comment.name.length,
            t.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        text = spannable
    }

}