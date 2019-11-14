package com.giraffe.canteen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.giraffe.R
import com.giraffe.canteen.data.CanteenRepository
import com.giraffe.canteen.model.Canteen
import kotlinx.android.synthetic.main.fragment_canteen_list.*
import org.koin.android.ext.android.inject

class CanteenListFragment : Fragment() {
    private val canteenRepository: CanteenRepository by inject()
    private lateinit var canteenListViewModel: CanteenListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        canteenListViewModel = ViewModelProviders.of(
            this,
            CanteenListViewModelFactory(canteenRepository, this, arguments)
        ).get(CanteenListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_canteen_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Prepare for canteen list RecyclerView
        val viewManager = LinearLayoutManager(context)
        // Start with no elements, update later when the data is ready
        val viewAdapter = CanteenListAdapter(this, context!!, listOf(), canteenListViewModel.canteenOccupancy) {
            // Navigate to canteen fragment
            val args = Bundle()
            args.putString("name", it.name)
            args.putString("location", it.location)
            args.putString("thumbnail", it.thumbnailUri.toString())
            args.putLong("totalTables", it.totalTables)
            findNavController().navigate(R.id.action_canteenListFragment_to_canteenFragment, args)
        }

        // Set an observer on the canteen list LiveData for when the data is ready
        canteenListViewModel.canteenList.observe(this, Observer<List<Canteen>>{
            viewAdapter.setCanteens(it, canteenListViewModel.canteenOccupancy)
        })

        canteen_list_recycler_view.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        // Add dividers to RecyclerView
        val dividerItemDecoration = DividerItemDecoration(
            canteen_list_recycler_view.context, viewManager.orientation)
        canteen_list_recycler_view.addItemDecoration(dividerItemDecoration)
    }
}