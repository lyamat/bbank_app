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
import com.example.bbank.databinding.CitySelectionDialogBinding
import com.example.bbank.domain.models.City
import com.example.bbank.presentation.adapters.CityAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class CitySelectionDialog : DialogFragment() {
    private lateinit var cities: List<City>
    private lateinit var binding: CitySelectionDialogBinding
    private val departmentsViewModel by activityViewModels<DepartmentsViewModel>()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
            dialog.window!!.setWindowAnimations(R.style.AppTheme_Slide)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CitySelectionDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCitySelectionDialog()
        setupCitiesRecyclerView()
    }

    private fun setupCitySelectionDialog() {
        binding.apply {
            tbCitySelection.setNavigationOnClickListener { dismiss() }
            tbCitySelection.setTitle(getString(R.string.city_selection_dialog))
            tbCitySelection.setOnMenuItemClickListener {
                dismiss()
                true
            }
        }
    }

    private fun setupCitiesRecyclerView() {
        binding.rvCities.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CityAdapter(
                cities = cities,
                sharedPreferences = sharedPreferences, // TODO: remove
                onClick = { cityName -> processCityClick(cityName) }
            )
        }
    }

    private fun processCityClick(cityName: String) {
        sharedPreferences.edit().putString("currentCity", cityName).apply()
        departmentsViewModel.getLocalDepartmentsByCity()
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
            val jsonString = context.resources.openRawResource(R.raw.cities_array).bufferedReader()
                .use { it.readText() }
            val gson = Gson()
            val listType = object : TypeToken<List<String>>() {}.type
            val cityNames: List<String> = gson.fromJson(jsonString, listType)
            return cityNames.map { City(it) }
        }
    }
}