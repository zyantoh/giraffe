package com.giraffe.canteen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.giraffe.canteen.data.CanteenRepository
import com.giraffe.canteen.model.Canteen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class CanteenListViewModel(
    canteenRepository: CanteenRepository
): ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val canteenList = MutableLiveData<List<Canteen>>()
    val canteenOccupancy = mutableListOf<LiveData<Long>>()

    init {
        uiScope.launch {
            val canteens = canteenRepository.getCanteenDetails()
            canteens.forEach {
                canteenOccupancy.add(canteenRepository.watchCanteenOccupancy(it.id))
            }
            canteenList.postValue(canteens)
        }
    }

    override fun onCleared() {
        super.onCleared()

        // Clear any coroutines started by this view model
        viewModelJob.cancel()
    }
}