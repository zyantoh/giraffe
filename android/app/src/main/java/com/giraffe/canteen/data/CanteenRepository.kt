package com.giraffe.canteen.data

import com.giraffe.canteen.model.Canteen
import com.giraffe.database.DatabaseService

class CanteenRepository(
    private val databaseService: DatabaseService
) {
    fun getCanteenDetails(
        successCallback: (List<Canteen>) -> Unit,
        failureCallback: (Exception) -> Unit,
        canceledCallback: () -> Unit
    ) {
        databaseService.collection("canteens").get(
            successCallback = {documentSnapshots ->
                val canteenNames = documentSnapshots.map {
                    Canteen(name = it.id, location = it.get("location") as String)
                }
                successCallback(canteenNames)
            },
            failureCallback = failureCallback,
            canceledCallback = canceledCallback
        )
    }
}