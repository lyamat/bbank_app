package com.example.bbank.presentation.departments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.DialogCitySelectionBinding
import com.example.bbank.domain.models.City
import com.example.bbank.presentation.adapters.CityAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class CitySelectionDialog : DialogFragment() {
    private lateinit var cities: List<City>
    private lateinit var binding: DialogCitySelectionBinding
    private val departmentsViewModel by activityViewModels<DepartmentsViewModel>()
    private var currentCity: String? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
        currentCity = sharedPreferences.getString("currentCity", "")
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setWindowAnimations(R.style.AppTheme_Slide)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCitySelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCitySelectionDialog()
        setupCitiesRecyclerView()
    }

    private fun setupCitySelectionDialog() =
        binding.apply {
            tbCitySelection.setNavigationOnClickListener { dismiss() }
            tbCitySelection.setTitle(getString(R.string.city_selection_dialog))
            tbCitySelection.setOnMenuItemClickListener {
                dismiss()
                true
            }
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
        dialog?.dismiss()
    }

    internal companion object {
        private const val TAG = "city_selection_dialog"
        fun display(fragmentManager: FragmentManager?, context: Context): CitySelectionDialog {
            val citySelectionDialog = CitySelectionDialog()
            citySelectionDialog.cities = getCities(context)
            citySelectionDialog.show(fragmentManager!!, TAG)
            return citySelectionDialog
        }

        private fun getCities(context: Context): List<City> {
            val cityNames = context.resources.getStringArray(R.array.cities_array)
            return cityNames.map { City(it) }
        }
    }
}
