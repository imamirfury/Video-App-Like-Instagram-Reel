package com.amir.musicvideosample.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideosModel(
    @SerializedName("description")
    val description: String,
    @SerializedName("sources")
    val sources: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("videoHeight")
    val videoHeight : Int
) : Parcelable