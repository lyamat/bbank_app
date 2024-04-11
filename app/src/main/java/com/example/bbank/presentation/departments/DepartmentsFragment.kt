package com.example.bbank.presentation.departments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentDepartmentsBinding
import com.example.bbank.domain.models.Department
import com.example.bbank.presentation.adapters.DepartmentsAdapter
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
internal class DepartmentsFragment : Fragment() {
    private lateinit var binding: FragmentDepartmentsBinding
    private val departmentsViewModel by activityViewModels<DepartmentsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDepartmentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onStartExchangesFragment()
        observeExchangesEvent()
        setupRecyclerViews()
    }

    private fun onStartExchangesFragment() {
        departmentsViewModel.getCurrentCity()

        binding.apply {
            btnGetRemoteDepartments.setOnClickListener {
                departmentsViewModel.getRemoteDepartmentsByCity("") // TODO: in future make with city?
//                exchangesViewModel.getRemoteExchangesByCity(sharedPreferences.getString("currentCity", "") ?: "")
            }
            btnGetLocalDepartments.setOnClickListener {
                departmentsViewModel.getLocalDepartmentsByCity()
            }

            chipCity.setOnClickListener {
                openCitySelectionDetailDialog()
            }
        }
    }

    private fun setupRecyclerViews() {
        binding.rvDepartments.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = DepartmentsAdapter(
                context = requireContext(),
                departments = emptyList(),
                onClick = { } // TODO: add DepartmentDetailFragment
            )
            val divider =
                MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }
    }

    private fun openCitySelectionDetailDialog() {
        CitySelectionDialog.display(getParentFragmentManager(), requireContext())
    }

    private fun observeExchangesEvent() {
        CoroutineScope(Dispatchers.Main).launch {
            departmentsViewModel.departmentsFlow().collect {
                processExchangeEvent(it)
            }
        }
    }

    private fun processExchangeEvent(departmentsEvent: DepartmentsViewModel.DepartmentsEvent) {
        when (departmentsEvent) {
            is DepartmentsViewModel.DepartmentsEvent.DepartmentsSuccess -> {
                handleSuccess(departmentsEvent.departments)
                hideLoading()
            }

            is DepartmentsViewModel.DepartmentsEvent.Loading -> {
                showLoading()
            }

            is DepartmentsViewModel.DepartmentsEvent.Error -> {
                handleError(departmentsEvent.message)
                hideLoading()
            }

            is DepartmentsViewModel.DepartmentsEvent.CitySuccess -> {
                handleCitySuccess(departmentsEvent.cityName)
                hideLoading()
            }

            else -> Unit
        }
    }

    private fun handleCitySuccess(cityName: String) {
        binding.chipCity.text = cityName
    }

    private fun handleSuccess(departments: List<Department>) {
        if (departments.isEmpty()) {
            handleError(getString(R.string.empty_department_list))
        } else {
            val currentTime = SimpleDateFormat("HH:mm", Locale.UK).format(Date())
            val currentCity = departments[0].name

            binding.apply {
                chipCity.text = currentCity
                tvDepartmentsCity.text = "Отделения в $currentCity\n$currentTime"
                (rvDepartments.adapter as DepartmentsAdapter).updateDepartments(
                    departments
                )
            }
        }
    }

    private fun handleError(error: String) {
        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()
    }

    private fun hideLoading() {
        binding.progressIndicatorDepartments.visibility = View.GONE
    }

    private fun showLoading() {
        binding.progressIndicatorDepartments.visibility = View.VISIBLE
    }
}