package com.giraffe.database.firestore

import com.giraffe.database.DbCollection
import com.giraffe.database.DbDocument
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

class FirestoreCollection(private val firestoreCollection: CollectionReference) : DbCollection {
    override fun document(name: String): DbDocument = FirestoreDocument(firestoreCollection.document(name))

    override suspend fun get(): List<FirestoreDocumentSnapshot> {
        val snapshot = firestoreCollection.get().await()
        return snapshot.documents.map {
            FirestoreDocumentSnapshot(it)
        }
    }
}