package com.amir.musicvideosample.utils


import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.amir.musicvideosample.R
import com.amir.musicvideosample.base.MusicVideoApp
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheUtil
import com.google.android.exoplayer2.util.Util
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll

private const val TAG = "PreCachingService"
class PreCachingService(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    private var cachingJob: Deferred<Unit?>? = null
    private var cacheDataSourceFactory: CacheDataSourceFactory? = null
    private val simpleCache = MusicVideoApp.simpleCache


    override suspend fun doWork(): Result = coroutineScope {
        cacheDataSourceFactory = CacheDataSourceFactory(
            simpleCache,
            DefaultHttpDataSourceFactory(
                Util.getUserAgent(
                    applicationContext,
                    applicationContext.getString(R.string.app_name)
                )
            )
        )

        val dataList = inputData.getStringArray(Constants.KEY_STORIES_LIST_DATA)

        val jobs = dataList?.map { data ->
            async {
                val dataUri = Uri.parse(data)
                val dataSpec = DataSpec(dataUri, 0, 500 * 1024, null)

                val dataSource: DataSource =
                    DefaultDataSourceFactory(
                        applicationContext,
                        Util.getUserAgent(
                            applicationContext,
                            "exo"
                        )
                    ).createDataSource()

                preloadVideo(dataSpec,
                    simpleCache,
                    dataSource,
                    CacheUtil.ProgressListener { requestLength: Long, bytesCached: Long, newBytesCached: Long ->
                        val downloadPercentage = (bytesCached * 100.0
                                / requestLength)
                        Log.d(TAG, "downloadPercentage: $downloadPercentage")
                    }
                )
            }
        }
        jobs?.joinAll()
        Result.success()
    }

    private fun preloadVideo(
        dataSpec: DataSpec?,
        cache: Cache?,
        upstream: DataSource?,
        progressListener: CacheUtil.ProgressListener?
    ) {
        try {
           CacheUtil.cache(dataSpec,cache,upstream,progressListener,null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}