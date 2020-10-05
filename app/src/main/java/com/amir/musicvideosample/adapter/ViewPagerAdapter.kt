package com.amir.musicvideosample.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.amir.musicvideosample.model.VideosModel
import com.amir.musicvideosample.ui.fragment.MusicVideoFragment

class ViewPagerAdapter(fm : FragmentManager, private val videosList : ArrayList<VideosModel>) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return videosList.size
    }

    override fun getItem(position: Int): Fragment {
        return MusicVideoFragment.newInstance(videoModel = videosList[position])
    }
}