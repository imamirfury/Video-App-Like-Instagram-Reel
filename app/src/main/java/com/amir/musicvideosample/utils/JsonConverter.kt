package com.amir.musicvideosample.utils

import android.content.Context
import com.amir.musicvideosample.model.VideosModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class JsonConverter {

    fun getVideos(context: Context) : ArrayList<VideosModel>?{
        val videoListJson = getStringFromJson("videos",context)
        videoListJson?.let {
            return convertJsonToObject(it)
        }
        return null
    }

    private fun getStringFromJson(jsonFileName : String,context: Context) : String? {
        try {
            val inputStream : InputStream = context.assets.open(jsonFileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            return String(buffer, Charset.forName("UTF-8"))

        }catch (e : IOException){
            e.printStackTrace()
        }
        return null
    }

    private  fun convertJsonToObject(jsonStr : String) : ArrayList<VideosModel> {
        val list = object : TypeToken<ArrayList<VideosModel>>() {}.type
        return Gson().fromJson(jsonStr,list)
    }


}