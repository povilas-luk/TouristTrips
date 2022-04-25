package com.example.touristtrips.feature_route.presentation.route

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
import com.example.touristtrips.core.domain.util.Operation
import com.example.touristtrips.databinding.FragmentRouteBinding
import com.example.touristtrips.feature_route.domain.model.Route
import com.example.touristtrips.feature_route.presentation.route_locations_epoxy.RouteLocationsEpoxyController
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MyRouteFragment : Fragment() {
    private var _binding: FragmentRouteBinding? = null
    private val binding get() = _binding!!

    private val addEditRoutesViewModel: AddEditRouteViewModel by viewModels()

    private val safeArgs: MyRouteFragmentArgs by navArgs()

    private val routeId: String by lazy {
        safeArgs.routeId
    }

    private lateinit var currentRoute: Route

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        addEditRoutesViewModel.getRouteWithLocations(routeId)
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

        binding.locationHeaderImageView.setOnClickListener {
            findNavController().navigate(
                MyRouteFragmentDirections.actionRouteFragmentToRouteLocationSelectionFragment(
                    routeId
                )
            )
        }

        lifecycleScope.launchWhenStarted {
            addEditRoutesViewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is AddEditRouteViewModel.RouteEvent.Success -> {
                        if (event.operation == Operation.FOUND) {
                            currentRoute = event.route
                            displayRoute(currentRoute)
                        } else {
                            Toast.makeText(
                                context,
                                "Route ${event.operation.displayName}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (event.operation == Operation.DELETED) {
                            findNavController().navigateUp()
                        }

                    }
                    is AddEditRouteViewModel.RouteEvent.Failure -> {
                        Toast.makeText(context, event.errorText, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        val controller = RouteLocationsEpoxyController(::itemSelected, ::deleteItemSelected)
        binding.routeLocationsEpoxyRecyclerView.setController(controller)

        addEditRoutesViewModel.locationsListLiveData.observe(viewLifecycleOwner) { locations ->
            controller.locationsList = locations
            updatePrice()
        }
    }

    private fun deleteItemSelected(id: String) {
        addEditRoutesViewModel.onEvent(
            AddEditRouteViewModel.AddEditRouteEvent.DeleteLocation(
                routeId,
                id
            )
        )
    }

    private fun itemSelected(id: String) {
        findNavController().navigate(
            MyRouteFragmentDirections.actionRouteFragmentToLocationFragment(
                id
            )
        )
    }

    private fun displayRoute(route: Route) {
        binding.titleTextVIew.text = route.title
        binding.typeTextView.text = route.type
        binding.cityTextView.text = route.city
        binding.timeToVisitTextView.text = route.months_to_visit
        binding.descriptionTextView.text = route.description
        binding.priceTextView.text = route.price.toString()
        Picasso.get().load(Uri.parse(route.imageUrl)).placeholder(R.drawable.bruno_soares_284974)
            .into(binding.headerImageView)
    }

    private fun updatePrice() {
        binding.priceTextView.text = getAllLocationsPrice().toString()
    }

    private fun getAllLocationsPrice(): Float {
        var price = 0F
        addEditRoutesViewModel.locationsListLiveData.value?.forEach { location ->
            price += location.price
        }
        return price
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_map_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuEdit) {
            findNavController().navigate(
                MyRouteFragmentDirections.actionRouteFragmentToAddEditRouteFragment(
                    routeId
                )
            )
            true
        } else if (item.itemId == R.id.menuDelete) {
            addEditRoutesViewModel.onEvent(
                AddEditRouteViewModel.AddEditRouteEvent.DeleteRoute(
                    currentRoute
                )
            )
            true
        } else if (item.itemId == R.id.menuMap) {
            findNavController().navigate(
                MyRouteFragmentDirections.actionRouteFragmentToRouteMapsFragment(
                    myRouteId = routeId
                )
            )
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