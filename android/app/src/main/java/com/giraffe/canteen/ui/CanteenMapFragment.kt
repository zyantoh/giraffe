package com.giraffe.canteen.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.giraffe.R
import com.giraffe.canteen.data.CanteenRepository
import com.giraffe.canteen.model.TableStatus
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

        canteen_name_text_view.text = canteenMapViewModel.canteenName
        canteen_location_text_view.text = canteenMapViewModel.canteenLocation

        canteenMapViewModel.tables.observe(this, Observer {list ->
            list.forEach {
                it.observe(this, Observer { table ->
                    val viewId = when (table.tableId) {
                        "table_1" -> R.id.table_1_image_view
                        "table_2" -> R.id.table_2_image_view
                        "table_3" -> R.id.table_3_image_view
                        else -> R.id.table_1_image_view
                    }

                    val drawableId = when (table.status) {
                        TableStatus.CONFIDENT_TAKEN -> R.drawable.red
                        TableStatus.MAYBE_TAKEN -> R.drawable.orange
                        TableStatus.CONFIDENT_NOT_TAKEN -> R.drawable.green
                        TableStatus.UNKNOWN -> R.drawable.green
                    }

                    val drawable = ContextCompat.getDrawable(context!!, drawableId)
                    val imageView: ImageView = view.findViewById(viewId)
                    imageView.setImageDrawable(drawable)
                })
            }
        })

    }
}