package com.example.touristtrips.feature_route.presentation.route

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
import com.example.touristtrips.databinding.FragmentAddEditRouteBinding
import com.example.touristtrips.feature_route.domain.model.Route
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@AndroidEntryPoint
class AddEditRouteFragment : Fragment() {
    private var _binding: FragmentAddEditRouteBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: AddEditRouteFragmentArgs by navArgs()
    private val routeId: String? by lazy {
        safeArgs.routeId
    }
    private var editMode = false
    private lateinit var editRoute : Route

    private val viewModel: AddEditRouteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if (routeId != null) {
            viewModel.getRouteWithLocations(routeId!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEditRouteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            if (editMode) {
                viewModel.onEvent(AddEditRouteViewModel.AddEditRouteEvent.EditRoute(getRoute()))
            } else {
                viewModel.onEvent(AddEditRouteViewModel.AddEditRouteEvent.SaveRoute(getRoute()))
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is AddEditRouteViewModel.RouteEvent.Success -> {
                        if (event.operation == Operation.FOUND) {
                            setEditMode(event.route)
                        } else {
                            Toast.makeText(context, "Route ${event.operation.displayName}", Toast.LENGTH_SHORT).show()
                        }
                        if (event.operation == Operation.SAVED) {
                            findNavController().popBackStack()
                        }
                        if (event.operation == Operation.UPDATED) {
                            findNavController().popBackStack()
                        }
                    }
                    is AddEditRouteViewModel.RouteEvent.Failure -> {
                        Toast.makeText(context, event.errorText, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun setEditMode(route: Route) {
        editMode = true
        editRoute = route

        binding.typeEditText.setText(route.type)
        binding.titleEditText.setText(route.title)
        binding.descriptionEditText.setText(route.description)
        /*binding.latitudeEditText.setText(route.latitude)
        binding.longitudeEditText.setText(route.longitude)
        binding.cityEditText.setText(route.city)*/
        binding.imageUrlEditText.setText(route.imageUrl)
        binding.cityEditText.setText(route.city)
        binding.monthsToVisitEditText.setText(route.months_to_visit)

        binding.saveButton.text = getString(R.string.update)
    }

    private fun getRoute(): Route {
        if (editMode) {
            return editRoute.copy(
                type = binding.typeEditText.text.toString(),
                title = binding.titleEditText.text.toString(),
                description = binding.descriptionEditText.text.toString(),
                createdAt = System.currentTimeMillis(),
                imageUrl = binding.imageUrlEditText.text.toString(),
                city = binding.cityEditText.text.toString(),
                months_to_visit = binding.monthsToVisitEditText.text.toString(),
            )
        }
        return Route(
            routeId = UUID.randomUUID().toString(),
            type = binding.typeEditText.text.toString(),
            title = binding.titleEditText.text.toString(),
            description = binding.descriptionEditText.text.toString(),
            createdAt = System.currentTimeMillis(),
            imageUrl = binding.imageUrlEditText.text.toString(),
            city = binding.cityEditText.text.toString(),
            months_to_visit = binding.monthsToVisitEditText.text.toString(),
            price = getAllLocationsPrice()
        )
    }

    private fun getAllLocationsPrice(): Float {
        var price = 0F
        viewModel.locationsListLiveData.value?.forEach { location ->
            price += location.price
        }
        return price
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuDelete) {
            deleteLocation()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun deleteLocation() {

    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}