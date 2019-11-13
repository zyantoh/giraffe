package com.giraffe.database

import com.giraffe.database.firestore.FirestoreDocumentSnapshot

interface DbCollection {
    fun document(name: String): DbDocument
    fun get(
        successCallback: (List<FirestoreDocumentSnapshot>) -> Unit,
        failureCallback: (Exception) -> Unit,
        canceledCallback: () -> Unit
    )
}