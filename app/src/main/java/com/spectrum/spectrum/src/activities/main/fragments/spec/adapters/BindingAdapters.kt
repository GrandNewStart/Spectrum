package com.spectrum.spectrum.src.activities.main.fragments.spec.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginStart
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.makeramen.roundedimageview.RoundedImageView
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.config.Helpers.dp2px
import com.spectrum.spectrum.src.models.Spec

object BindingAdapters {

    @BindingAdapter("spec_spec")
    @JvmStatic
    fun bindSpecFragmentView(view: View, spec: Spec?) {
        val spec = spec ?: return
        when(view.id) {
            R.id.user_thumbnail_image -> {
                Glide.with(view)
                    .load(spec.userImage)
                    .error(R.drawable.icon_person)
                    .into(view as RoundedImageView)
            }
            R.id.user_name_text -> {
                (view as TextView).text = spec.userName
            }
            R.id.date_text -> {
                (view as TextView).text = spec.update
            }
            R.id.user_info_chip_group -> {
                val group = (view as ChipGroup)
                spec.userInfo.forEach {
                    val chip = Chip(group.context).apply {
                        text = it.name
                        val drawable = ChipDrawable.createFromAttributes(context, null, 0, R.style.DefaultChip)
                        setChipDrawable(drawable)
                        setEnsureMinTouchTargetSize(false)
                    }
                    group.addView(chip)
                }
                group.chipSpacingHorizontal = dp2px(8)
            }
            R.id.education_recycler_view -> {
                (view as RecyclerView).apply {
                    if (adapter == null) {
                        adapter = EducationAdapter(spec.educations)
                        return@apply
                    }
                    adapter?.notifyDataSetChanged()
                }
            }
            R.id.experience_recycler_view -> {
                (view as RecyclerView).apply {
                    if (adapter == null) {
                        adapter = ExperienceAdapter(spec.experiences)
                        return@apply
                    }
                    adapter?.notifyDataSetChanged()
                }
            }
            R.id.license_recycler_view -> {
                (view as RecyclerView).apply {
                    if (adapter == null) {
                        adapter = LicenseAdapter(spec.licenses)
                        return@apply
                    }
                    adapter?.notifyDataSetChanged()
                }
            }
            R.id.other_specs_text -> {
                (view as TextView).text = spec.otherSpecs
            }
        }
    }

}