package com.spectrum.spectrum.src.activities.main.fragments.mySpec.adapters

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.fragments.mySpec.models.Spec

object BindingAdapters {

    @SuppressLint("SetTextI18n")
    @BindingAdapter("my_spec_spec")
    @JvmStatic
    fun bindMySpec(view: View, spec: Spec?) {
        when(view.id) {
            R.id.menu_button-> {
                view.visibility = if (spec == null) View.GONE else View.VISIBLE
            }
            R.id.my_spec_card_view-> {
                view.visibility = if (spec == null) View.GONE else View.VISIBLE
            }
            R.id.your_spec_text-> {
                spec?.apply {
                    (view as TextView).text = "$username 님의 스펙"
                }
            }
            R.id.update_time_text-> {
                spec?.apply {
                    (view as TextView).text = "$updatedAt 업데이트됨"
                }
            }
            R.id.user_info_chip_group-> {
                spec?.apply {
                    (view as ChipGroup).apply {
                        Chip(context).apply {
                            text = "${age}세"
                            setEnsureMinTouchTargetSize(false)
                            setChipBackgroundColorResource(R.color.spectrumSilver2)
                            setTextAppearance(R.style.ChipTextSmall)
                            addView(this)
                        }
                        Chip(context).apply {
                            text = sex
                            setEnsureMinTouchTargetSize(false)
                            setChipBackgroundColorResource(R.color.spectrumSilver2)
                            setTextAppearance(R.style.ChipTextSmall)
                            addView(this)
                        }
                        jobGroups?.forEach {
                            Chip(context).apply {
                                text = it.name
                                setEnsureMinTouchTargetSize(false)
                                setChipBackgroundColorResource(R.color.spectrumSilver2)
                                setTextAppearance(R.style.ChipTextSmall)
                                addView(this)
                            }
                        }
                    }
                }
            }
            R.id.no_spec_view-> {
                view.visibility = if (spec == null) View.VISIBLE else View.GONE
            }
        }
    }

}