package com.giraffe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val retrieve1 = intent.getStringExtra("title")
        val text1: TextView
        text1 = findViewById(R.id.textView4)
        text1.setText(retrieve1)
        val retrieve2 = intent.getStringExtra("detail")
        val text2: TextView
        text2 = findViewById(R.id.textView5)
        text2.setText(retrieve2)

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
