package com.example.touristtrips.feature_location.presentation.locations

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.touristtrips.R
import com.example.touristtrips.core.Operation
import com.example.touristtrips.databinding.FragmentAddEditLocationBinding
import com.example.touristtrips.feature_location.domain.model.Location
import com.example.touristtrips.feature_location.domain.util.LocalLocations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@AndroidEntryPoint
class AddEditLocationFragment : Fragment() {
    private var _binding: FragmentAddEditLocationBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: MyLocationFragmentArgs by navArgs()
    private val locationId: String by lazy {
        safeArgs.locationId
    }
    private var editMode = false
    private lateinit var editLocation : Location

    private val viewModel: AddEditLocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.getLocation(locationId)
    }

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
            if (editMode) {
                viewModel.onEvent(AddEditLocationViewModel.AddEditLocationEvent.EditLocation(getLocation()))
                findNavController().navigateUp()
            } else {
                viewModel.onEvent(AddEditLocationViewModel.AddEditLocationEvent.SaveLocation(getLocation()))
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is AddEditLocationViewModel.LocationEvent.Success -> {
                        if (event.operation == Operation.FOUND) {
                            setEditMode(event.location)
                        } else {
                            Toast.makeText(context, "Location ${event.operation}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    is AddEditLocationViewModel.LocationEvent.Failure -> {
                        Toast.makeText(context, event.errorText, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun setEditMode(location: Location) {
        editMode = true;
        editLocation = location

        binding.typeEditText.setText(location.type)
        binding.titleEditText.setText(location.title)
        binding.descriptionEditText.setText(location.description)
        binding.latitudeEditText.setText(location.latitude)
        binding.longitudeEditText.setText(location.longitude)
        binding.cityEditText.setText(location.city)
        binding.imageUrlEditText.setText(location.imageUrl)

        binding.saveButton.text = getString(R.string.update)
    }

    private fun getLocation(): Location {
        if (editMode) {
            return editLocation.copy(
                type = binding.typeEditText.text.toString(),
                title = binding.titleEditText.text.toString(),
                description = binding.descriptionEditText.text.toString(),
                latitude = binding.latitudeEditText.text.toString(),
                longitude = binding.longitudeEditText.text.toString(),
                city = binding.cityEditText.text.toString(),
                createdAt = System.currentTimeMillis(),
                imageUrl = binding.imageUrlEditText.text.toString(),
            )
        }
        return Location(
            locationId = UUID.randomUUID().toString(),
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_upload, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menuUpload) {
            uploadLocations()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun uploadLocations() {
        val locations = LocalLocations().getLocalLocations(requireContext())

        locations.forEach { location ->
            viewModel.onEvent(AddEditLocationViewModel.AddEditLocationEvent.SaveLocation(location))
        }

        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}