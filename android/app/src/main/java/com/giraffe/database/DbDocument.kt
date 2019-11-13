package com.giraffe.database

interface DbDocument {
    fun collection(name: String): DbCollection

    suspend fun get(): Map<String, Any>?

    suspend fun set(data: Any)
}