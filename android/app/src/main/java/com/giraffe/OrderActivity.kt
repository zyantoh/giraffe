package com.giraffe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.layout_orderitem.*

class OrderActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<OrderAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        //setContentView(R.layout.layout_orderitem)

        layoutManager = LinearLayoutManager(this)
        rv_order.layoutManager = layoutManager

        adapter = OrderAdapter()
        rv_order.adapter = adapter

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

        val stalls = arrayOf("Japanese",
                            "Ban Mian / Fish Soup",
                            "Mala Hotpot",
                            "Econ Rice",
                            "Pastry",
                            "Western",
                            "Canopy Coffee Club",
                            "Asian Delight",
                            "Salad Bar",
                            "Chicken Rice",
                            "Korean",
                            "Yogurt",
                            "Indonesian")



        val spinner: Spinner = findViewById(R.id.spinner)
        val dataAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            stalls
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(dataAdapter)
/*
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val dishTitle: TextView
                dishTitle = findViewById(R.id.dishtitle)
                val dishDesc: TextView
                dishDesc = findViewById(R.id.dishdesc)
                val dishPrice: TextView
                dishPrice = findViewById(R.id.dishprice)

                if (stalls[position] == "Japanese")
                {
                    for (item in japanesedishes)
                    {
                        dishTitle.text = item.dish
                        dishDesc.text = item.description
                        dishPrice.text = "$" + item.price.toString()
                    }
                }
                if (stalls[position] == "Ban Mian / Fish Soup")
                {
                    for (item in banmaindishes)
                    {
                        dishTitle.text = item.dish
                        dishDesc.text = item.description
                        dishPrice.text = "$" + item.price.toString()
                    }
                }
            }
        }
*/
    }
}
