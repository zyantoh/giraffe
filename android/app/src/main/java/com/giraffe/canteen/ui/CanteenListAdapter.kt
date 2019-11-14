package com.giraffe.canteen.ui

import android.content.Context
import com.giraffe.R

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.giraffe.canteen.model.Canteen

class CanteenListAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context,
    private var canteens: List<Canteen>,
    private var canteenOccupancy: List<LiveData<Long>>,
    private val onItemClickListener: (Canteen) -> Unit
) : RecyclerView.Adapter<CanteenListAdapter.CanteenListViewHolder>() {

    class CanteenListViewHolder(canteenListView: View) : RecyclerView.ViewHolder(canteenListView) {
        val containerView: ConstraintLayout = canteenListView.findViewById(R.id.canteen_list_item_constraintlayout)
        val thumbnailImageView: ImageView = canteenListView.findViewById(R.id.thumbnail_image_view)
        val nameTextView: TextView = canteenListView.findViewById(R.id.name_text_view)
        val locationTextView: TextView = canteenListView.findViewById(R.id.location_text_view)
        val occupancyProgressBar: ProgressBar = canteenListView.findViewById(R.id.occupancy_progressbar)
        val occupancyTextView: TextView = canteenListView.findViewById(R.id.occupancy_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): CanteenListViewHolder {
        val canteenListView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_canteen_list_item, parent, false)

        return CanteenListViewHolder(canteenListView)
    }

    override fun onBindViewHolder(viewHolder: CanteenListViewHolder, position: Int) {
        val canteen = canteens[position]
        val occupancy = canteenOccupancy[position]

        // Set the onClickListener
        viewHolder.containerView.setOnClickListener {
            onItemClickListener(canteen)
        }

        Glide.with(viewHolder.thumbnailImageView.context)
            .load(canteen.thumbnailUri)
            .into(viewHolder.thumbnailImageView)
        viewHolder.nameTextView.text = canteen.name
        viewHolder.locationTextView.text = canteen.location
        viewHolder.occupancyTextView.text = context.getString(R.string.loading)

        occupancy.observe(lifecycleOwner, Observer<Long> {
            viewHolder.occupancyProgressBar.progress = (it*100/canteen.totalTables).toInt()
            viewHolder.occupancyTextView.text = context.resources.getString(R.string.occupancy, it, canteen.totalTables)
        })
    }

    override fun getItemCount(): Int = canteens.size

    fun setCanteens(newCanteenList: List<Canteen>, newCanteenOccupancy: List<LiveData<Long>>) {
        canteens = newCanteenList
        canteenOccupancy = newCanteenOccupancy
        notifyDataSetChanged()
    }
}

