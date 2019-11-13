package com.giraffe.database.firestore

import com.giraffe.database.DbCollection
import com.giraffe.database.DbDocument
import com.google.firebase.firestore.DocumentReference

class FirestoreDocument(private val firestoreDocument: DocumentReference) : DbDocument {
    override fun collection(name: String): DbCollection = FirestoreCollection(firestoreDocument.collection(name))

    override fun get(
        successCallback: (Map<String, Any>?) -> Unit,
        failureCallback: (Exception) -> Unit,
        canceledCallback: () -> Unit
    ) {
        firestoreDocument.get()
            .addOnSuccessListener{
                successCallback(it.data)
            }
            .addOnFailureListener(failureCallback)
            .addOnCanceledListener(canceledCallback)
    }

    override fun set(
        data: Any,
        successCallback: () -> Unit,
        failureCallback: (Exception) -> Unit,
        canceledCallback: () -> Unit
    ) {
        firestoreDocument.set(data)
            .addOnSuccessListener{ successCallback() }
            .addOnFailureListener(failureCallback)
            .addOnCanceledListener(canceledCallback)
    }
}