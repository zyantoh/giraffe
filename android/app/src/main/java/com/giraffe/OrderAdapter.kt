package com.giraffe

import android.content.ClipData
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private val japanesedishes = arrayOf(FoodItem("Chicken Curry Rice", "Rice with Japanese Curry topped with chicken cutlets", 4.00),
        FoodItem("Pork Curry Rice", "Rice with Japanese Curry topped with pork cutlets", 4.50),
        FoodItem("Omelette Rice", "Rice with Japanese Curry topped with omelette", 3.50))

    private val banmaindishes = arrayOf(FoodItem("Ban Mian", "Dough noodles dipped in soup",3.50),
        FoodItem("U-Mian", "Thin dough noodles dipped in soup",3.50),
        FoodItem("Fish Soup", "Soup containing fish",3.00))

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var dishTitle: TextView
        var dishDesc: TextView
        var dishPrice: TextView

        init {
            dishTitle = itemView.findViewById(R.id.dishtitle)
            dishDesc = itemView.findViewById(R.id.dishdesc)
            dishPrice = itemView.findViewById(R.id.dishprice)
        }
    }


    class OrderAdapter(val items: List<FoodItem>, val listener: (ClipData.Item) -> Unit)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_orderitem, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.dishTitle.text = japanesedishes[i].dish
        holder.dishDesc.text = japanesedishes[i].description
        holder.dishPrice.text = "$" + japanesedishes[i].price.toString()

    }

    override fun getItemCount(): Int {
        return japanesedishes.size
    }

    /*
    fun updateItems(newItems) {
        this.notifyDataSetChanged()
    }
    */
}