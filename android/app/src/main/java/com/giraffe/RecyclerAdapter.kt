package com.giraffe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val titles = arrayOf("Classroom", "Food Club", "Poolside", "Makan Place", "Munch")

    private val details = arrayOf("Blk 31", "Blk 22", "Blk 18", "Blk 51", "Blk 73")

    private val images = intArrayOf(R.drawable.download, R.drawable.download,
        R.drawable.download, R.drawable.download, R.drawable.download)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var Image: ImageView
        var Title: TextView
        var Detail: TextView

        init {
            Image = itemView.findViewById(R.id.imageView)
            Title = itemView.findViewById(R.id.textView)
            Detail = itemView.findViewById(R.id.textView2)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_item, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.Title.text = titles[i]
        viewHolder.Detail.text = details[i]
        viewHolder.Image.setImageResource(images[i])
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}