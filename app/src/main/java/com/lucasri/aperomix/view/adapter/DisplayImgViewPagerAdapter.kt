package com.lucasri.aperomix.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.lucasri.aperomix.controllers.fragments.DisplayImgViewPagerFragment


class DisplayImgViewPagerAdapter(mgr: FragmentManager) : FragmentPagerAdapter(mgr) {

    override fun getItem(position: Int): Fragment {
        return(DisplayImgViewPagerFragment.newInstance(position))
    }

    override fun getCount(): Int {
        return 3
    }
}
