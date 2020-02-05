package com.giraffe.canteen.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Canteen(
    val id: String,
    val name: String,
    val location: Location,
    val thumbnailUri: Uri?,
    val totalTables: Long,
    val school: School,
    val stalls: List<Stall>?
) : Parcelable
