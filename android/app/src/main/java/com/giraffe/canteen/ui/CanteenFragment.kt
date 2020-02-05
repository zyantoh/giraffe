package com.giraffe.canteen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.giraffe.R
import com.giraffe.canteen.data.CanteenRepository
import com.giraffe.canteen.model.Canteen
import kotlinx.android.synthetic.main.fragment_canteen.*
import org.koin.android.ext.android.inject

class CanteenFragment : Fragment() {
    private val canteenRepository: CanteenRepository by inject()
    private lateinit var canteenViewModel: CanteenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        canteenViewModel = ViewModelProviders.of(
            this,
            CanteenViewModelFactory(canteenRepository, this, arguments)
        ).get(CanteenViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_canteen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup the progress bar
        val canteen: Canteen = arguments!!.getParcelable("canteen")!!

        name_text_view.text = canteen.name
        Glide.with(context!!)
            .load(canteen.thumbnailUri)
            .into(thumbnail_image_view)
        occupancy_text_view.text = resources.getString(R.string.loading)

        canteenViewModel.canteenOccupancy.observe(this, Observer<Long> {
            occupancy_progressbar.progress = (it*100/canteen.totalTables).toInt()
            occupancy_text_view.text = resources.getString(R.string.occupancy, it, canteen.totalTables)
        })

        // Set the button on click listener
        view_map_button.setOnClickListener {
            findNavController().navigate(R.id.action_canteenFragment_to_canteenMapFragment, arguments)
        }

        if (canteen.stalls != null) {
            val stallNames = canteen.stalls.map { it.name }
            canteen_stall_list_recyclerView.adapter =
                ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, stallNames)
        }
    }
}