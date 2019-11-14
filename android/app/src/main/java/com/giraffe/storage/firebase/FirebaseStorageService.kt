package com.giraffe.storage.firebase

import android.net.Uri
import com.giraffe.storage.StorageService
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FirebaseStorageService(bucketUrl: String) : StorageService {
    private val storageService = FirebaseStorage.getInstance(bucketUrl)

    override suspend fun getURI(path: String): Uri =
        storageService.reference.child(path).downloadUrl.await()
}