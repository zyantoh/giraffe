package com.giraffe.database

import androidx.lifecycle.MutableLiveData

interface DbDocument {
    fun collection(name: String): DbCollection

    suspend fun get(): Map<String, Any>?

    suspend fun set(data: Any)

    fun watch(): MutableLiveData<Map<String, Any>>
}