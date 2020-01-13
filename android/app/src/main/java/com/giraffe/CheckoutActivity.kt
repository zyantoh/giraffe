package com.giraffe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val retrieve1 = intent.getStringExtra("title")
        val text1: TextView
        text1 = findViewById(R.id.textView17)
        text1.setText(retrieve1)
        val retrieve2 = intent.getStringExtra("detail")
        val text2: TextView
        text2 = findViewById(R.id.textView18)
        text2.setText(retrieve2)
        val retrieve3 = intent.getStringExtra("final")
        val text3: TextView
        text3 = findViewById(R.id.textView19)
        text3.setText(retrieve3)
    }
}
