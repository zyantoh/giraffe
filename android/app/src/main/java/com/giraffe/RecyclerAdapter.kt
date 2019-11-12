package com.giraffe

import android.content.Intent
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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

        viewHolder.itemView.setOnClickListener { v: View  ->
            if (viewHolder.Title.text == "Classroom")
            {
                val intent = Intent( viewHolder.itemView.context, SelectActivity::class.java)
                val title = viewHolder.Title.text
                val detail = viewHolder.Detail.text
                intent.putExtra("title", title)
                intent.putExtra("detail", detail)
                viewHolder.itemView.context.startActivity(intent)
            }
            else {
                val intent = Intent( viewHolder.itemView.context, EmptyActivity::class.java)
                val message = "Development in progress"
                val title = viewHolder.Title.text
                val detail = viewHolder.Detail.text
                intent.putExtra("message", message)
                intent.putExtra("title", title)
                intent.putExtra("detail", detail)
                viewHolder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}