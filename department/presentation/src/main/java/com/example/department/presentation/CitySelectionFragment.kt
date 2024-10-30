package com.example.department.presentation

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.presentation.ui.base.BaseFragment
import com.example.department.presentation.databinding.FragmentCitySelectionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class CitySelectionFragment :
    BaseFragment<FragmentCitySelectionBinding>(FragmentCitySelectionBinding::inflate) {
    private val departmentsViewModel by activityViewModels<DepartmentsViewModel>()

    override fun setupView() {
        setupCitiesRecyclerView()
    }

    private fun setupCitiesRecyclerView() =
        binding.rvCities.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CityAdapter(
                cities = departmentsViewModel.state.value.cities,
                currentCity = departmentsViewModel.state.value.currentCity,
                onClick = { chosenCity -> processCityClick(chosenCity) }
            )
        }

    private fun processCityClick(city: String) {
        departmentsViewModel.setCurrentCity(city)
        findNavController().navigateUp()
    }

    override fun onClickButtonCancel() = Unit
}