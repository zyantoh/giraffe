package com.giraffe.database.firestore

import androidx.lifecycle.MutableLiveData
import com.giraffe.database.DbCollection
import com.giraffe.database.DbDocument
import com.giraffe.database.DbDocumentSnapshot
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.tasks.await

class FirestoreDocument(private val firestoreDocument: DocumentReference) : DbDocument {
    override fun collection(name: String): DbCollection = FirestoreCollection(firestoreDocument.collection(name))

    override suspend fun get(): DbDocumentSnapshot {
        val snapshot = firestoreDocument.get().await()
        return FirestoreDocumentSnapshot(snapshot)
    }

    override suspend fun set(data: Any) {
        firestoreDocument.set(data).await()
    }

    override fun watch(): MutableLiveData<Map<String, Any>> {
        val liveData = MutableLiveData<Map<String, Any>>()
        firestoreDocument.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            if (documentSnapshot!!.data != null) {
                liveData.postValue(documentSnapshot.data)
            }
        }

        return liveData
    }
}