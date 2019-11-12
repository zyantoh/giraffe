package com.giraffe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class EmptyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)

        val text = intent.getStringExtra("message")
        val textview: TextView
        textview = findViewById(R.id.textView3)
        textview.setText(text)
    }
}
