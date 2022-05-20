package com.example.touristtrips.presentation.remote_routes.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.touristtrips.R
import com.example.touristtrips.databinding.FragmentRoutesBinding
import com.example.touristtrips.presentation.remote_routes.viewmodel.RoutesViewModel
import com.example.touristtrips.presentation.shared.epoxy.routes_epoxy.RoutesEpoxyController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutesFragment : Fragment() {
    private var _binding: FragmentRoutesBinding? = null
    private val binding get() = _binding!!

    private val routesViewModel: RoutesViewModel by navGraphViewModels(R.id.routes_graph) { defaultViewModelProviderFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoutesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = RoutesEpoxyController(::itemSelected, textWatcher)
        binding.epoxyRecyclerView.setController(controller)

        routesViewModel.getRoutes()

        routesViewModel.routesState.observe(viewLifecycleOwner) { routeState ->
            routesViewModel.setAllRoutes()
            controller.routesState = routeState
        }
    }

    private fun itemSelected(id: String) {
        findNavController().navigate(RoutesFragmentDirections.actionRoutesFragmentToRouteFragment(id))
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0 != null) {
                routesViewModel.showRoutesWithText(p0.toString())
            }
        }

        override fun afterTextChanged(p0: Editable?) {
            // nothing
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_routes_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuSort) {
            findNavController().navigate(RoutesFragmentDirections.actionRoutesFragmentToSortBottomSheetRoutesFragment())
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