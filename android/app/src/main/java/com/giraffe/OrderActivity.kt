package com.giraffe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.layout_orderitem.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.*


fun Double.format(digits: Int) = "%.${digits}f".format(this)

class OrderActivity : AppCompatActivity() {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        //setContentView(R.layout.layout_orderitem)

        val retrieve1 = intent.getStringExtra("title")
        val title: TextView
        title = findViewById(R.id.textView10)
        title.setText(retrieve1)
        val retrieve2 = intent.getStringExtra("detail")
        val detail: TextView
        detail = findViewById(R.id.textView11)
        detail.setText(retrieve2)

        var finaltext: String = ""
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



        val dataAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            stalls
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(dataAdapter)

        val orderAdapter = OrderAdapter()
        rv_order.adapter = orderAdapter
        layoutManager = LinearLayoutManager(this)
        rv_order.layoutManager = layoutManager

        button4.setOnClickListener {
            val dishlist: List<FoodItem> = orderAdapter.getDishes();
            val qtylist: MutableList<Int> = mutableListOf()
            var finalprice: Double = 0.00
            finaltext = ""
            for (i in 0 until orderAdapter.itemCount) {
                val editText = layoutManager.findViewByPosition(i)!!.findViewById<EditText>(R.id.quantity).text.toString().toInt()
                qtylist.add(editText)
            }
            for (i in 0 until orderAdapter.itemCount) {
                if (qtylist[i] != 0) {
                    val subprice: Double = qtylist[i]*dishlist[i].price
                    finaltext += "${dishlist[i].dish}\n${dishlist[i].description}\n" +
                            "${qtylist[i]} X \$${dishlist[i].price.format(2)} = \$${subprice.format(2)}\n\n"
                    finalprice += subprice
                }
            }
            finaltext += "Total = \$${finalprice.format(2)}"
            val intent = Intent( this, CheckoutActivity::class.java)
            intent.putExtra("title", title.text)
            intent.putExtra("detail", detail.text)
            intent.putExtra("final",finaltext)
            this.startActivity(intent)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val spinnertext: String = spinner.selectedItem.toString()

                orderAdapter.updateStall(spinnertext)
                //adapter
            }
        }
//        fun updateStall(newStall: String) {
//            dishes = when (newStall) {
//                "Japanese" -> japanesedishes
//                "Ban Mian / Fish Soup" -> banmaindishes
//                else -> listOf()
//            }
//            adapter.notifyDataSetChanged()
//        }
    }
}
