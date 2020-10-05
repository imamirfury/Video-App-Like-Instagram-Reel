package com.amir.musicvideosample.base

import android.app.Application
import android.content.Context
import com.amir.musicvideosample.data.db.AppDatabase
import com.amir.musicvideosample.data.db.VideosDao
import com.amir.musicvideosample.data.factory.ViewModelFactory
import com.amir.musicvideosample.data.repository.AppRepository
import com.amir.musicvideosample.data.repository.AppRepositoryImpl
import com.amir.musicvideosample.viewModel.VideosViewModel
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MusicVideoApp : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@MusicVideoApp))
        bind() from singleton { AppDatabase(instance()) }
        bind<VideosDao>() with singleton { instance<AppDatabase>().videosDao() }
        bind<AppRepository>() with singleton { AppRepositoryImpl(instance()) }
        bind() from provider {
            ViewModelFactory(instance(),instance())
        }

        bind() from singleton { VideosViewModel(instance(),instance()) }
    }

    companion object {
        var context: Context? = null
        var simpleCache: SimpleCache? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        val leastRecentlyUsedCacheEvictor = LeastRecentlyUsedCacheEvictor(90 * 1024 * 1024)
        val databaseProvider: DatabaseProvider = ExoDatabaseProvider(this)

        if (simpleCache == null) {
            simpleCache = SimpleCache(cacheDir, leastRecentlyUsedCacheEvictor, databaseProvider)
        }
    }
}