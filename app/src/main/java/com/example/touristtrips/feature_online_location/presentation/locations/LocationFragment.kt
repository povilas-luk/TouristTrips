package com.example.touristtrips.feature_online_location.presentation.locations

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.example.touristtrips.R
import com.example.touristtrips.databinding.FragmentLocationBinding
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.presentation.locations.AddEditLocationViewModel
import com.example.touristtrips.feature_location.presentation.locations.MyLocationFragmentArgs
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : Fragment() {
    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LocationsViewModel by navGraphViewModels(R.id.locations_graph)

    private val safeArgs: MyLocationFragmentArgs by navArgs()
    private val locationId: String by lazy {
        safeArgs.locationId
    }

    private lateinit var currentLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        //viewModel.getLocation(locationId)
    }

    override fun onResume() {
        super.onResume()
        //viewModel.getLocation(locationId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLocation(locationId)

        viewModel.locationsState.observe(viewLifecycleOwner) { locationState ->
            currentLocation = locationState.location
            displayLocation(currentLocation)
        }
    }

    private fun itemSelected(id: String) {

    }

    private fun displayLocation(location: Location) {
        binding.titleTextVIew.text = location.title
        binding.typeTextView.text = location.type
        binding.cityTextView.text = location.city
        binding.timeToVisitTextView.text = location.months_to_visit
        binding.descriptionTextView.text = location.description
        binding.priceTextView.text = location.price.toString()
        Picasso.get().load(Uri.parse(location.imageUrl)).placeholder(R.drawable.bruno_soares_284974).into(binding.headerImageView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_map_save, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuMap) {
            findNavController().navigate(LocationFragmentDirections.actionLocationFragmentToLocationMapsFragment(locationId = locationId))
            true
        } else if (item.itemId == R.id.menuSave) {
            saveCurrentLocation()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun saveCurrentLocation() {
        val myLocationsViewModel: AddEditLocationViewModel by viewModels()
        myLocationsViewModel.onEvent(AddEditLocationViewModel.AddEditLocationEvent.SaveLocation(currentLocation))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}