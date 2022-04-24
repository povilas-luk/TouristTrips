package com.example.touristtrips.core.presentation.locations.location_map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.touristtrips.R
import com.example.touristtrips.core.domain.util.requestFineLocationPermission
import com.example.touristtrips.databinding.FragmentLocationMapsBinding
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.presentation.locations.AddEditLocationViewModel
import com.example.touristtrips.feature_online_location.presentation.locations.LocationsViewModel
import com.example.touristtrips.feature_online_route.presentation.routes.RoutesViewModel
import com.example.touristtrips.feature_route.presentation.route.AddEditRouteViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LocationMapsFragment : Fragment() {
    private var _binding: FragmentLocationMapsBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: LocationMapsFragmentArgs by navArgs()

    private val locationId: String? by lazy {
        safeArgs.locationId
    }

    private val myLocationId: String? by lazy {
        safeArgs.myLocationId
    }

    lateinit var map: GoogleMap

    private val requestCode = 2;

    private var currentLocation: Location? = null

    //private val addEditRoutesViewModel: AddEditRouteViewModel by viewModels()


    /*private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null

    // The entry point to the Places API.
    private lateinit var placesClient: PlacesClient

    // The entry point to the Fused Location Provider.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private var lastKnownLocation: Location? = null
    private var likelyPlaceNames: Array<String?> = arrayOfNulls(0)
    private var likelyPlaceAddresses: Array<String?> = arrayOfNulls(0)
    private var likelyPlaceAttributions: Array<List<*>?> = arrayOfNulls(0)
    private var likelyPlaceLatLngs: Array<LatLng?> = arrayOfNulls(0)*/


    override fun onResume() {
        super.onResume()
        /*if (myLocationId != null) {
            myLocationsFragment()
        } else if (locationId != null) {
            locationsFragment()
        }*/
    }

    //private lateinit var locationsList: List<Location>

    private val callback = OnMapReadyCallback { googleMap ->
        this.map = googleMap

        enableMyLocation()

        if (currentLocation != null) {
            val latLng: LatLng? = if (currentLocation!!.location_search.isNotEmpty()) {
                searchGeoCoder(currentLocation!!.location_search)
            } else {
                LatLng(currentLocation!!.latitude.toDouble(), currentLocation!!.longitude.toDouble())
            }
            if (latLng != null) {
                googleMap.addMarker(MarkerOptions().position(latLng).title(currentLocation!!.title))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10F))
            } else {
                Toast.makeText(context, "Location \"${currentLocation!!.title}\" data not found!", Toast.LENGTH_SHORT).show()
            }
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
        _binding = FragmentLocationMapsBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_route_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (myLocationId != null) {
            myLocationsFragment()
        } else if (locationId != null) {
            locationsFragment()
        }
    }

    private fun mapReady(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun myLocationsFragment() {
        val addEditLocationsViewModel: AddEditLocationViewModel by viewModels()

        addEditLocationsViewModel.getLocation(myLocationId!!)

        lifecycleScope.launchWhenCreated {
            addEditLocationsViewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is AddEditLocationViewModel.LocationEvent.Success -> {
                        Toast.makeText(context, event.operation.toString(), Toast.LENGTH_SHORT).show()
                        if (currentLocation == null) {
                            currentLocation = event.location
                        }
                        mapReady()
                    }
                    is AddEditLocationViewModel.LocationEvent.Failure -> {
                        Toast.makeText(context, event.errorText, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun locationsFragment() {
        val locationsViewModel: LocationsViewModel by viewModels()

        locationsViewModel.getLocation(locationId!!)

        locationsViewModel.locationsState.observe(viewLifecycleOwner) { locationState ->
            if (currentLocation == null) {
                currentLocation = locationState.location
            }
            mapReady()
        }

    }

    private fun enableMyLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
                map.isMyLocationEnabled = true
            } else {
                requestFineLocationPermission(requireActivity(), requestCode)
            }
        }
        else {
            map.isMyLocationEnabled = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                        map.isMyLocationEnabled = true
                    }
                }
                return
            }
        }
    }

    /*private fun getLocationPermission() {
        *//*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         *//*
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        *//*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         *//*
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                LatLng(lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        map?.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }
*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}