package com.giraffe.canteen.ui

import com.giraffe.R

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.giraffe.canteen.model.Canteen

class CanteenListAdapter(
    private var canteens: List<Canteen>,
    private val onItemClickListener: (Canteen) -> Unit
) : RecyclerView.Adapter<CanteenListAdapter.CanteenListViewHolder>() {

    class CanteenListViewHolder(canteenListView: View) : RecyclerView.ViewHolder(canteenListView) {
        val containerView: ConstraintLayout = canteenListView.findViewById(R.id.canteen_list_item_constraintlayout)
        val thumbnailImageView: ImageView = canteenListView.findViewById(R.id.thumbnail_image_view)
        val nameTextView: TextView = canteenListView.findViewById(R.id.name_text_view)
        val locationTextView: TextView = canteenListView.findViewById(R.id.location_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): CanteenListViewHolder {
        val canteenListView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_canteen_list_item, parent, false)

        return CanteenListViewHolder(canteenListView)
    }

    override fun onBindViewHolder(viewHolder: CanteenListViewHolder, position: Int) {
        val canteen = canteens[position]

        // Set the onClickListener
        viewHolder.containerView.setOnClickListener {
            onItemClickListener(canteen)
        }

        Glide.with(viewHolder.thumbnailImageView.context)
            .load(canteen.thumbnailUri)
            .into(viewHolder.thumbnailImageView)
        viewHolder.nameTextView.text = canteen.name
        viewHolder.locationTextView.text = canteen.location
    }

    override fun getItemCount(): Int = canteens.size

    fun setCanteens(newCanteenList: List<Canteen>) {
        canteens = newCanteenList
        notifyDataSetChanged()
    }
}

