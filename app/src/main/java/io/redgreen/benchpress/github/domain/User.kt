package io.redgreen.benchpress.github.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @field:Json(name = "login") val username: String,
    @field:Json(name = "avatar_url") val avatarUrl: String
) : Parcelable
