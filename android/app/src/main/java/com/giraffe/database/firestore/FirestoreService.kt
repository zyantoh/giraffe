package com.giraffe.database.firestore

import com.giraffe.database.DatabaseService
import com.giraffe.database.DbCollection
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreService(appName: String) : DatabaseService {
    private val firestore = FirebaseFirestore.getInstance(FirebaseApp.getInstance(appName))

    override fun collection(name: String): DbCollection =
        FirestoreCollection(firestore.collection(name))
}