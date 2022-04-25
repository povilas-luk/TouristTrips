package com.example.touristtrips.core.presentation.routes.route_map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.touristtrips.R
import com.example.touristtrips.core.domain.util.requestFineLocationPermission
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
    private val requestCode = 1

    private val routeMapsViewModel: RouteMapsViewModel by viewModels()


    private var locationsList: List<Location> = emptyList()
    private lateinit var updatedLocationsList: ArrayList<Location>

    private val callback = OnMapReadyCallback { googleMap ->
        this.map = googleMap

        updatedLocationsList = ArrayList()

        if (!locationsList.isNullOrEmpty()) {

            locationsList.forEachIndexed { index, location ->
                val latLng: LatLng? = if (location.location_search.isNotEmpty()) {
                    searchGeoCoder(location.location_search)
                } else {
                    LatLng(location.latitude.toDouble(), location.longitude.toDouble())
                }
                if (latLng != null) {
                    googleMap.addMarker(MarkerOptions().position(latLng).title(location.title))
                    addToUpdatedLocations(latLng)
                    if (index == 0) googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            latLng,
                            10F
                        )
                    )
                } else {
                    Toast.makeText(
                        context,
                        "Location \"${location.title}\" data not found!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            routeMapsViewModel.getDirectionsPolylines(updatedLocationsList)

            routeMapsViewModel.directionsPolylines.observe(viewLifecycleOwner) { polylines ->
                polylines.forEach { polyline ->
                    map.addPolyline(polyline)
                }
            }
        }
        enableFineLocation()
    }

    private fun addToUpdatedLocations(latLng: LatLng) {
        updatedLocationsList.add(
            Location(
                latitude = latLng.latitude.toString(),
                longitude = latLng.longitude.toString(),
            )
        )
    }

    private fun searchGeoCoder(search: String): LatLng? {
        val listGeoCoder = Geocoder(context).getFromLocationName(search, 1)
        return if (listGeoCoder.isNotEmpty()) {
            LatLng(listGeoCoder[0].latitude, listGeoCoder[0].longitude)
        } else {
            null
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
        if (myRouteId != null) {
            myRoutesFragment()
        } else if (routeId != null) {
            routesFragment()
        }
    }

    private fun mapReady() {
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
            if (locationsList.isNullOrEmpty()) {
                routesViewModel.getRouteLocations()
            }
        }

        routesViewModel.routeLocations.observe(viewLifecycleOwner) { locationState ->
            if (locationsList.isNullOrEmpty()) {
                locationsList = locationState.locations
            }
            mapReady()
        }
    }

    private fun enableFineLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                map.isMyLocationEnabled = true
            } else {
                requestFineLocationPermission(requireActivity(), requestCode)
            }
        } else {
            map.isMyLocationEnabled = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        map.isMyLocationEnabled = true
                    }
                }
                return
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}