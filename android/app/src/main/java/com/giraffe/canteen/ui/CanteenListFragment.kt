package com.giraffe.canteen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.giraffe.R
import com.giraffe.canteen.data.CanteenRepository
import com.giraffe.canteen.model.Canteen
import com.giraffe.database.DatabaseService
import com.giraffe.storage.StorageService
import kotlinx.android.synthetic.main.fragment_canteen_list.*

class CanteenListFragment(
    databaseService: DatabaseService,
    storageService: StorageService
) : Fragment() {
    private val canteenRepository = CanteenRepository(databaseService, storageService)
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
        val viewAdapter = CanteenListAdapter(listOf()) {
            val canteenName = it.findViewById<TextView>(R.id.name_text_view)
            // TODO: Go to activity
        }

        // Set an observer on the canteen list LiveData for when the data is ready
        canteenListViewModel.canteenList.observe(this, Observer<List<Canteen>>{
            viewAdapter.setCanteens(it)
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