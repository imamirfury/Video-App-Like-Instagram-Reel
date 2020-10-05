package com.amir.musicvideosample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.amir.musicvideosample.R
import com.amir.musicvideosample.adapter.ViewPager2Adapter
import com.amir.musicvideosample.databinding.FragmentHomeBinding
import com.amir.musicvideosample.model.VideosModel
import com.amir.musicvideosample.utils.Constants
import com.amir.musicvideosample.utils.JsonConverter
import com.amir.musicvideosample.utils.PreCachingService


private val TAG = HomeFragment::class.java.simpleName
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding  : FragmentHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),R.layout.fragment_home,null,false)
        setUpVideoListPager(binding = binding)
        return binding.root
    }

    private fun setUpVideoListPager(binding: FragmentHomeBinding){
        val videosList = JsonConverter().getVideos(requireContext())
        videosList?.let {
            val adapter = ViewPager2Adapter(this,it)
            binding.musicVideosPager.adapter = adapter
            startPreCaching(it)
        }
    }


    private fun startPreCaching(videosList : ArrayList<VideosModel>){
        val urlsList = arrayOfNulls<String>(videosList.size)
        videosList.mapIndexed { index, videosModel ->
            urlsList[index] = videosModel.sources
        }

        val inputData = Data.Builder().putStringArray(Constants.KEY_STORIES_LIST_DATA,urlsList).build()
        val preCachingWork = OneTimeWorkRequestBuilder<PreCachingService>().setInputData(inputData).build()
        WorkManager.getInstance(requireContext()).enqueue(preCachingWork)
    }
}