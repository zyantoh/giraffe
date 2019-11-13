package com.giraffe.canteen.model

import android.net.Uri


data class Canteen(
    val name: String,
    val location: String,
    val thumbnailUri: Uri
) {
    var tables: Map<String, Table>? = null
}