package com.giraffe.canteen.ui

import androidx.lifecycle.*
import com.giraffe.canteen.data.CanteenRepository
import com.giraffe.canteen.model.Table
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class CanteenMapViewModel(
    savedStateHandle: SavedStateHandle,
    canteenRepository: CanteenRepository
): ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val canteenName: String = savedStateHandle.get("name") ?:
        throw IllegalArgumentException("CanteenMapViewModel is missing 'name' argument")
    val canteenLocation: String = savedStateHandle.get("location") ?:
        throw IllegalArgumentException("CanteenMapViewModel is missing 'location' argument")

    var tables: MutableLiveData<List<LiveData<Table>>> = MutableLiveData()

    init {
        uiScope.launch {
            val tableIds = canteenRepository.getTableIds(canteenName)
            tables.postValue(tableIds.map {
                canteenRepository.watchCanteenTable(canteenName, it)
            })
        }
    }

    override fun onCleared() {
        super.onCleared()

        // Clear any coroutines started by this view model
        viewModelJob.cancel()
    }
}
