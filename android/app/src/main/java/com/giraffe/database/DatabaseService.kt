package com.giraffe.database

interface DatabaseService {
    fun collection(name: String): DbCollection

    fun document(path: String): DbDocument
}