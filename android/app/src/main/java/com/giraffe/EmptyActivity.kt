package com.giraffe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class EmptyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)

        val retrieve1 = intent.getStringExtra("title")
        val text1: TextView
        text1 = findViewById(R.id.textView6)
        text1.setText(retrieve1)
        val retrieve2 = intent.getStringExtra("detail")
        val text2: TextView
        text2 = findViewById(R.id.textView7)
        text2.setText(retrieve2)

        val text = intent.getStringExtra("message")
        val textview: TextView
        textview = findViewById(R.id.textView3)
        textview.setText(text)
    }
}
