package com.giraffe.canteen.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class School(
    val id: String,
    val name: String
) : Parcelable
