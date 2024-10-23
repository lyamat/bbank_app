package com.example.bbank.presentation.departments

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentCitySelectionBinding
import com.example.bbank.domain.models.City
import com.example.bbank.presentation.adapters.CityAdapter
import com.example.core.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class CitySelectionFragment :
    BaseFragment<FragmentCitySelectionBinding>(FragmentCitySelectionBinding::inflate) {
    // TODO: get cities... from fetched departments (departments.map { it.name }.filter { it.isNotBlank() }.distinct()
    private val cities by lazy { getCities(requireContext()) }
    private val departmentsViewModel by activityViewModels<DepartmentsViewModel>()
    private val currentCity by lazy { sharedPreferences.getString("currentCity", "") }

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun setupView() {
        setupCitiesRecyclerView()
    }

    private fun setupCitiesRecyclerView() =
        binding.rvCities.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CityAdapter(
                cities = cities,
                currentCity = currentCity,
                onClick = { cityName -> processCityClick(cityName) }
            )
        }

    private fun processCityClick(cityName: String) {
        departmentsViewModel.saveCity(cityName)
        findNavController().navigateUp()
    }


    private fun getCities(context: Context): List<City> =
        context.resources.getStringArray(R.array.cities_array).map { City(it) }

    override fun onClickButtonCancel() = Unit
}
