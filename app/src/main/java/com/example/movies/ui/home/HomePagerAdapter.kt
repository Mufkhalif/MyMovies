package com.example.movies.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movies.ui.movie.MovieFragment

class HomePagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {

    private val className: String get() = "com.example.bookmark.BookmarkFragment"

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> MovieFragment()
        1 -> Class.forName(className).newInstance() as Fragment
        else -> Fragment()
    }

}