package com.giraffe

import androidx.multidex.MultiDexApplication
import com.giraffe.canteen.canteenModule
import com.giraffe.database.databaseModule
import com.giraffe.storage.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()

            androidContext(this@MainApplication)

            androidFileProperties()

            modules(listOf(databaseModule, storageModule, canteenModule))
        }
    }
}
