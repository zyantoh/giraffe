package com.giraffe

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val japanesedishes = arrayOf(FoodItem("Chicken Curry Rice", "Rice with Japanese Curry topped with chicken cutlets", 4.0),
        FoodItem("Pork Curry Rice", "Rice with Japanese Curry topped with pork cutlets", 4.5),
        FoodItem("Omelette Rice", "Rice with Japanese Curry topped with omelette", 3.5))

    private val banmaindishes = arrayOf(FoodItem("Ban Mian", "Dough noodles dipped in soup",3.5),
        FoodItem("U-Mian", "Thin dough noodles dipped in soup",3.5),
        FoodItem("Fish Soup", "Soup containing fish",3.0))

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

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_item, viewGroup, false)
        return RecyclerAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: RecyclerAdapter.ViewHolder, i: Int) {

    }



    /*
    fun updateItems(newItems) {
        this.notifyDataSetChanged()
    }
    */


    override fun getItemCount(): Int {
        return japanesedishes.size
    }
}