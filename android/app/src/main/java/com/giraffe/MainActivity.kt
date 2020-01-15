package com.giraffe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setUpNavigation()
    }

    fun setUpNavigation() {
        NavigationUI.setupWithNavController(
            bottomNavigationView,
            (nav_host_fragment!! as NavHostFragment).navController
        )
    }
}
