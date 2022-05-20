package com.example.touristtrips.presentation.remote_locations.fragment

import android.os.Bundle
import android.view.*
import androidx.navigation.navGraphViewModels
import com.example.touristtrips.R
import com.example.touristtrips.domain.shared.model.SortOrder
import com.example.touristtrips.domain.shared.model.SortType
import com.example.touristtrips.databinding.FragmentSortOrderBottomSheetDialogBinding
import com.example.touristtrips.presentation.remote_locations.viewmodel.LocationsViewModel
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

        locationsViewModel.locationsState.observe(viewLifecycleOwner) {
            setSortRadioButtons()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}