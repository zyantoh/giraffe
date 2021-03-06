package com.giraffe.canteen.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.giraffe.R
import com.giraffe.canteen.data.CanteenRepository
import com.giraffe.storage.storageModule
import kotlinx.android.synthetic.main.fragment_canteen_map.*
import org.koin.android.ext.android.inject

class CanteenMapFragment : Fragment() {
    private val canteenRepository: CanteenRepository by inject()
    private lateinit var canteenMapViewModel: CanteenMapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        canteenMapViewModel = ViewModelProviders.of(
            this,
            CanteenMapViewModelFactory(canteenRepository, this, arguments)
        ).get(CanteenMapViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_canteen_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        canteen_name_text_view.text = canteenMapViewModel.canteen.name

        canteenMapViewModel.mapUri.observe(this, Observer<Uri> {
            Glide.with(context!!)
                .load(it)
                .into(canteen_map_imageView)
        })
    }
}