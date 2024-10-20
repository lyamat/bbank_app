package com.example.bbank.presentation.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentConverterBinding
import com.example.bbank.presentation.adapters.ConverterAdapter
import com.example.bbank.presentation.departments.DepartmentsViewModel
import com.example.core.presentation.ui.UiText
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        setViewsClickListeners()
        setupConverterRecyclerView()
        observeConverterState()
    }

    private fun setViewsClickListeners() =
        binding.apply {
            btnAddCurrency.setOnClickListener {
//                currencyConverterViewModel.startAddingCurrencyInConverter()
            }
            tvSuggestionToGetData.setOnClickListener {
                departmentsViewModel.fetchRemoteDepartments()
            }
            chipBankBuy.setOnClickListener {
                chipBankSale.isChecked = false
            }
            chipBankSale.setOnClickListener {
                chipBankBuy.isChecked = false
            }
        }

    private fun setupConverterRecyclerView() =
        binding.rvConverter.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ConverterAdapter(
                currencyValues = emptyList(),
                onConverterEvent = { event ->
                    converterViewModel.handleConverterEvent(event)
                }
            )
        }

    private fun observeConverterState() =
        viewLifecycleOwner.lifecycleScope.launch {
            converterViewModel.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    handleConverterState(it)
                }
        }

    private fun handleConverterState(state: ConverterState) {
        if (state.currencyValues.isEmpty()) {
            hideConverter()
        } else {
            showConverter()
            (binding.rvConverter.adapter as ConverterAdapter).updateCurrencyValues(state.currencyValues)
        }
        state.error?.let {
            showConverterError(it)
        }
    }

    private fun hideConverter() {
        binding.apply {
            chipsConverter.visibility = View.GONE
            btnAddCurrency.visibility = View.GONE
            rvConverter.visibility = View.GONE
        }
    }

    private fun showConverter() {
        binding.apply {
            tvSuggestionToGetData.visibility = View.GONE
            tvNoData.visibility = View.GONE
            chipsConverter.visibility = View.VISIBLE
            btnAddCurrency.visibility = View.VISIBLE
            rvConverter.visibility = View.VISIBLE
        }
    }

//    private fun openDialogForCurrencyAdding() {
//        binding.apply {
//            val checkBoxBuilder = AlertDialog.Builder(context)
//            checkBoxBuilder.setTitle(getString(R.string.choose_currencies))
//
//            // TODO: (4) remove currency_codes from resourses and remake logic of taking cities
//            val currencyCodes = resources.openRawResource(R.raw.currency_codes)
//                .bufferedReader().use { it.readText() }
//                .let { Gson().fromJson(it, Array<String>::class.java).toList() }
//
//            val currentCurrencyValues =
//                (binding.currencyRecyclerView.adapter as CurrencyAdapter).getCurrencyValues()
//
//            val selectedCurrencies = currentCurrencyValues.map { it.first }
//            val currencyList =
//                currencyCodes.map { CurrencyModel(it, it in selectedCurrencies) }
//            val onlyCurrencyNameList =
//                currencyList.map { it.name.uppercase(Locale.ROOT) }.toTypedArray()
//            val onlyCurrencyIsCheckedList =
//                currencyList.map { it.isChecked }.toBooleanArray()
//
//            checkBoxBuilder.setMultiChoiceItems(
//                onlyCurrencyNameList,
//                onlyCurrencyIsCheckedList
//            ) { _, position, isChecked ->
//                currencyList[position].isChecked = isChecked
//            }
//
//            checkBoxBuilder.setPositiveButton(getString(R.string.ok)) { _, _ ->
//                val checkedCurrenciesList =
//                    currencyList.filter { it.isChecked }.map { it.name }
//                val updatedCurrencyValues = checkedCurrenciesList.map { currencyCode ->
//                    currentCurrencyValues.find { it.first == currencyCode }
//                        ?: Pair(currencyCode, "")
//                }
//                converterViewModel.endAddingCurrencyInConverter()
//                (binding.currencyRecyclerView.adapter as CurrencyAdapter).updateCurrencyValues(
//                    updatedCurrencyValues
//                )
//            }
//
//            checkBoxBuilder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
//                converterViewModel.endAddingCurrencyInConverter()
//            }
//            val dialog = checkBoxBuilder.create()
//            dialog.show()
//        }
//    }

//    private data class CurrencyModel(
//        val name: String,
//        var isChecked: Boolean = false,
//    )

    private fun showConverterError(error: UiText) =
        Snackbar.make(requireView(), error.asString(requireContext()), Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()
}