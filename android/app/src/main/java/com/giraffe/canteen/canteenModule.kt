package com.giraffe.canteen

import com.giraffe.canteen.data.CanteenRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val canteenModule: Module = module {
    single { CanteenRepository(get(), get()) }
}
