package com.giraffe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val retrieve1 = intent.getStringExtra("title")
        val title: TextView
        title = findViewById(R.id.textView10)
        title.setText(retrieve1)
        val retrieve2 = intent.getStringExtra("detail")
        val detail: TextView
        detail = findViewById(R.id.textView11)
        detail.setText(retrieve2)
//        <resources>
//            <string-array name="stalls_array">
//                <item>Black tv</item>
//                <item>Whiteboard</item>
//                <item>Teacher's table</item>
//            </string-array>
//        </resources>

        val stalls = arrayOf("Black tv", "Whiteboard")
        val blacktv1 = arrayOf("Aquarius", "Milo", "100plus", "Plain water")
        val blacktv2 = arrayOf("$1.80", "$1.20", "$1.60", "$0.90")
        val whiteboard1 = arrayOf("Oreo", "Famous Amos Cookie", "Subway Cookie", "Brownies")
        val whiteboard2 = arrayOf("$3.00", "$1.00", "$1.50", "$2.00")
        val spinner: Spinner = findViewById(R.id.spinner)
        val dataAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, stalls
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(dataAdapter)

//        fun addListenerOnSpinnerItemSelection() {
//            spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
//        }
    }
}
