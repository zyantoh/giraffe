package com.giraffe.database

interface DbCollection {
    fun document(name: String): DbDocument
}