package com.amir.musicvideosample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amir.musicvideosample.databinding.FragmentMusicVideoBinding
import com.amir.musicvideosample.model.VideosModel

class MusicVideosAdapter : ListAdapter<VideosModel,MusicVideosAdapter.MusicVideosHolder>(VideosDiffCallBack()) {


    private class VideosDiffCallBack : DiffUtil.ItemCallback<VideosModel>() {
        override fun areItemsTheSame(oldItem: VideosModel, newItem: VideosModel): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: VideosModel, newItem: VideosModel): Boolean {
            return oldItem.equals(newItem)
        }
    }

    inner class MusicVideosHolder(private val binding : FragmentMusicVideoBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : VideosModel){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicVideosHolder {
        return MusicVideosHolder(FragmentMusicVideoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MusicVideosHolder, position: Int) = holder.bind(getItem(position))

}