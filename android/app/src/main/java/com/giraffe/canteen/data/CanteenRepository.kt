package com.giraffe.canteen.data

import android.net.Uri
import androidx.lifecycle.*
import com.giraffe.canteen.model.Canteen
import com.giraffe.canteen.model.Location
import com.giraffe.canteen.model.School
import com.giraffe.canteen.model.Stall
import com.giraffe.database.DatabaseService
import com.giraffe.database.DbDocument
import com.giraffe.database.DbDocumentSnapshot
import com.giraffe.database.firestore.FirestoreDocument
import com.giraffe.storage.StorageService
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

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

    suspend fun getStallDetails(document: DbDocument): Stall {
        val snapshot = document.get()
        return Stall(
            name = snapshot.get("name") as String
        )
    }

    suspend fun mapSnapshotToCanteen(snapshot: DbDocumentSnapshot): Canteen = coroutineScope {
        val thumbnailUri: Uri? = if (snapshot.get("thumbnail") == null) {
            null
        } else {
            storageService.getURI(snapshot.get("thumbnail") as String)
        }

        // NOTE: This part of the code is specific to Firestore (using Geopoint)
        val geopoint = snapshot.get("location") as GeoPoint
        val location = Location(
            latitude = geopoint.latitude,
            longitude = geopoint.longitude
        )

        val school = getSchoolDetails(FirestoreDocument(snapshot.get("school") as DocumentReference))
        val stallDeferred = if (snapshot.get("stalls") == null) {
            null
        } else {
            (snapshot.get("stalls") as List<DocumentReference>).map {
                async {
                    getStallDetails(FirestoreDocument(it))
                }
            }
        }

        val stalls = stallDeferred?.map { it.await() }

        Canteen(
            id = snapshot.id,
            name = snapshot.get("name") as String,
            location = location,
            thumbnailUri = thumbnailUri,
            totalTables = snapshot.get("totalTables") as Long,
            school = school,
            stalls = stalls
        )
    }

    suspend fun getCanteenDetails(): List<Canteen> = coroutineScope {
        val canteenDocuments = databaseService.collection("canteens").get()
        // Retrieve all the canteens and convert them to canteen objects
        val canteenDeferred = canteenDocuments.map {
            async {
                mapSnapshotToCanteen(it)
            }
        }

        canteenDeferred.map { it.await() }
    }

    suspend fun getOccupancyUri(canteen: Canteen) : Uri = storageService.getURI("canteens/${canteen.id}.jpg")

    fun watchCanteenOccupancy(
        canteenId: String
    ): LiveData<Long> {
        val document = databaseService.collection("canteens").document(canteenId).watch()
        return Transformations.map(document) {
            it["occupiedTables"] as Long
        }
    }
}
