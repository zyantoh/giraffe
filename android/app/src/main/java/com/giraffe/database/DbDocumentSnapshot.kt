package com.giraffe.database

interface DbDocumentSnapshot {
    val data: Map<String, Any>?
    val id: String

    fun get(field: String): Any?
}