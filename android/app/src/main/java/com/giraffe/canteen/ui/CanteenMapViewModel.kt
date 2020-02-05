package com.giraffe.canteen.ui

import android.net.Uri
import androidx.lifecycle.*
import com.giraffe.canteen.data.CanteenRepository
import com.giraffe.canteen.model.Canteen
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

    val canteen: Canteen = savedStateHandle.get("canteen") ?:
        throw IllegalArgumentException("CanteenMapViewModel is missing 'canteenn' argument")

    val mapUri = MutableLiveData<Uri>()

    init {
        uiScope.launch {
            mapUri.postValue(canteenRepository.getOccupancyUri(canteen))
        }
    }

    override fun onCleared() {
        super.onCleared()

        // Clear any coroutines started by this view model
        viewModelJob.cancel()
    }
}
