package com.giraffe.canteen.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stall(
    val name: String
) : Parcelable
