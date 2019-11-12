package com.giraffe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val image1: ImageView
        image1 = findViewById(R.id.imageView2)
        val image2: ImageView
        image2 = findViewById(R.id.imageView3)
        val image3: ImageView
        image3 = findViewById(R.id.imageView4)
        val image4: ImageView
        image4 = findViewById(R.id.imageView5)
        //image4.setImageResource(R.drawable.red)
    }
}
