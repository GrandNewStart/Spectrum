package com.spectrum.spectrum.src.activities.main.fragments.search.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.spectrum.spectrum.src.activities.main.fragments.search.SearchFragment

class PagerAdapter(private val fragment: SearchFragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        if (position == 0) return fragment.mPostFragment
        if (position == 1) return fragment.mCompanyFragment

        return Fragment()
    }

}

