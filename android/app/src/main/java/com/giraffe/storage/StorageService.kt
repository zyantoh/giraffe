package com.giraffe.storage

import android.net.Uri

interface StorageService {
    suspend fun getURI(path: String): Uri
}