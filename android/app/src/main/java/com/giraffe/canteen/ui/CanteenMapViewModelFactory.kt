package com.giraffe.canteen.ui

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateVMFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.giraffe.canteen.data.CanteenRepository

class CanteenMapViewModelFactory(
    private val canteenRepository: CanteenRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateVMFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        @Suppress("UNCHECKED_CAST")
        return CanteenMapViewModel(handle, canteenRepository) as T
    }
}
