package com.giraffe.canteen.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.giraffe.R

class CanteenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_canteen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val canteenName = arguments!!.getString("name")!!
        val canteenLocation = arguments!!.getString("location")!!
        val canteenThumbnail = Uri.parse(arguments!!.getString("thumbnail")!!)

        view.findViewById<TextView>(R.id.name_text_view).text = canteenName
        view.findViewById<TextView>(R.id.location_text_view).text = canteenLocation
        Glide.with(context!!)
            .load(canteenThumbnail)
            .into(view.findViewById(R.id.thumbnail_image_view))
    }
}