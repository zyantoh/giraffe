package com.giraffe.canteen.data

import androidx.lifecycle.*
import com.giraffe.canteen.model.Canteen
import com.giraffe.canteen.model.Location
import com.giraffe.canteen.model.School
import com.giraffe.database.DatabaseService
import com.giraffe.database.DbDocument
import com.giraffe.database.DbDocumentSnapshot
import com.giraffe.storage.StorageService
import com.google.firebase.firestore.GeoPoint

class CanteenRepository(
    private val databaseService: DatabaseService,
    private val storageService: StorageService
) {
    suspend fun getSchoolDetails(document: DbDocument): School {
        val snapshot = document.get()
        return School(
            id = snapshot.id,
            name = snapshot.get("name") as String
        )
    }

    suspend fun mapSnapshotToCanteen(snapshot: DbDocumentSnapshot): Canteen {
        val thumbnailUri = storageService.getURI(snapshot.get("thumbnail") as String)

        // NOTE: This part of the code is specific to Firestore (using Geopoint)
        val geopoint = snapshot.get("location") as GeoPoint
        val location = Location(
            latitude = geopoint.latitude,
            longitude = geopoint.longitude
        )

        // TODO: Check if this works, underlying type is Firestore DbDocRef
        val school = getSchoolDetails(snapshot.get("school") as DbDocument)

        return Canteen(
            id = snapshot.id,
            name = snapshot.get("name") as String,
            location = location,
            thumbnailUri = thumbnailUri,
            totalTables = snapshot.get("totalTables") as Long,
            school = school
        )
    }


    suspend fun getCanteenDetails(): List<Canteen> {
        val canteenDocuments = databaseService.collection("canteens").get()
        // Retrieve all the canteens and convert them to canteen objects
        // TODO: Make this faster by using coroutines
        return canteenDocuments.map { mapSnapshotToCanteen(it) }
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
}
