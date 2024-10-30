package com.example.department.presentation

import android.net.Uri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.presentation.ui.base.BaseFragment
import com.example.department.presentation.databinding.FragmentDepartmentsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
internal class DepartmentsFragment :
    BaseFragment<FragmentDepartmentsBinding>(FragmentDepartmentsBinding::inflate) {

    private val departmentsViewModel by activityViewModels<DepartmentsViewModel>()

    override fun setupView() {
        setViewsClickListeners()
        setupDepartmentsRecyclerView()
        observeDepartmentsState()
    }

    override fun onClickButtonCancel() =
        departmentsViewModel.cancelCurrentFetching()

    private fun setViewsClickListeners() =
        binding.apply {
            chipCity.setOnClickListener {
                openCitySelectionFragment()
            }
            chipIsWorking.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    (rvDepartments.adapter as DepartmentsAdapter).showOpenDepartments()
                } else {
                    (rvDepartments.adapter as DepartmentsAdapter).showAllDepartments()
                }
            }
            btnGetRemoteDepartments.setOnClickListener {
                departmentsViewModel.fetchDepartments()
            }
        }

    private fun setupDepartmentsRecyclerView() =
        binding.rvDepartments.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = DepartmentsAdapter(
                departments = emptyList(),
                onClick = { departmentId -> openDepartmentDetailFragment(departmentId) }
            )
        }

    private fun openCitySelectionFragment() {
        findNavController().navigate(R.id.citySelectionFragment)
    }

    private fun openDepartmentDetailFragment(departmentId: String) {
        val deepLinkUri =
            Uri.parse("app://com.example.app/departmentDetail?departmentId=$departmentId")

        val deepLinkRequest = NavDeepLinkRequest.Builder
            .fromUri(deepLinkUri)
            .build()

        findNavController().navigate(deepLinkRequest)
    }

    private fun observeDepartmentsState() =
        viewLifecycleOwner.lifecycleScope.launch {
            departmentsViewModel.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    handleDepartmentState(it)
                }
        }

    private fun handleDepartmentState(state: DepartmentsState) {
        val currentTime = SimpleDateFormat("HH:mm", Locale.UK).format(Date())
        val currentCity = state.currentCity
        if (state.departments.isNotEmpty()) {
            binding.apply {
                chipCity.text = currentCity
                tvDepartmentsCity.text =
                    getString(R.string.departments_in, currentCity, currentTime)
                (rvDepartments.adapter as DepartmentsAdapter).updateDepartments(state.departments)
            }
        }
        if (state.isLoading) {
            showDialogProgressBar()
        } else hideDialogProgressBar()

        if (state.isFetchCanceled) {
            showRetryDialog(
                title = getString(R.string.departments_fetching_error),
                error = getString(R.string.request_was_canceled),
                actionRetry = { departmentsViewModel.fetchDepartments() }
            )
            departmentsViewModel.setIsFetchCanceled(false)
            return
        }
        state.error?.let {
            showDialogGeneralError(
                getString(R.string.error_occurred),
                it.asString(requireContext())
            )
            departmentsViewModel.setStateError(null)
            return
        }
    }
}