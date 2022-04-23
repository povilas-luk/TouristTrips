package com.example.touristtrips.core.presentation.routes.route_map

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.touristtrips.R
import com.example.touristtrips.databinding.FragmentRouteMapsBinding
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_online_route.presentation.routes.RoutesViewModel
import com.example.touristtrips.feature_route.presentation.route.AddEditRouteViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RouteMapsFragment : Fragment() {
    private var _binding: FragmentRouteMapsBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: RouteMapsFragmentArgs by navArgs()

    private val routeId: String? by lazy {
        safeArgs.routeId
    }

    private val myRouteId: String? by lazy {
        safeArgs.myRouteId
    }

    lateinit var map: GoogleMap

    private val routeMapsViewModel: RouteMapsViewModel by viewModels()


    override fun onResume() {
        super.onResume()
        if (myRouteId != null) {
            myRoutesFragment()
        } else if (routeId != null) {
            routesFragment()
        }
    }

    private lateinit var locationsList: List<Location>

    private val callback = OnMapReadyCallback { googleMap ->
        this.map = googleMap

        if (!locationsList.isNullOrEmpty()) {
            val firstLocation =
                LatLng(locationsList[0].latitude.toDouble(), locationsList[0].longitude.toDouble())
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 12F))

            locationsList.forEach { location ->
                val latLng = LatLng(location.latitude.toDouble(), location.longitude.toDouble())
                googleMap.addMarker(MarkerOptions().position(latLng).title(location.title))
            }

            routeMapsViewModel.getDirectionsPolylines(locationsList)

            routeMapsViewModel.directionsPolylines.observe(viewLifecycleOwner) { polylines ->
                polylines.forEach { polyline ->
                    map.addPolyline(polyline)
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRouteMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun mapReady(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun myRoutesFragment() {
        val addEditRoutesViewModel: AddEditRouteViewModel by viewModels()

        addEditRoutesViewModel.getRouteWithLocations(myRouteId!!)

        addEditRoutesViewModel.locationsListLiveData.observe(viewLifecycleOwner) { locations ->
            locationsList = locations
            mapReady()
        }
    }

    private fun routesFragment() {
        val routesViewModel: RoutesViewModel by viewModels()

        routesViewModel.getRouteWithLocationsId(routeId!!)
        routesViewModel.routeWithLocationsId.observe(viewLifecycleOwner) { routeWithLocationsId ->
            routesViewModel.getRouteLocations()
        }

        routesViewModel.routeLocations.observe(viewLifecycleOwner) { locationState ->
            locationsList = locationState.locations
            mapReady()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}