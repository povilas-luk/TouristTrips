package com.example.touristtrips.feature_route.presentation.route

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.touristtrips.core.util.Operation
import com.example.touristtrips.databinding.FragmentMyLocationsBinding
import com.example.touristtrips.feature_location.presentation.location_epoxy_model.LocationsEpoxyController
import com.example.touristtrips.feature_location.presentation.locations.MyLocationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RouteLocationSelectionFragment : Fragment() {
    private var _binding: FragmentMyLocationsBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: MyRouteFragmentArgs by navArgs()
    private val routeId: String by lazy {
        safeArgs.routeId
    }

    private val myLocationsViewModel: MyLocationsViewModel by viewModels()
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

        lifecycleScope.launchWhenCreated{
            routesViewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is AddEditRouteViewModel.RouteEvent.Success -> {
                        Toast.makeText(context, "Route ${event.operation.displayName}", Toast.LENGTH_SHORT).show()
                        if (event.operation == Operation.RL_ADDED) {
                            findNavController().navigateUp()
                        }
                    }
                    is AddEditRouteViewModel.RouteEvent.Failure -> {
                        Toast.makeText(context, event.errorText, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        val controller = LocationsEpoxyController(::itemSelected, textWatcher)
        binding.epoxyRecyclerView.setController(controller)

        myLocationsViewModel.locationsState.observe(viewLifecycleOwner) { locationState ->
            controller.locationsState = locationState
        }
    }

    private fun itemSelected(id: String) {
        routesViewModel.onEvent(AddEditRouteViewModel.AddEditRouteEvent.AddLocation(routeId, id))
        //findNavController().navigate(RouteLocationSelectionFragmentDirections.actionRouteLocationSelectionFragmentToRouteFragment(routeId = routeId, locationToAddId = id))
        //findNavController().navigateUp()
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0 != null) {
                myLocationsViewModel.showLocationsWithText(p0.toString())
            }
        }

        override fun afterTextChanged(p0: Editable?) {
            // nothing
        }

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