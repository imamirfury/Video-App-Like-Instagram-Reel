package com.amir.musicvideosample.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.amir.musicvideosample.model.VideosModel
import com.amir.musicvideosample.ui.fragment.MusicVideoFragment

class ViewPager2Adapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private var videosList  = mutableListOf<VideosModel>()
    fun submitList(list : List<VideosModel>){
        this.videosList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return videosList.size
    }

    override fun createFragment(position: Int): Fragment {
        return MusicVideoFragment.newInstance(videosList[position])
    }
}

