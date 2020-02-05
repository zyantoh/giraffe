package com.giraffe.canteen.ui

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.giraffe.R
import com.giraffe.canteen.data.CanteenRepository
import com.giraffe.canteen.model.Canteen
import com.giraffe.canteen.model.Location
import com.google.android.material.snackbar.Snackbar
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

    private fun filterByLocation(currentLocation: Location, viewAdapter: CanteenListAdapter) {
        // Calculate the top 5 closest canteens
        if (canteenListViewModel.canteenList.value == null) {
            return
        }

        var closestCanteens = canteenListViewModel.canteenList.value!!.indices.sortedWith(
            compareBy({ canteenListViewModel.canteenList.value!![it].location.distance(currentLocation) })
        )

        if (closestCanteens.size > 3) {
            closestCanteens = closestCanteens.subList(0, 3)
        }

        val closestOccupancy = mutableListOf<LiveData<Long>>()
        val closestList = mutableListOf<Canteen>()
        closestCanteens.forEach {
            closestOccupancy.add(canteenListViewModel.canteenOccupancy[it])
            closestList.add(canteenListViewModel.canteenList.value!![it])
        }

        viewAdapter.setCanteens(closestList, closestOccupancy)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Start with no elements, update later when the data is ready
        val viewAdapter = CanteenListAdapter(this, context!!, mutableListOf(), mutableListOf()) {
            // Navigate to canteen fragment
            val args = Bundle()
            args.putParcelable("canteen", it)
            findNavController().navigate(R.id.action_canteenListFragment_to_canteenFragment, args)
        }

        // Set an observer on the canteen list LiveData for when the data is ready
        canteenListViewModel.canteenList.observe(this, Observer<List<Canteen>>{
            viewAdapter.setCanteens(it, canteenListViewModel.canteenOccupancy)
        })

        canteen_list_exp_list_view.setAdapter(viewAdapter)

        // Search
        val queryListener: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Filter the data shown
                val filteredOccupancy = mutableListOf<LiveData<Long>>()
                val filteredList = mutableListOf<Canteen>()

                canteenListViewModel.canteenList.value?.forEachIndexed { index, canteen ->
                    if (canteen.name.toLowerCase().contains(query?.toLowerCase() ?: "") && canteenListViewModel.canteenList.value != null) {
                        filteredOccupancy.add(canteenListViewModel.canteenOccupancy[index])
                        filteredList.add(canteenListViewModel.canteenList.value!![index])
                    }
                }

                viewAdapter.setCanteens(filteredList, filteredOccupancy)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        }
        canteen_list_searchView.setOnQueryTextListener(queryListener)

        // FAB
        canteen_list_fab.setOnClickListener {
            val snackbar = Snackbar.make(view, "Displaying canteens closest to you...", Snackbar.LENGTH_INDEFINITE)
            snackbar.show()

            val locationManager = context!!.getSystemService(LOCATION_SERVICE) as LocationManager
            val locationListener = object: LocationListener {
                override fun onLocationChanged(location: android.location.Location?) {
                    if (location == null) {
                        return
                    }

                    val currentLocation = Location(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )

                    filterByLocation(currentLocation, viewAdapter)
                    locationManager.removeUpdates(this)
                    snackbar.dismiss()
                }

                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

                override fun onProviderEnabled(p0: String?) {}

                override fun onProviderDisabled(p0: String?) {}
            }


            val permissionAccessFineLocationApproved = ActivityCompat
                .checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED

            if (permissionAccessFineLocationApproved) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0.0f, locationListener)
            } else {
                // App doesn't have access to the device's location at all. Make full request
                // for permission.
                ActivityCompat.requestPermissions(activity!!,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            }

        }
    }
}