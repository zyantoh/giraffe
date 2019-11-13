package com.giraffe.canteen.data

import androidx.lifecycle.*
import com.giraffe.canteen.model.Canteen
import com.giraffe.database.DatabaseService
import com.giraffe.storage.StorageService

class CanteenRepository(
    private val databaseService: DatabaseService,
    private val storageService: StorageService
) {
    suspend fun getCanteenDetails(): List<Canteen> {
        val canteenDocuments = databaseService.collection("canteens").get()
        return canteenDocuments.map {
            val thumbnailUri = storageService.getURI(it.get("thumbnail") as String)
            Canteen(
                name = it.id,
                location = it.get("location") as String,
                thumbnailUri = thumbnailUri,
                totalTables = it.get("totalTables") as Long
            )
        }
    }


    fun watchCanteenOccupancy(
        canteenName: String
    ): LiveData<Long> {
        val document = databaseService.collection("canteens").document(canteenName).watch()
        return Transformations.map(document) {
            it.get("occupiedTables") as Long
        }
    }
}
