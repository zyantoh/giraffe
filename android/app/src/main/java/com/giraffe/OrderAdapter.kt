package com.giraffe

import android.content.ClipData
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    private var dishes: List<FoodItem> = listOf()

    private val japanesedishes = listOf(FoodItem("Chicken Curry Rice", "Rice with Japanese Curry topped with chicken cutlets", 4.00),
        FoodItem("Pork Curry Rice", "Rice with Japanese Curry topped with pork cutlets", 4.50),
        FoodItem("Omelette Rice", "Rice with Japanese Curry topped with omelette", 3.50))

    private val banmaindishes = listOf(FoodItem("Ban Mian", "Dough noodles dipped in soup",3.50),
        FoodItem("U-Mian", "Thin dough noodles dipped in soup",3.50),
        FoodItem("Fish Soup", "Soup containing fish",3.00))

    private val foodstalldishes = listOf(japanesedishes,banmaindishes)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var dishTitle: TextView
        var dishDesc: TextView
        var dishPrice: TextView
        var quantity: TextView

        init {
            dishTitle = itemView.findViewById(R.id.dishtitle)
            dishDesc = itemView.findViewById(R.id.dishdesc)
            dishPrice = itemView.findViewById(R.id.dishprice)
            quantity = itemView.findViewById(R.id.quantity)
        }
    }


//    class AnotherAdapter(val items: List<FoodItem>, val listener: (ClipData.Item) -> Unit)
//    {
//        fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
//            val v = LayoutInflater.from(viewGroup.context)
//                .inflate(R.layout.layout_orderitem, viewGroup, false)
//            return ViewHolder(v)
//        }
//
//        fun onBindViewHolder(holder: ViewHolder, i: Int) {
//            holder.dishTitle.text = items[i].dish
//            holder.dishDesc.text = items[i].description
//            holder.dishPrice.text = "$" + items[i].price.toString()
//            holder.quantity.text = "0"
//        }
//
//        fun getItemCount(): Int {
//            return items.size
//        }
//    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_orderitem, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        if (dishes.count() != 0)
        {
            holder.dishTitle.text = dishes[i].dish
            holder.dishDesc.text = dishes[i].description
            holder.dishPrice.text = "$" + dishes[i].price.toString()
            holder.quantity.text = "0"
        }
        else {

        }
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    fun updateStall(newStall: String) {
        dishes = when (newStall) {
            "Japanese" -> japanesedishes
            "Ban Mian / Fish Soup" -> banmaindishes
            else -> listOf()
        }
        notifyDataSetChanged()
    }

    fun getDishes(): List<FoodItem> = dishes
}