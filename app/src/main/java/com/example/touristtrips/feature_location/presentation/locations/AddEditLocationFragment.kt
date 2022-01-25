package com.example.touristtrips.feature_location.presentation.locations

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.touristtrips.R
import com.example.touristtrips.databinding.FragmentAddEditLocationBinding
import com.example.touristtrips.databinding.FragmentMyLocationsBinding
import com.example.touristtrips.feature_location.domain.model.Location
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@AndroidEntryPoint
class AddEditLocationFragment : Fragment() {
    private var _binding: FragmentAddEditLocationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddEditLocationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEditLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            Log.i("Testing", "button clicked")
            viewModel.onEvent(AddEditLocationViewModel.AddEditLocationEvent.SaveLocation(getLocation()))
        }

        lifecycleScope.launchWhenCreated {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is LocationEvent.Success -> {
                        Toast.makeText(context, event.resultText, Toast.LENGTH_SHORT).show()
                    }
                    is LocationEvent.Failure -> {
                        Toast.makeText(context, event.errorText, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    fun getLocation(): Location {
         return Location(
             id = UUID.randomUUID().toString(),
             type = binding.typeEditText.text.toString(),
             title = binding.titleEditText.text.toString(),
             description = binding.descriptionEditText.text.toString(),
             latitude = binding.latitudeEditText.text.toString(),
             longitude = binding.longitudeEditText.text.toString(),
             city = binding.cityEditText.text.toString(),
             createdAt = System.currentTimeMillis(),
             imageUrl = binding.imageUrlEditText.text.toString(),
             months_to_visit = "March",
             price = 10F
        )
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}