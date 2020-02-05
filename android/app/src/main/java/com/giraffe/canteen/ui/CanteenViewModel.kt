package com.giraffe.canteen.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.giraffe.canteen.data.CanteenRepository
import com.giraffe.canteen.model.Canteen
import java.lang.IllegalArgumentException

class CanteenViewModel(
    savedStateHandle: SavedStateHandle,
    canteenRepository: CanteenRepository
): ViewModel() {
    private val canteen: Canteen = savedStateHandle.get("canteen") ?:
            throw IllegalArgumentException("CanteenViewModel is missing 'canteen' argument")

    val canteenOccupancy = MutableLiveData<Long>()

    private val occupancy = canteenRepository.watchCanteenOccupancy(canteen.id)
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