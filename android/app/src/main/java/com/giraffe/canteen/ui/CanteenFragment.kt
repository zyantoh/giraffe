package com.giraffe.canteen.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.giraffe.R
import com.giraffe.canteen.data.CanteenRepository
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

        val name = arguments!!.getString("name")!!
        val location = arguments!!.getString("location")!!
        val thumbnail = Uri.parse(arguments!!.getString("thumbnail")!!)
        val totalTables = arguments!!.getLong("totalTables")

        name_text_view.text = name
        location_text_view.text = location
        Glide.with(context!!)
            .load(thumbnail)
            .into(thumbnail_image_view)
        occupancy_text_view.text = resources.getString(R.string.loading)

        canteenViewModel.canteenOccupancy.observe(this, Observer<Long> {
            occupancy_progressbar.progress = (it*100/totalTables).toInt()
            occupancy_text_view.text = resources.getString(R.string.occupancy, it, totalTables)
        })
    }
}