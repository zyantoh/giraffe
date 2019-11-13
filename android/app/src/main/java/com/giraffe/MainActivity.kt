package com.giraffe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.giraffe.canteen.ui.CanteenListFragment
import com.giraffe.database.firestore.FirestoreService
import com.giraffe.storage.firebase.FirebaseStorageService

class MainActivity : AppCompatActivity() {
    private val databaseService = FirestoreService("[DEFAULT]")
    private val storageService = FirebaseStorageService("gs://giraffe-1.appspot.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainFragment = CanteenListFragment(databaseService, storageService)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, mainFragment, mainFragment.tag)
            .commit()

        setContentView(R.layout.activity_main)
    }
}
