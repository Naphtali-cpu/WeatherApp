package com.naph.cellulantandroidinterview.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.naph.cellulantandroidinterview.ui.views.LaterFragment
import com.naph.cellulantandroidinterview.ui.views.TodayFragment
import com.naph.cellulantandroidinterview.ui.views.TomorrowFragment

class ViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return TodayFragment()
            }
            1 -> {
                return TomorrowFragment()
            }
            2 -> {
                return LaterFragment()
            }
            else -> {
                return TodayFragment()
            }
        }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Today"
            }
            1 -> {
                return "Tomorrow"
            }
            2 -> {
                return "Later"
            }
        }
        return super.getPageTitle(position)
    }
}