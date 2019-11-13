package com.giraffe.canteen.model

data class Canteen(val name: String, val location: String) {
    var tables: Map<String, Table>? = null
}