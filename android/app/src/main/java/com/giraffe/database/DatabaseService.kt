package com.giraffe.database

interface DatabaseService {
    fun collection(name: String): DbCollection
}