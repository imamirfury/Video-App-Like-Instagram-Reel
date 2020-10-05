package com.amir.musicvideosample.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.amir.musicvideosample.model.VideosModel
import com.amir.musicvideosample.ui.fragment.MusicVideoFragment

class ViewPager2Adapter(fragment: Fragment, private val videosList: ArrayList<VideosModel>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return videosList.size
    }

    override fun createFragment(position: Int): Fragment {
        return MusicVideoFragment.newInstance(videosList[position])
    }
}

