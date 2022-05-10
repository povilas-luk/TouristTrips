package com.example.touristtrips.presentation.my_routes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.example.touristtrips.R
import com.example.touristtrips.domain.shared.util.SortOrder
import com.example.touristtrips.domain.shared.util.SortType
import com.example.touristtrips.databinding.FragmentSortOrderBottomSheetDialogBinding
import com.example.touristtrips.presentation.my_routes.viewmodel.MyRoutesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortBottomSheetMyRoutesFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSortOrderBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    private val myRoutesViewModel: MyRoutesViewModel by navGraphViewModels(R.id.my_routes_graph) { defaultViewModelProviderFactory }

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

        myRoutesViewModel.routesState.observe(viewLifecycleOwner) {
            setSortRadioButtons()
        }

    }

    private fun sortLocations(sortById: Int) {
        when (sortById) {
            binding.titleRadioButton.id -> myRoutesViewModel.sortRoutes(
                SortOrder.Title(
                    currentSortType
                )
            )
            binding.typeRadioButton.id -> myRoutesViewModel.sortRoutes(
                SortOrder.Type(
                    currentSortType
                )
            )
            binding.cityRadioButton.id -> myRoutesViewModel.sortRoutes(
                SortOrder.City(
                    currentSortType
                )
            )
            binding.timeToVisitRadioButton.id -> myRoutesViewModel.sortRoutes(
                SortOrder.TimeToVisit(
                    currentSortType
                )
            )
            else -> myRoutesViewModel.sortRoutes(SortOrder.Title(currentSortType))
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
        if (myRoutesViewModel.routesState.value?.sortOrder != null) {
            currentSortType = myRoutesViewModel.routesState.value?.sortOrder?.sortType!!
            currentSortOrder = myRoutesViewModel.routesState.value?.sortOrder!!
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