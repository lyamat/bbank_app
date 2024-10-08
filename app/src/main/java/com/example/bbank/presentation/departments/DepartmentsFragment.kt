package com.example.bbank.presentation.departments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentDepartmentsBinding
import com.example.bbank.presentation.adapters.DepartmentsAdapter
import com.example.bbank.presentation.utils.UiText
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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
        setupViewListeners()
        setupDepartmentsRecyclerView()
        observeDepartmentsUiState()
    }

    private fun setupViewListeners() =
        binding.apply {
            btnGetRemoteDepartments.setOnClickListener {
                departmentsViewModel.fetchRemoteDepartmentsByCity()
            }
            chipCity.setOnClickListener {
                showCitySelectionDialog()
            }
            chipIsWorking.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    (rvDepartments.adapter as DepartmentsAdapter).showOnlyOpenDepartments()
                } else {
                    (rvDepartments.adapter as DepartmentsAdapter).showAllDepartments()
                }
            }
        }

    private fun setupDepartmentsRecyclerView() =
        binding.rvDepartments.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = DepartmentsAdapter(
                departments = emptyList(),
                onClick = { department ->
                    val b = Bundle().apply { putParcelable("department", department) }
                    findNavController().navigate(
                        R.id.action_departmentsFragment_to_departmentDetails, b
                    )
                }
            )
        }

    private fun showCitySelectionDialog() =
        CitySelectionDialog.display(getParentFragmentManager(), requireContext())

    private fun observeDepartmentsUiState() =
        viewLifecycleOwner.lifecycleScope.launch {
            departmentsViewModel.departmentsUiState.collectLatest {
                handleDepartmentsUiState(it)
            }
        }

    private fun handleDepartmentsUiState(departmentsUiState: DepartmentsUiState) {
        val currentTime = SimpleDateFormat("HH:mm", Locale.UK).format(Date())
        val currentCity = departmentsUiState.currentCity
        binding.apply {
            chipCity.text = currentCity
            tvDepartmentsCity.text = getString(R.string.departments_in, currentCity, currentTime)
            (rvDepartments.adapter as DepartmentsAdapter).updateDepartments(departmentsUiState.departments)
        }
        departmentsUiState.error?.let {
            handleDepartmentsError(it)
        }
        if (departmentsUiState.isLoading)
            showLoading()
        else hideLoading()
    }

    private fun handleDepartmentsError(messageError: UiText) {
        departmentsViewModel.clearDepartmentsError()
        Snackbar.make(requireView(), messageError.asString(requireContext()), Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()
    }

    private fun hideLoading() {
        binding.btnGetRemoteDepartments.visibility = View.VISIBLE
        binding.progressIndicatorDepartments.visibility = View.GONE
    }

    private fun showLoading() {
        binding.btnGetRemoteDepartments.visibility = View.GONE
        binding.progressIndicatorDepartments.visibility = View.VISIBLE
    }
}