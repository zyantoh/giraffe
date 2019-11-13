package com.giraffe.database

interface DbDocument {
    fun collection(name: String): DbCollection

    fun get(
        successCallback: (Map<String, Any>?) -> Unit,
        failureCallback: (Exception) -> Unit,
        canceledCallback: () -> Unit
    )

    fun set(
        data: Any,
        successCallback: () -> Unit,
        failureCallback: (Exception) -> Unit,
        canceledCallback: () -> Unit
    )
}