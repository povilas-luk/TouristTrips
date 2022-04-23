package com.example.touristtrips.feature_online_route.presentation.routes

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.touristtrips.R
import com.example.touristtrips.databinding.FragmentRouteBinding
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.presentation.locations.AddEditLocationViewModel
import com.example.touristtrips.feature_location.presentation.locations.MyLocationsViewModel
import com.example.touristtrips.feature_route.domain.model.Route
import com.example.touristtrips.feature_route.presentation.route.AddEditRouteViewModel
import com.example.touristtrips.feature_route.presentation.route.MyRouteFragmentArgs
import com.example.touristtrips.feature_route.presentation.route.MyRouteFragmentDirections
import com.example.touristtrips.feature_route.presentation.route_locations_epoxy.RouteLocationsEpoxyController
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RouteFragment : Fragment() {
    private var _binding: FragmentRouteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RoutesViewModel by viewModels()

    private val safeArgs: MyRouteFragmentArgs by navArgs()

    private val routeId: String by lazy {
        safeArgs.routeId
    }

    /*private val locationToAddId: String? by lazy {
        safeArgs.locationToAddId
    }
*/
    private lateinit var currentRoute: Route
    private lateinit var routeLocations: List<Location>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        //addEditRoutesViewModel.getRouteWithLocations(routeId)
    }

    override fun onResume() {
        super.onResume()
        //addEditRoutesViewModel.getRouteWithLocations(routeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRouteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = RouteLocationsEpoxyController(::itemSelected, ::deleteItemSelected)
        binding.routeLocationsEpoxyRecyclerView.setController(controller)

        viewModel.getRouteWithLocationsId(routeId)
        viewModel.routeWithLocationsId.observe(viewLifecycleOwner) { routeWithLocationsId ->
            currentRoute = routeWithLocationsId.route
            displayRoute(currentRoute)
            viewModel.getRouteLocations()
        }

        viewModel.routeLocations.observe(viewLifecycleOwner) { locationState ->
            routeLocations = locationState.locations
            controller.locationsList = routeLocations
        }

    }

    private fun deleteItemSelected(id: String) {}

    private fun itemSelected(id: String) {}

    private fun displayRoute(route: Route) {
        binding.titleTextVIew.text = route.title
        binding.typeTextView.text = route.type
        binding.cityTextView.text = route.city
        binding.timeToVisitTextView.text = route.months_to_visit
        binding.descriptionTextView.text = route.description
        Picasso.get().load(Uri.parse(route.imageUrl)).into(binding.headerImageView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_map_save, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuMap) {
            findNavController().navigate(RouteFragmentDirections.actionRouteFragmentToRouteMapsFragment2(routeId = routeId))
            true
        } else if (item.itemId == R.id.menuSave) {
            saveCurrentRoute()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun saveCurrentRoute() {
        val myRoutesViewModel: AddEditRouteViewModel by viewModels()
        val myLocationsViewModel: AddEditLocationViewModel by viewModels()
        myRoutesViewModel.onEvent(AddEditRouteViewModel.AddEditRouteEvent.SaveRoute(currentRoute))
        routeLocations.forEach { location ->
            myLocationsViewModel.onEvent(AddEditLocationViewModel.AddEditLocationEvent.SaveLocation(location))
            myRoutesViewModel.onEvent(AddEditRouteViewModel.AddEditRouteEvent.AddLocation(currentRoute.routeId, location.locationId))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}