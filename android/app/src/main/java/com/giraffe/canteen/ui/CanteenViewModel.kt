package com.giraffe.canteen.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.giraffe.canteen.data.CanteenRepository
import java.lang.IllegalArgumentException

class CanteenViewModel(
    savedStateHandle: SavedStateHandle,
    canteenRepository: CanteenRepository
): ViewModel() {
    private val canteenName: String = savedStateHandle.get("name") ?:
            throw IllegalArgumentException("CanteenViewModel is missing 'name' argument")

    val canteenOccupancy = MutableLiveData<Long>()

    private val occupancy = canteenRepository.watchCanteenOccupancy(canteenName)
    private val observer: Observer<Long> = Observer {
        canteenOccupancy.postValue(it)
    }

    init {
        occupancy.observeForever(observer)
    }

    override fun onCleared() {
        super.onCleared()

        // Clear the observer
        occupancy.removeObserver(observer)
    }
}