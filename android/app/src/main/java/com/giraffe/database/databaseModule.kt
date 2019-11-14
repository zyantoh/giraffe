package com.giraffe.database

import com.giraffe.database.firestore.FirestoreService
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule: Module = module {
    // Use firestore as database
    factory { FirestoreService("[DEFAULT]") } bind DatabaseService::class
}
