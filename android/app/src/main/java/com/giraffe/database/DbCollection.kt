package com.giraffe.database

import com.giraffe.database.firestore.FirestoreDocumentSnapshot

interface DbCollection {
    fun document(name: String): DbDocument
    suspend fun get(): List<FirestoreDocumentSnapshot>
}