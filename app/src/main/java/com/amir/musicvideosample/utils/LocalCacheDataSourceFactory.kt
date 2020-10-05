package com.amir.musicvideosample.utils

import android.content.Context
import com.amir.musicvideosample.R
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.cache.CacheDataSink
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File


private const val CACHE_SIZE = 1024 * 1024 * 50
private const val MAX_FILE_SIZE = 1024 * 1024 * 100

class LocalCacheDataSourceFactory(private val context: Context) : DataSource.Factory {


    private val defaultDataSourceFactory: DefaultDataSourceFactory
    private val simpleCache: SimpleCache = SimpleCache(
        File(context.cacheDir, "media"),
        LeastRecentlyUsedCacheEvictor(CACHE_SIZE.toLong())
    )

    private val cacheDataSink: CacheDataSink =
        CacheDataSink(simpleCache, MAX_FILE_SIZE.toLong())
    private val fileDataSource: FileDataSource = FileDataSource()

    init {
        val userAgent = context.getString(R.string.app_name)
        val bandWithMeter = DefaultBandwidthMeter()
        defaultDataSourceFactory = DefaultDataSourceFactory(
            this.context,
            bandWithMeter,
            DefaultHttpDataSourceFactory(userAgent)
        )
    }

    override fun createDataSource(): DataSource {
        return CacheDataSource(
            simpleCache,
            defaultDataSourceFactory.createDataSource(),
            fileDataSource,
            cacheDataSink,
            CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,
            null
        )
    }
}
