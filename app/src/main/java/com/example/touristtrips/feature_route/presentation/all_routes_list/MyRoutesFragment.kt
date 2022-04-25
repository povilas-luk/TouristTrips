package com.example.touristtrips.feature_route.presentation.all_routes_list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.touristtrips.R
import com.example.touristtrips.databinding.FragmentMyRoutesBinding
import com.example.touristtrips.core.presentation.epoxy.routes_epoxy.RoutesEpoxyController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyRoutesFragment : Fragment() {
    private var _binding: FragmentMyRoutesBinding? = null
    private val binding get() = _binding!!

    private val myRoutesViewModel: MyRoutesViewModel by navGraphViewModels(R.id.my_routes_graph) { defaultViewModelProviderFactory }

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

        val controller = RoutesEpoxyController(::itemSelected, textWatcher)
        binding.epoxyRecyclerView.setController(controller)

        myRoutesViewModel.routesState.observe(viewLifecycleOwner) { routeState ->
            controller.routesState = routeState
        }
    }

    private fun itemSelected(id: String) {
        findNavController().navigate(MyRoutesFragmentDirections.actionMyRoutesFragmentToRouteFragment(id))
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0 != null) {
                myRoutesViewModel.showRoutesWithText(p0.toString())
            }
        }

        override fun afterTextChanged(p0: Editable?) {
            // nothing
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_my_routes_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuAdd) {
            findNavController().navigate(MyRoutesFragmentDirections.actionMyRoutesFragmentToAddEditRouteFragment())
            true
        } else if (item.itemId == R.id.menuSort) {
            findNavController().navigate(MyRoutesFragmentDirections.actionMyRoutesFragmentToSortBottomSheetMyRoutesFragment())
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