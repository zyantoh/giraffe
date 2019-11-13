package com.giraffe.database.firestore

import com.giraffe.database.DbDocumentSnapshot
import com.google.firebase.firestore.DocumentSnapshot

class FirestoreDocumentSnapshot(private val snapshot: DocumentSnapshot): DbDocumentSnapshot {
    override val data = snapshot.data
    override val id = snapshot.id

    override fun get(field: String) = snapshot.get(field)
}