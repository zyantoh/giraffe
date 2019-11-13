package com.giraffe.database.firestore

import com.giraffe.database.DbCollection
import com.giraffe.database.DbDocument
import com.google.firebase.firestore.CollectionReference

class FirestoreCollection(private val firestoreCollection: CollectionReference) : DbCollection {
    override fun document(name: String): DbDocument = FirestoreDocument(firestoreCollection.document(name))

    override fun get(
        successCallback: (List<FirestoreDocumentSnapshot>) -> Unit,
        failureCallback: (Exception) -> Unit,
        canceledCallback: () -> Unit
    ) {
        firestoreCollection.get()
            .addOnSuccessListener {
                successCallback(it.documents.map{ ref -> FirestoreDocumentSnapshot(ref)})
            }
    }
}