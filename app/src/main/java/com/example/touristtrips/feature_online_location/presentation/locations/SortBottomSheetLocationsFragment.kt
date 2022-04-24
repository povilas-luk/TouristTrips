package com.example.touristtrips.feature_online_location.presentation.locations

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.navigation.navGraphViewModels
import com.example.touristtrips.R
import com.example.touristtrips.core.domain.util.SortOrder
import com.example.touristtrips.core.domain.util.SortType
import com.example.touristtrips.databinding.FragmentSortOrderBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortBottomSheetLocationsFragment: BottomSheetDialogFragment() {
    private var _binding: FragmentSortOrderBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    private val locationsViewModel: LocationsViewModel by navGraphViewModels(R.id.locations_graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var currentSortType: SortType = SortType.Descending
    private var currentSortOrder: SortOrder = SortOrder.Title(currentSortType)
    private var currentSortOrderId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSortOrderBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSortRadioButtons()

        binding.sortByRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            sortLocations(i)
        }

        binding.sortTypeGroup.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                binding.ascendingRadioButton.id -> {
                    currentSortType = SortType.Ascending
                    sortLocations(currentSortOrderId)
                }
                binding.descendingRadioButton.id -> {
                    currentSortType = SortType.Descending
                    sortLocations(currentSortOrderId)
                }
            }
        }

    }

    private fun sortLocations(sortById: Int) {
        when (sortById) {
            binding.titleRadioButton.id -> locationsViewModel.sortLocations(SortOrder.Title(currentSortType))
            binding.typeRadioButton.id -> locationsViewModel.sortLocations(SortOrder.Type(currentSortType))
            binding.cityRadioButton.id -> locationsViewModel.sortLocations(SortOrder.City(currentSortType))
            binding.timeToVisitRadioButton.id -> locationsViewModel.sortLocations(SortOrder.TimeToVisit(currentSortType))
            else -> locationsViewModel.sortLocations(SortOrder.Title(currentSortType))
        }
        setSortRadioButtons()
    }

    private fun getSortById(sortOrder: SortOrder): Int {
        return when (sortOrder) {
            is SortOrder.Title -> binding.titleRadioButton.id
            is SortOrder.City -> binding.cityRadioButton.id
            is SortOrder.TimeToVisit -> binding.timeToVisitRadioButton.id
            is SortOrder.Type -> binding.typeRadioButton.id
        }
    }

    private fun setSortRadioButtons() {
        if (locationsViewModel.locationsState.value?.sortOrder != null) {
            currentSortType = locationsViewModel.locationsState.value?.sortOrder?.sortType!!
            currentSortOrder = locationsViewModel.locationsState.value?.sortOrder!!
            currentSortOrderId = getSortById(currentSortOrder)

            when (currentSortType) {
                is SortType.Descending -> binding.descendingRadioButton.isChecked = true
                is SortType.Ascending -> binding.ascendingRadioButton.isChecked = true
            }
            when (currentSortOrder) {
                is SortOrder.City -> binding.cityRadioButton.isChecked = true
                is SortOrder.Type -> binding.typeRadioButton.isChecked = true
                is SortOrder.TimeToVisit -> binding.timeToVisitRadioButton.isChecked = true
                is SortOrder.Title -> binding.titleRadioButton.isChecked = true
            }
        }
    }

    /*private fun itemSelected(id: String) {
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
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sort, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuSort) {

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