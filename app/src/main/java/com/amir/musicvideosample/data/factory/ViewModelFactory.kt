@file:Suppress("UNCHECKED_CAST")

package com.amir.musicvideosample.data.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amir.musicvideosample.data.repository.AppRepository
import com.amir.musicvideosample.viewModel.VideosViewModel

class ViewModelFactory(private val application: Application,private val repository: AppRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T  = with(modelClass){
        when{
            isAssignableFrom(VideosViewModel::class.java) -> VideosViewModel(application,repository)
            else -> error("View Model Error")
        }
    }as T
}