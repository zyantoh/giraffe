package com.giraffe.canteen.ui

import android.content.Context
import android.text.Layout
import com.giraffe.R

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
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
) : BaseExpandableListAdapter() {
    private var headers: MutableList<String> = mutableListOf()
    private var groups: HashMap<String, MutableList<Int>> = hashMapOf()

    init {
        setCanteens(canteens, canteenOccupancy)
    }

    override fun getGroup(groupPosition: Int): Any = headers[groupPosition]

    override fun isChildSelectable(p0: Int, p1: Int): Boolean = true

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean,
                              convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater  = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.layout_canteen_list_group, parent, false)
        }

        val headerTextView = convertView!!.findViewById<TextView>(R.id.canteen_list_group_textView)
        val title = headers[groupPosition]
        headerTextView.text = title

        return convertView!!
    }

    override fun getChildrenCount(groupPosition: Int): Int = groups[headers[groupPosition]]!!.size

    override fun getChild(groupPosition: Int, childPosition: Int): Any = groups[headers[groupPosition]]!![childPosition]

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildView(groupPosition: Int, childPosition: Int,
                              isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val viewHolder: CanteenListViewHolder
        if (convertView == null) {
            val inflater  = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.layout_canteen_list_item, parent, false)

            viewHolder = CanteenListViewHolder(convertView)
        } else {
            viewHolder = convertView.tag as CanteenListViewHolder
        }

        setViewHolderValues(viewHolder, groups[headers[groupPosition]]!![childPosition])
        convertView!!.tag = viewHolder

        return convertView!!
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun getGroupCount(): Int = headers.size

    private inner class CanteenListViewHolder(canteenListView: View) : RecyclerView.ViewHolder(canteenListView) {
        val containerView: ConstraintLayout = canteenListView.findViewById(R.id.canteen_list_item_constraintlayout)
        val thumbnailImageView: ImageView = canteenListView.findViewById(R.id.thumbnail_image_view)
        val nameTextView: TextView = canteenListView.findViewById(R.id.name_text_view)
        val occupancyProgressBar: ProgressBar = canteenListView.findViewById(R.id.occupancy_progressbar)
        val occupancyTextView: TextView = canteenListView.findViewById(R.id.occupancy_text_view)
    }

    private fun setViewHolderValues(viewHolder: CanteenListViewHolder, canteenPosition: Int) {
        val canteen = canteens[canteenPosition]
        val occupancy = canteenOccupancy[canteenPosition]

        // Set the onClickListener
        viewHolder.containerView.setOnClickListener {
            onItemClickListener(canteen)
        }

        Glide.with(viewHolder.thumbnailImageView.context)
            .load(canteen.thumbnailUri)
            .into(viewHolder.thumbnailImageView)
        viewHolder.nameTextView.text = canteen.name
        viewHolder.occupancyTextView.text = context.getString(R.string.loading)

        occupancy.observe(lifecycleOwner, Observer<Long> {
            viewHolder.occupancyProgressBar.progress =
                (it*100/canteen.totalTables).toInt()
            viewHolder.occupancyTextView.text = context.resources.getString(
                R.string.occupancy, it, canteen.totalTables)
        })
    }

    fun setCanteens(newCanteenList: List<Canteen>, newCanteenOccupancy: List<LiveData<Long>>) {
        canteens = newCanteenList
        canteenOccupancy = newCanteenOccupancy
        headers = mutableListOf()

        // Group the canteens by their schools
        val schools = HashSet<String>()
        canteens.forEachIndexed { index, canteen ->
            val schoolName = canteen.school.name

            if (!schools.contains(schoolName)) {
                schools.add(schoolName)
                headers.add(schoolName)

                groups[schoolName] = mutableListOf()
            }

            groups[schoolName]!!.add(index)
        }

        notifyDataSetChanged()
    }
}

