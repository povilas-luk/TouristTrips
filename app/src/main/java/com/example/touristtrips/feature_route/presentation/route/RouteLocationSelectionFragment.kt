package com.example.touristtrips.feature_route.presentation.route

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.touristtrips.R
import com.example.touristtrips.databinding.FragmentMyLocationsBinding
import com.example.touristtrips.databinding.FragmentMyRoutesBinding
import com.example.touristtrips.databinding.FragmentRouteLocationSelectionBinding
import com.example.touristtrips.feature_location.presentation.location_epoxy_model.LocationsEpoxyController
import com.example.touristtrips.feature_location.presentation.locations.LocationsViewModel
import com.example.touristtrips.feature_location.presentation.locations.MyLocationsFragmentDirections
import com.example.touristtrips.feature_route.presentation.all_routes_list.MyRoutesFragmentDirections
import com.example.touristtrips.feature_route.presentation.all_routes_list.RoutesViewModel
import com.example.touristtrips.feature_route.presentation.routes_epoxy.RoutesEpoxyController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RouteLocationSelectionFragment : Fragment() {
    private var _binding: FragmentMyLocationsBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: RouteFragmentArgs by navArgs()
    private val routeId: String by lazy {
        safeArgs.routeId
    }

    private val locationsViewModel: LocationsViewModel by viewModels()
    private val routesViewModel: AddEditRouteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionsMenu(true)
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

        val controller = LocationsEpoxyController(::itemSelected)
        binding.epoxyRecyclerView.setController(controller)

        locationsViewModel.locationsState.observe(viewLifecycleOwner) { locationState ->
            controller.locationsState = locationState
        }
    }

    private fun itemSelected(id: String) {
        routesViewModel.onEvent(AddEditRouteViewModel.AddEditRouteEvent.AddLocation(routeId, id))
        //findNavController().navigate(RouteLocationSelectionFragmentDirections.actionRouteLocationSelectionFragmentToRouteFragment(routeId = routeId, locationToAddId = id))
        findNavController().navigateUp()
    }

/*    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuAdd) {
            findNavController().navigate(MyLocationsFragmentDirections.actionMyLocationsFragmentToAddLocationFragment())
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}