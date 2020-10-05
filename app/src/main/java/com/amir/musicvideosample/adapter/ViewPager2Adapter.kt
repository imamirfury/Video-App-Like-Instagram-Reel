package com.amir.musicvideosample.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.amir.musicvideosample.model.VideosModel
import com.amir.musicvideosample.ui.fragment.MusicVideoFragment
import com.amir.musicvideosample.viewModel.VideosViewModel

class ViewPager2Adapter(fragment: Fragment,private val videosViewModel: VideosViewModel) : FragmentStateAdapter(fragment) {
    private var videosList  = mutableListOf<VideosModel>()

    fun submitList(list : List<VideosModel>){
        this.videosList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return videosList.size
    }

    override fun createFragment(position: Int): Fragment {
        if (videosList.size - position == 2){
            videosViewModel.shouldFetchNewVideos.value = true
        }
        return MusicVideoFragment.newInstance(videosList[position])
    }

}

