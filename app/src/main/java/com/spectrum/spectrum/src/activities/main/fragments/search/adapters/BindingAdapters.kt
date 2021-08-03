package com.spectrum.spectrum.src.activities.main.fragments.search.adapters

import android.view.inputmethod.EditorInfo
import androidx.databinding.BindingAdapter
import androidx.fragment.app.findFragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText
import com.spectrum.spectrum.src.activities.main.fragments.search.SearchFragment
import com.spectrum.spectrum.src.config.Constants

object BindingAdapters {

    @BindingAdapter("search_view_pager")
    @JvmStatic
    fun bindSearchTabs(tabLayout: TabLayout, viewPager: ViewPager2) {
        viewPager.apply {
            if (adapter == null) adapter = PagerAdapter(findFragment())
        }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position == 0) tab.text = Constants.spec_story
            if (position == 1) tab.text = Constants.company
        }.attach()
    }

    @BindingAdapter("search_fragment")
    @JvmStatic
    fun bindSearchEditText(editText: TextInputEditText, fragment: SearchFragment) {
        editText.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                fragment.apply {
                    mViewModel.mKeyword = editText.text.toString().trim()
                    mViewModel.refresh(this)
                    fragment.showKeyboard(editText, false)
                    editText.clearFocus()
                }
            }
            true
        }
    }

}