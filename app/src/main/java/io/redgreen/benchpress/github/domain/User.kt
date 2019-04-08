package io.redgreen.benchpress.github.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val username: String,
    val avatarUrl: String
) : Parcelable
