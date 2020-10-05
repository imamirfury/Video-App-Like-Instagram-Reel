package com.amir.musicvideosample.utils

import android.content.Context
import android.net.Uri
import com.amir.musicvideosample.R
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


private const val FORMAT_MP4 = ".mp4"
private const val FORMAT_M3U8 = ".m3u8"
private const val MAX_CACHE_SIZE : Long = 1024 * 1024 * 1024

object ExoPlayerUtils {

    private fun getDataSourceFactory(context: Context): DataSource.Factory {
        return DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, context.getString(R.string.app_name))
        )
    }

    fun getMediaSource(context: Context, videoUrl: String): MediaSource {
        return if (videoUrl.contains(FORMAT_M3U8)) {
            HlsMediaSource.Factory(getDataSourceFactory(context))
                .createMediaSource(Uri.parse(videoUrl))
        } else {
            ProgressiveMediaSource.Factory(getDataSourceFactory(context))
                .createMediaSource(Uri.parse(videoUrl))
        }
    }

}