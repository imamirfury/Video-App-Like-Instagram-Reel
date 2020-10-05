package com.amir.musicvideosample.data.repository

import androidx.lifecycle.LiveData
import com.amir.musicvideosample.model.VideosModel

interface AppRepository {

    suspend fun insertVideo(video : VideosModel)

    suspend fun deleteVideos()

    suspend fun getAllVideos() : LiveData<List<VideosModel>>
}