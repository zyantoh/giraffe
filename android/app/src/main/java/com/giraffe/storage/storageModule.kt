package com.giraffe.storage

import com.giraffe.storage.firebase.FirebaseStorageService
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

val storageModule: Module = module {
    // Use firebase storage as image storage
    factory { FirebaseStorageService("gs://giraffe-1.appspot.com") } bind StorageService::class
}