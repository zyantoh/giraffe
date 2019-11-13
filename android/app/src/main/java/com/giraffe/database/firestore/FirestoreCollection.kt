package com.giraffe.database.firestore

import com.giraffe.database.DbCollection
import com.giraffe.database.DbDocument
import com.google.firebase.firestore.CollectionReference

class FirestoreCollection(private val firestoreCollection: CollectionReference) : DbCollection {
    override fun document(name: String): DbDocument = FirestoreDocument(firestoreCollection.document(name))
}