package com.example.touristtrips.feature_route.presentation.all_routes_list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.touristtrips.R
import com.example.touristtrips.databinding.FragmentMyRoutesBinding
import com.example.touristtrips.feature_route.presentation.routes_epoxy.RoutesEpoxyController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyRoutesFragment : Fragment() {
    private var _binding: FragmentMyRoutesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RoutesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyRoutesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = RoutesEpoxyController(::itemSelected)
        binding.epoxyRecyclerView.setController(controller)

        viewModel.routesState.observe(viewLifecycleOwner) { routeState ->
            controller.routesState = routeState
        }
    }

    private fun itemSelected(id: String) {
        findNavController().navigate(MyRoutesFragmentDirections.actionMyRoutesFragmentToRouteFragment(id))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuAdd) {
            findNavController().navigate(MyRoutesFragmentDirections.actionMyRoutesFragmentToAddEditRouteFragment())
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