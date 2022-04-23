package com.example.touristtrips.feature_online_location.presentation.locations

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.touristtrips.databinding.FragmentLocationsBinding
import com.example.touristtrips.feature_location.presentation.location_epoxy_model.LocationsEpoxyController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsFragment : Fragment() {
    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!

    private val locationsViewModel: LocationsViewModel by viewModels()

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

        val controller = LocationsEpoxyController(::itemSelected, textWatcher)
        binding.epoxyRecyclerView.setController(controller)

        locationsViewModel.getLocations()

        locationsViewModel.locationsState.observe(viewLifecycleOwner) { locationState ->
            locationsViewModel.setAllLocations()
            controller.locationsState = locationState
        }
    }

    private fun itemSelected(id: String) {
        findNavController().navigate(LocationsFragmentDirections.actionLocationsFragmentToLocationFragment2(id))
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0 != null) {
                locationsViewModel.showLocationsWithText(p0.toString())
            }
        }

        override fun afterTextChanged(p0: Editable?) {
            // nothing
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}