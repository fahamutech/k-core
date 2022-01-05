package com.fahamutech.kcore.admin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fahamutech.kcore.admin.fragment.HomeArticlesFragment
import com.fahamutech.kcore.admin.fragment.HomeTestimonyFragment

class HomePageFragmentAdapter(fm: FragmentManager?) : FragmentPagerAdapter(
    fm!!
) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeArticlesFragment()
            }
            1 -> {
                HomeTestimonyFragment()
            }
            else -> Fragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                "Home"
            }
            1 -> {
                "Testimony"
            }
            else -> super.getPageTitle(position)
        }
    }

    override fun getCount(): Int {
        return 2
    }
}