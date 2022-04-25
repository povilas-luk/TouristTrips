package com.example.touristtrips.core.presentation.locations.location_map

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.touristtrips.R
import com.example.touristtrips.core.domain.util.requestFineLocationPermission
import com.example.touristtrips.databinding.FragmentLocationMapsBinding
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.presentation.locations.AddEditLocationViewModel
import com.example.touristtrips.feature_online_location.presentation.locations.LocationsViewModel
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

    private val requestCode = 2

    private var currentLocation: Location? = null

    private val callback = OnMapReadyCallback { googleMap ->
        this.map = googleMap

        enableMyLocation()

        if (currentLocation != null) {
            val latLng: LatLng? = if (currentLocation!!.location_search.isNotEmpty()) {
                searchGeoCoder(currentLocation!!.location_search)
            } else {
                LatLng(
                    currentLocation!!.latitude.toDouble(),
                    currentLocation!!.longitude.toDouble()
                )
            }
            if (latLng != null) {
                googleMap.addMarker(MarkerOptions().position(latLng).title(currentLocation!!.title))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10F))
            } else {
                Toast.makeText(
                    context,
                    "Location \"${currentLocation!!.title}\" data not found!",
                    Toast.LENGTH_SHORT
                ).show()
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (myLocationId != null) {
            myLocationsFragment()
        } else if (locationId != null) {
            locationsFragment()
        }
    }

    private fun mapReady() {
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
                        Toast.makeText(context, event.operation.toString(), Toast.LENGTH_SHORT)
                            .show()
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