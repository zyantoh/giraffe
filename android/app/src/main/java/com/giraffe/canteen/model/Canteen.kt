package com.giraffe.canteen.model

import android.net.Uri


data class Canteen(
    val id: String,
    val name: String,
    val location: Location,
    val thumbnailUri: Uri,
    val totalTables: Long,
    val school: School
)