package com.giraffe.canteen.data

import androidx.lifecycle.*
import com.giraffe.canteen.model.Canteen
import com.giraffe.canteen.model.Table
import com.giraffe.canteen.model.TableStatus
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
                totalTables = it.get("totalTables") as Long,
                school = it.get("school") as String
            )
        }
    }

    suspend fun getTableIds(canteenName: String): List<String> {
        val tableDocuments = databaseService
            .collection("canteens")
            .document(canteenName)
            .collection("tables")
            .get()
        return tableDocuments.map { it.id }
    }

    fun watchCanteenOccupancy(
        canteenName: String
    ): LiveData<Long> {
        val document = databaseService.collection("canteens").document(canteenName).watch()
        return Transformations.map(document) {
            it["occupiedTables"] as Long
        }
    }

    fun watchCanteenTable(
        canteenName: String,
        tableId: String
    ): LiveData<Table> {
        val document = databaseService
            .collection("canteens")
            .document(canteenName)
            .collection("tables")
            .document(tableId)
            .watch()

        return Transformations.map(document) {
            val tableStatus = TableStatus.valueOf(it["isTaken"] as String)
            Table(
                tableId,
                it["type"] as String,
                tableStatus,
                // TODO: Add coordinates
                0,
                0
            )
        }
    }
}
