package com.example.touristtrips.feature_location.presentation.all_locations_list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.touristtrips.R
import com.example.touristtrips.databinding.FragmentMyLocationsBinding
import com.example.touristtrips.core.presentation.epoxy.location_epoxy_model.LocationsEpoxyController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyLocationsFragment : Fragment() {
    private var _binding: FragmentMyLocationsBinding? = null
    private val binding get() = _binding!!

    private val myLocationsViewModel: MyLocationsViewModel by navGraphViewModels(R.id.my_locations_graph) { defaultViewModelProviderFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = LocationsEpoxyController(::itemSelected, textWatcher)
        binding.epoxyRecyclerView.setController(controller)

        myLocationsViewModel.locationsState.observe(viewLifecycleOwner) { locationState ->
            controller.locationsState = locationState
        }
    }

    private fun itemSelected(id: String) {
        findNavController().navigate(MyLocationsFragmentDirections.actionMyLocationsFragmentToLocationFragment(id))
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0 != null) {
                myLocationsViewModel.showLocationsWithText(p0.toString())
            }
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
        override fun afterTextChanged(p0: Editable?) { }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_my_locations_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuAdd) {
            findNavController().navigate(MyLocationsFragmentDirections.actionMyLocationsFragmentToAddLocationFragment())
            true
        } else if (item.itemId == R.id.menuSort) {
            findNavController().navigate(MyLocationsFragmentDirections.actionMyLocationsFragmentToSortBottomSheetMyLocationsFragment())
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