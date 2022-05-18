package com.example.touristtrips.presentation.shared.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.touristtrips.R
import com.example.touristtrips.databinding.DialogLocationSequenceBinding
import com.example.touristtrips.databinding.FragmentRouteMapsBinding
import com.example.touristtrips.domain.my_locations.model.Location
import com.example.touristtrips.presentation.remote_routes.viewmodel.RoutesViewModel
import com.example.touristtrips.presentation.my_routes.viewmodel.AddEditRouteViewModel
import com.example.touristtrips.presentation.shared.viewmodel.RouteMapsViewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class RouteMapsFragment : Fragment(), GoogleMap.OnMarkerClickListener {
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

    val addEditRoutesViewModel: AddEditRouteViewModel by viewModels()

    private var locationsList: List<Location> = emptyList()

    private val locationsMarkers = ArrayList<MarkerOptions>()
    private var routePolylines: List<PolylineOptions> = emptyList()
    private var routeDistance: Double = 0.0

    private var cameraPosition: CameraPosition? = null

    private val callback = OnMapReadyCallback { googleMap ->
        this.map = googleMap

        enableFineLocation()

        if (locationsMarkers.isNotEmpty()) {
            if (cameraPosition == null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationsMarkers[0].position, 10F))
                cameraPosition = googleMap.cameraPosition
            } else if (cameraPosition != null) {
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition!!))
            }

            locationsMarkers.forEach { locationMarker ->
                googleMap.addMarker(locationMarker)
            }
        }

        googleMap.setOnMarkerClickListener(this)

        if (routePolylines.isNotEmpty()) {
            routePolylines.forEach { polyline ->
                map.addPolyline(polyline)
            }
        }

        binding.distance.text = "%.2f".format(routeDistance)
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        if (myRouteId != null)
            locationsList.forEachIndexed { index, location ->
            if (p0.title == location.title) {
                locationSequenceDialog(location.locationId)
            }
        } else {
            p0.showInfoWindow()
        }
        return true
    }

    private fun locationSequenceDialog(id: String) {
        val dialogBinding: DialogLocationSequenceBinding = DialogLocationSequenceBinding.inflate(layoutInflater)

        val currentLocation = addEditRoutesViewModel.getLocation(id)
        val currentLocationIndex = addEditRoutesViewModel.getLocationIndex(currentLocation)

        dialogBinding.titleTextView.text = currentLocation.title
        Picasso.get().load(Uri.parse(currentLocation.imageUrl)).placeholder(R.drawable.bruno_soares_284974)
            .into(dialogBinding.headerImageView)

        val customDialog = AlertDialog.Builder(requireContext(), 0).create()

        customDialog.apply {
            setView(dialogBinding.root)
            setCancelable(true)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()

        dialogBinding.saveButton.setOnClickListener {
            val selectedSequence = dialogBinding.sequenceAutoCompleteTextView.text.toString().toIntOrNull()
            if (selectedSequence != null && selectedSequence - 1 != currentLocationIndex ) {
                saveCameraPosAndClearMap()
                addEditRoutesViewModel.onEvent(
                    AddEditRouteViewModel.AddEditRouteEvent.UpdateLocation(myRouteId!!, id, selectedSequence - 1)
                )
                customDialog.dismiss()
            }
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, addEditRoutesViewModel.getLocationsSizeArray())

        dialogBinding.sequenceAutoCompleteTextView.setAdapter(adapter)
        dialogBinding.sequenceAutoCompleteTextView.setText((currentLocationIndex + 1).toString(), false)
    }

    private fun saveCameraPosAndClearMap() {
        cameraPosition = map.cameraPosition
        map.clear()
    }

    private fun setLocationsMarkers(locations: List<Location>) {
        if (!locations.isNullOrEmpty()) {
            locationsList = locations
            locationsMarkers.clear()
            locations.forEach { location ->
                val latLng: LatLng? = if (location.latitude.isNotEmpty() && location.longitude.isNotEmpty()) {
                    LatLng(location.latitude.toDouble(), location.longitude.toDouble())
                } else {
                    searchGeoCoder(location.location_search)
                }
                if (latLng != null) {
                    locationsMarkers.add(MarkerOptions().position(latLng).title(location.title))
                } else {
                    Toast.makeText(
                        context,
                        "Location \"${location.title}\" data not found!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            routeMapsViewModel.getRouteDirections(locations)
        }
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

        routeMapsViewModel.routeDirections.observe(viewLifecycleOwner) { directions ->
            routePolylines = directions.map { it.polyline }
            routeDistance = directions.sumOf { it.distance }
            mapReady()
        }
    }

    private fun mapReady() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun myRoutesFragment() {

        addEditRoutesViewModel.getRouteWithLocations(myRouteId!!)

        addEditRoutesViewModel.locationsListLiveData.observe(viewLifecycleOwner) { locations ->
            setLocationsMarkers(locations)
        }
    }

    private fun routesFragment() {
        val routesViewModel: RoutesViewModel by viewModels()

        routesViewModel.getRouteWithLocationsId(routeId!!)
        routesViewModel.routeWithLocationsId.observe(viewLifecycleOwner) { _ ->
            if (locationsMarkers.isNullOrEmpty()) {
                routesViewModel.getRouteLocations()
            }
        }

        routesViewModel.routeLocations.observe(viewLifecycleOwner) { locationState ->
            if (locationsMarkers.isNullOrEmpty()) {
                setLocationsMarkers(locationState.locations)
            }
        }
    }

    private fun enableFineLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            fineLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            map.isMyLocationEnabled = true
        }
    }

    private val fineLocationPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            map.isMyLocationEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}