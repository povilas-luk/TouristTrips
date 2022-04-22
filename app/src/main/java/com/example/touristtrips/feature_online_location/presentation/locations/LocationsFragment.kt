package com.example.touristtrips.feature_online_location.presentation.locations

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.touristtrips.R
import com.example.touristtrips.databinding.FragmentLocationsBinding
import com.example.touristtrips.feature_location.presentation.location_epoxy_model.LocationsEpoxyController
import com.example.touristtrips.feature_location.presentation.locations.MyLocationsViewModel
import com.example.touristtrips.feature_location.presentation.locations.MyLocationsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsFragment : Fragment() {
    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LocationsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = LocationsEpoxyController(::itemSelected)
        binding.epoxyRecyclerView.setController(controller)

        viewModel.getLocations()

        viewModel.locationsState.observe(viewLifecycleOwner) { locationState ->
            controller.locationsState = locationState
        }
    }

    private fun itemSelected(id: String) {
        findNavController().navigate(LocationsFragmentDirections.actionLocationsFragmentToLocationFragment2(id))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}