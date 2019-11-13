package com.giraffe.database.firestore

import com.giraffe.database.DbCollection
import com.giraffe.database.DbDocument
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.tasks.await

class FirestoreDocument(private val firestoreDocument: DocumentReference) : DbDocument {
    override fun collection(name: String): DbCollection = FirestoreCollection(firestoreDocument.collection(name))

    override suspend fun get(): Map<String, Any>? {
        val snapshot = firestoreDocument.get().await()
        return snapshot.data
    }

    override suspend fun set(data: Any) {
        firestoreDocument.set(data).await()
    }
}