package com.giraffe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        val retrieve1 = intent.getStringExtra("title")
        val title: TextView
        title = findViewById(R.id.textView8)
        title.setText(retrieve1)
        val retrieve2 = intent.getStringExtra("detail")
        val detail: TextView
        detail = findViewById(R.id.textView9)
        detail.setText(retrieve2)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent( this, MapActivity::class.java)
            intent.putExtra("title", title.text)
            intent.putExtra("detail", detail.text)
            this.startActivity(intent)
        }
        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            val intent = Intent( this, OrderActivity::class.java)
            intent.putExtra("title", title.text)
            intent.putExtra("detail", detail.text)
            this.startActivity(intent)
        }
        val button3 = findViewById<Button>(R.id.button3)
        button3.setOnClickListener {
            val intent = Intent( this, MapActivity::class.java)
            intent.putExtra("title", title.text)
            intent.putExtra("detail", detail.text)
            this.startActivity(intent)
        }
    }
}
