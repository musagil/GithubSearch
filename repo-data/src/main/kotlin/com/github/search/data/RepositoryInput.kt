package com.github.search.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoryInput(
    val repositoryId: Int,
    val image: String,
    val name: String,
    val desc: String
) : Parcelable
