package com.amir.musicvideosample.data.repository

import androidx.lifecycle.LiveData
import com.amir.musicvideosample.data.db.VideosDao
import com.amir.musicvideosample.model.VideosModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepositoryImpl(private val videosDao: VideosDao) : AppRepository {
    override suspend fun insertVideo(video: VideosModel) {
        withContext(Dispatchers.IO) {
            videosDao.insertVideo(video)
        }
    }

    override suspend fun deleteVideos() {
        withContext(Dispatchers.IO) {
            videosDao.deleteAllVideos()
        }
    }

    override suspend fun getAllVideos(): LiveData<List<VideosModel>> {
        return withContext(Dispatchers.IO) {
            videosDao.getAllVideos()
        }
    }

}