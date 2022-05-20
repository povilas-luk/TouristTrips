package com.example.touristtrips.presentation.shared.fragment.maps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.touristtrips.R
import com.example.touristtrips.databinding.DialogLocationSequenceBinding
import com.example.touristtrips.databinding.FragmentRouteMapsBinding
import com.example.touristtrips.presentation.shared.viewmodel.maps.RouteMapsViewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RouteMapsFragment : Fragment(), GoogleMap.OnMarkerClickListener {
    private var _binding: FragmentRouteMapsBinding? = null
    private val binding get() = _binding!!

    lateinit var map: GoogleMap

    private val routeMapsViewModel: RouteMapsViewModel by viewModels()

    private var cameraPosition: CameraPosition? = null

    private val callback = OnMapReadyCallback { googleMap ->
        this.map = googleMap

        enableFineLocation()

        map.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        if (routeMapsViewModel.routeMapState.value?.localRoute == true) {
            routeMapsViewModel.onMapEvent(RouteMapsViewModel.MapEvent.MarkerSelected(p0))
        } else {
            p0.showInfoWindow()
        }
        return true
    }

    private fun saveCameraPosition() {
        cameraPosition = map.cameraPosition
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

        mapReady()

        routeMapsViewModel.routeMapState.observe(viewLifecycleOwner) { routeMapState ->

            if (cameraPosition == null) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(routeMapState.routeMapData.markerDataList[0].markerOptions.position, 10F))
                cameraPosition = map.cameraPosition
            } else if (cameraPosition != null) {
                map.clear()
                map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition!!))
            }

            routeMapState.routeMapData.markerDataList.forEach { mapMarkerData ->
                mapMarkerData.markerOptions
                val marker = map.addMarker(mapMarkerData.markerOptions)
                marker?.tag = mapMarkerData.locationId
            }
            routeMapState.routeMapData.mapPolylineList.forEach { polylineOptions ->
                map.addPolyline(polylineOptions)
            }
            binding.distance.text = "%.2f".format(routeMapState.routeMapData.routeDistance)
        }

        lifecycleScope.launchWhenCreated {
            routeMapsViewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is RouteMapsViewModel.UiEvent.ShowLocationSequenceDialog -> {
                        openLocationSequenceDialog(event)
                    }
                }
            }
        }
    }

    private fun mapReady() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun openLocationSequenceDialog(locationSequenceData: RouteMapsViewModel.UiEvent.ShowLocationSequenceDialog) {
        val dialogBinding: DialogLocationSequenceBinding = DialogLocationSequenceBinding.inflate(layoutInflater)

        dialogBinding.titleTextView.text = locationSequenceData.locationTitle
        Picasso.get().load(Uri.parse(locationSequenceData.locationImageUrl)).placeholder(R.drawable.bruno_soares_284974)
            .into(dialogBinding.headerImageView)

        val customDialog = AlertDialog.Builder(requireContext(), 0).create()

        customDialog.apply {
            setView(dialogBinding.root)
            setCancelable(true)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()

        dialogBinding.saveButton.setOnClickListener {
            val selectedSequence = dialogBinding.sequenceAutoCompleteTextView.text.toString().toIntOrNull()
            if (selectedSequence != null && selectedSequence -1 != locationSequenceData.sequence) {
                saveCameraPosition()
                routeMapsViewModel.onMapEvent(RouteMapsViewModel.MapEvent.LocationSequenceChanged(locationSequenceData.locationId, selectedSequence - 1))
                customDialog.dismiss()
            }
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, locationSequenceData.locationsSequenceArray)

        dialogBinding.sequenceAutoCompleteTextView.setAdapter(adapter)
        dialogBinding.sequenceAutoCompleteTextView.setText((locationSequenceData.sequence + 1).toString(), false)
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