package com.example.oxalis.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabLayOutAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val listTabFragment: List<Fragment>
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return listTabFragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> listTabFragment[position]
            1 -> listTabFragment[position]
            2 -> listTabFragment[position]
            else -> listTabFragment[0]
        }
    }

}