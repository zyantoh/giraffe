package com.giraffe.canteen.model

data class Table(
    val tableId: String,
    val type: String,
    val status: TableStatus,
    val x: Int,
    val y: Int
)