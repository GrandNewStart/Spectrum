package com.spectrum.spectrum.src.activities.main.fragments.company.adapters

import android.graphics.Color
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.NestedScrollView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.spectrum.spectrum.R
import com.spectrum.spectrum.src.activities.main.MainActivity
import com.spectrum.spectrum.src.activities.main.fragments.company.CompanyFragment
import com.spectrum.spectrum.src.activities.main.fragments.company.models.Company
import com.spectrum.spectrum.src.activities.main.fragments.company.models.Industry
import com.spectrum.spectrum.src.config.Constants

object BindingAdapters {

    @BindingAdapter("company_logo_button", "company_fragment", requireAll = true)
    @JvmStatic
    fun bindSearchEditText(editText: EditText, button: ImageButton, fragment: CompanyFragment) {
        editText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                button.setImageResource(R.drawable.icon_back)
                (fragment.activity as MainActivity).showSearchDialog()
            }
            else {
                button.setImageResource(R.drawable.icon_logo_small)
                fragment.showKeyboard(editText, false)
                (fragment.activity as MainActivity).hideSearchDialog()
            }
        }
        editText.setOnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_SEARCH) {
                val keyword = editText.text.toString().trim()
                if (keyword.isNotEmpty()) {
                    fragment.mViewModel.proceedToSearch(fragment, keyword)
                    editText.clearFocus()
                }
                true
            }
            else false
        }
        editText.setOnKeyListener { _, id, _ ->
            if (id == KeyEvent.KEYCODE_BACK && editText.hasFocus()) {
                editText.clearFocus()
                editText.text = null
                true
            }
            else {
                false
            }
        }
    }

    @BindingAdapter("company_fragment")
    @JvmStatic
    fun bindScrollView(scrollView: NestedScrollView, fragment: CompanyFragment) {
        scrollView.setOnScrollChangeListener { v, _, _, _, _ ->
            if (!v.canScrollVertically(1)) {
                fragment.mViewModel.getCompanies(fragment)
            }
        }
    }

    @BindingAdapter("companies_vertical")
    @JvmStatic
    fun bindCompaniesVertical(recyclerView: RecyclerView, items: ArrayList<Company>?) {
        val companies = items ?: ArrayList()
        recyclerView.apply {
            adapter = CompanyAdapter(companies, CompanyAdapter.VERTICAL)
        }
    }

    @BindingAdapter("companies_horizontal")
    @JvmStatic
    fun bindCompaniesHorizontal(recyclerView: RecyclerView, items: ArrayList<Company>?) {
        val companies = items ?: ArrayList()
        recyclerView.apply {
            adapter = CompanyAdapter(companies, CompanyAdapter.HORIZONTAL)
        }
    }

    @BindingAdapter("company_industry_chips", "company_fragment")
    @JvmStatic
    fun bindIndustryChipGroup(chipGroup: ChipGroup,
                              items: ArrayList<Industry>?,
                              fragment: CompanyFragment) {
        chipGroup.apply {
            removeAllViews()
            items?.forEach { industry ->
                Chip(context).apply {
                    text = industry.data
                    isClickable = true
                    setEnsureMinTouchTargetSize(false)
                    setTextAppearance(R.style.ChipTextBig)
                    setChipStrokeColorResource(R.color.spectrumSilver3)
                    setChipStrokeWidthResource(R.dimen.thin_stroke_width)
                    setChipBackgroundColorResource(R.color.spectrumSilver4)
                    setChipSelected(this, industry.isSelected)
                    setOnClickListener {
                        if (industry.isSelected) return@setOnClickListener
                        for (i in 0 until items.size) {
                            items[i].isSelected = (industry.id == items[i].id)
                            setChipSelected((getChildAt(i) as Chip), items[i].isSelected)
                        }
                        fragment.mViewModel.onIndustrySelected(industry.id, fragment)
                    }
                    addView(this)
                }
            }
        }
    }

    private fun setChipSelected(chip: Chip, selected: Boolean) {
        chip.apply {
            if (selected){
                setChipBackgroundColorResource(R.color.spectrumBlue)
                chipStrokeWidth = 0f
                setTextColor(resources.getColor(R.color.white, null))
            }
            else {
                setChipBackgroundColorResource(R.color.spectrumSilver4)
                setChipStrokeWidthResource(R.dimen.thin_stroke_width)
                setTextColor(resources.getColor(R.color.spectrumGray2, null))
            }
        }
    }

}