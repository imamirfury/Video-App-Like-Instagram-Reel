package com.amir.musicvideosample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.amir.musicvideosample.R
import com.amir.musicvideosample.adapter.ViewPager2Adapter
import com.amir.musicvideosample.databinding.FragmentHomeBinding
import com.amir.musicvideosample.model.VideosModel
import com.amir.musicvideosample.utils.Constants
import com.amir.musicvideosample.utils.PreCachingService
import com.amir.musicvideosample.viewModel.VideosViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


private val TAG = HomeFragment::class.java.simpleName
class HomeFragment : Fragment(),KodeinAware {

    override val kodein: Kodein by kodein()
    private val videosViewModel : VideosViewModel by instance()

    private lateinit var adapter  : ViewPager2Adapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding  : FragmentHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),R.layout.fragment_home,null,false)
        adapter = ViewPager2Adapter(this,videosViewModel)
        binding.musicVideosPager.adapter = adapter
        setUpVideoList(binding = binding)
        loadNewVideosIfNeeded()
        return binding.root
    }

    private fun setUpVideoList(binding: FragmentHomeBinding){
        CoroutineScope(Dispatchers.Main).launch {
            val videos = videosViewModel.getAllVideosAsync().await()
            videos?.observe(this@HomeFragment, Observer {
                if (it == null)return@Observer
                adapter.submitList(it)
                startPreCaching(it)
            })
        }
    }

    private fun loadNewVideosIfNeeded(){
        videosViewModel.shouldFetchNewVideos?.observe(this,{
            it?.let {
                if (it){
                    videosViewModel.loadNewVideos(requireContext())
                }
            }
        })
    }


    private fun startPreCaching(videosList : List<VideosModel>){
        val urlsList = arrayOfNulls<String>(videosList.size)
        videosList.mapIndexed { index, videosModel ->
            urlsList[index] = videosModel.sources
        }
        val inputData = Data.Builder().putStringArray(Constants.KEY_STORIES_LIST_DATA,urlsList).build()
        val preCachingWork = OneTimeWorkRequestBuilder<PreCachingService>().setInputData(inputData).build()
        WorkManager.getInstance(requireContext()).enqueue(preCachingWork)
    }
}