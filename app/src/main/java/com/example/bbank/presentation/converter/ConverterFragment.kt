package com.example.bbank.presentation.converter

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentConverterBinding
import com.example.bbank.domain.models.ConversionRate
import com.example.bbank.presentation.adapters.CurrencyAdapter
import com.example.bbank.presentation.departments.DepartmentsState
import com.example.bbank.presentation.departments.DepartmentsViewModel
import com.example.core.presentation.ui.UiText
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
internal class ConverterFragment : Fragment() {
    private lateinit var binding: FragmentConverterBinding
    private val converterViewModel by viewModels<ConverterViewModel>()
    private val departmentsViewModel by activityViewModels<DepartmentsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        setChipsOnClickListeners()
        observeDepartmentsUiState()
        observeConverterUiState()
    }

    override fun onPause() {
        super.onPause()
        converterViewModel.saveCurrencyValues((binding.currencyRecyclerView.adapter as CurrencyAdapter).getCurrencyValues())
    }

    private fun setOnClickListeners() =
        binding.apply {
            btnAddCurrency.setOnClickListener {
                converterViewModel.startAddingCurrencyInConverter()
            }
            tvSuggestionToGetData.setOnClickListener {
                departmentsViewModel.fetchRemoteDepartments()
            }
        }

    private fun observeDepartmentsUiState() =
        viewLifecycleOwner.lifecycleScope.launch {
            departmentsViewModel.state.collectLatest {
                handleDepartmentsUiState(it)
            }
        }

    private fun handleDepartmentsUiState(departmentsState: DepartmentsState) {
        if (departmentsState.departments.isNotEmpty()) {
            converterViewModel.getDataForConverterAdapter()
        }
        departmentsState.error?.let {
            handleConverterError(it)
        }
        if (departmentsState.isLoading)
            showLoading()
        else hideLoading()
    }

    private fun setChipsOnClickListeners() =
        binding.apply {
            chipBankBuy.setOnClickListener {
                chipBankSale.isChecked = false
                (currencyRecyclerView.adapter as CurrencyAdapter).showRates("in")
            }
            chipBankSale.setOnClickListener {
                chipBankBuy.isChecked = false
                (currencyRecyclerView.adapter as CurrencyAdapter).showRates("out")
            }
        }

    private fun populateConverterRecyclerView(
        currencyValues: List<Pair<String, String>>,
        conversionRates: List<ConversionRate>
    ) =
        binding.currencyRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CurrencyAdapter(
                currencyValues,
                conversionRates,
                messageCallback = { message ->
                    handleConverterError(message)
                }
            )
        }

    private fun observeConverterUiState() =
        viewLifecycleOwner.lifecycleScope.launch {
            converterViewModel.converterUiState.collectLatest {
                handleConverterUiState(it)
            }
        }

    private fun handleConverterUiState(converterUiState: ConverterUiState) {
        if (converterUiState.isCurrencyAdding) {
            openDialogForCurrencyAdding()
        } else {
            populateConverterRecyclerView(
                converterUiState.currencyValues,
                converterUiState.conversionRates
            )
            if (converterUiState.conversionRates.isEmpty()) {
                hideConverter()
            } else showConverter()
            converterUiState.error?.let {
                handleConverterError(it)
            }
            if (converterUiState.isLoading)
                showLoading()
            else hideLoading()
        }
    }

    private fun hideConverter() {
        binding.apply {
            chipsConverter.visibility = View.GONE
            btnAddCurrency.visibility = View.GONE
            currencyRecyclerView.visibility = View.GONE
        }
    }

    private fun showConverter() {
        binding.apply {
            tvSuggestionToGetData.visibility = View.GONE
            tvNoData.visibility = View.GONE
            chipsConverter.visibility = View.VISIBLE
            btnAddCurrency.visibility = View.VISIBLE
            currencyRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun openDialogForCurrencyAdding() {
        binding.apply {
            val checkBoxBuilder = AlertDialog.Builder(context)
            checkBoxBuilder.setTitle(getString(R.string.choose_currencies))

            // TODO: (4) remove currency_codes from resourses and remake logic of taking cities
            val currencyCodes = resources.openRawResource(R.raw.currency_codes)
                .bufferedReader().use { it.readText() }
                .let { Gson().fromJson(it, Array<String>::class.java).toList() }

            val currentCurrencyValues =
                (binding.currencyRecyclerView.adapter as CurrencyAdapter).getCurrencyValues()

            val selectedCurrencies = currentCurrencyValues.map { it.first }
            val currencyList =
                currencyCodes.map { CurrencyModel(it, it in selectedCurrencies) }
            val onlyCurrencyNameList =
                currencyList.map { it.name.uppercase(Locale.ROOT) }.toTypedArray()
            val onlyCurrencyIsCheckedList =
                currencyList.map { it.isChecked }.toBooleanArray()

            checkBoxBuilder.setMultiChoiceItems(
                onlyCurrencyNameList,
                onlyCurrencyIsCheckedList
            ) { _, position, isChecked ->
                currencyList[position].isChecked = isChecked
            }

            checkBoxBuilder.setPositiveButton(getString(R.string.ok)) { _, _ ->
                val checkedCurrenciesList =
                    currencyList.filter { it.isChecked }.map { it.name }
                val updatedCurrencyValues = checkedCurrenciesList.map { currencyCode ->
                    currentCurrencyValues.find { it.first == currencyCode }
                        ?: Pair(currencyCode, "")
                }
                converterViewModel.endAddingCurrencyInConverter()
                (binding.currencyRecyclerView.adapter as CurrencyAdapter).updateCurrencyValues(
                    updatedCurrencyValues
                )
            }

            checkBoxBuilder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
                converterViewModel.endAddingCurrencyInConverter()
            }
            val dialog = checkBoxBuilder.create()
            dialog.show()
        }
    }

    private data class CurrencyModel(
        val name: String,
        var isChecked: Boolean = false,
    )

    private fun handleConverterError(error: UiText) =
        Snackbar.make(requireView(), error.asString(requireContext()), Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()

    private fun hideLoading() {
        binding.progressIndicatorConverter.visibility = View.GONE
    }

    private fun showLoading() {
        binding.progressIndicatorConverter.visibility = View.VISIBLE
    }
}