package com.example.touristtrips.feature_location.presentation.locations

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.touristtrips.R
import com.example.touristtrips.core.util.Operation
import com.example.touristtrips.databinding.FragmentLocationBinding
import com.example.touristtrips.feature_location.domain.model.Location
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MyLocationFragment : Fragment() {
    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    private val addEditLocationViewModel: AddEditLocationViewModel by viewModels()

    private val safeArgs: MyLocationFragmentArgs by navArgs()
    private val locationId: String by lazy {
        safeArgs.locationId
    }

    private lateinit var currentLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        addEditLocationViewModel.getLocation(locationId)
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

        lifecycleScope.launchWhenCreated {
            addEditLocationViewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is AddEditLocationViewModel.LocationEvent.Success -> {
                        if (event.operation == Operation.FOUND) {
                            currentLocation = event.location
                            displayLocation(currentLocation)
                        } else {
                            Toast.makeText(context, "Location ${event.operation.displayName}", Toast.LENGTH_SHORT).show()
                        }
                        if (event.operation == Operation.DELETED) {
                            findNavController().popBackStack()
                        }

                    }
                    is AddEditLocationViewModel.LocationEvent.Failure -> {
                        Toast.makeText(context, event.errorText, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
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
        inflater.inflate(R.menu.menu_map_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuEdit) {
            findNavController().navigate(MyLocationFragmentDirections.actionLocationFragmentToAddLocationFragment(locationId))
            true
        } else if (item.itemId == R.id.menuDelete) {
            addEditLocationViewModel.onEvent(AddEditLocationViewModel.AddEditLocationEvent.DeleteLocation(currentLocation))
            true
        } else if (item.itemId == R.id.menuMap) {
            findNavController().navigate(MyLocationFragmentDirections.actionMyLocationFragmentToLocationMapsFragment(myLocationId = locationId))
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}