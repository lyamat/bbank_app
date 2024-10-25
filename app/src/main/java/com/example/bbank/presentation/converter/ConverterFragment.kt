package com.example.bbank.presentation.converter

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentConverterBinding
import com.example.core.presentation.ui.UiText
import com.example.core.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class ConverterFragment :
    BaseFragment<FragmentConverterBinding>(FragmentConverterBinding::inflate) {

    private val converterViewModel by viewModels<ConverterViewModel>()

    override fun setupView() {
        setViewsClickListeners()
        setupConverterRecyclerView()
        observeStates()
    }

    private fun setViewsClickListeners() =
        binding.apply {
            btnAddCurrency.setOnClickListener {
                openDialogForCurrencyAdding()
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

    private fun observeStates() =
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    converterViewModel.state.collectLatest {
                        handleConverterState(it)
                    }
                }
            }
        }

    private fun handleConverterState(state: ConverterState) {
        if (state.currencyValues.isNotEmpty()) {
            (binding.rvConverter.adapter as ConverterAdapter).updateCurrencyValues(state.currencyValues)
        }

        state.error?.let {
            showDialogGeneralError(
                getString(R.string.error_occurred),
                UiText.DynamicString(it.asString(requireContext()))
            )
            converterViewModel.setStateError(null)
        }
    }

    private fun openDialogForCurrencyAdding() {
        binding.apply {
            val checkBoxBuilder = AlertDialog.Builder(requireContext())
            checkBoxBuilder.setTitle(getString(R.string.choose_available_currencies))

            val availableCurrencyCodes = converterViewModel.state.value.availableCurrencies
            val currentCurrencyValues = converterViewModel.state.value.currencyValues

            val selectedCurrencies = currentCurrencyValues.map { it.first }
            val currencyList = availableCurrencyCodes.map { currencyCode ->
                currencyCode to (currencyCode in selectedCurrencies)
            }.toMutableList()
            val onlyCurrencyNameList = currencyList.map { it.first }.toTypedArray()
            val onlyCurrencyIsCheckedList = currencyList.map { it.second }.toBooleanArray()

            checkBoxBuilder.setMultiChoiceItems(
                onlyCurrencyNameList,
                onlyCurrencyIsCheckedList
            ) { _, position, isChecked ->
                currencyList[position] = currencyList[position].copy(second = isChecked)
            }

            checkBoxBuilder.setPositiveButton(getString(R.string.ok)) { _, _ ->
                val checkedCurrenciesList = currencyList.filter { it.second }.map { it.first }
                val updatedCurrencyValues = checkedCurrenciesList.map { currencyCode ->
                    currentCurrencyValues.find { it.first == currencyCode }
                        ?: Pair(currencyCode, "")
                }
                converterViewModel.handleConverterEvent(
                    ConverterEvent.UpdateCurrenciesInConverter(
                        updatedCurrencyValues
                    )
                )
            }

            checkBoxBuilder.setNegativeButton(getString(R.string.cancel)) { _, _ -> }

            val dialog = checkBoxBuilder.create()
            dialog.show()
        }
    }

    override fun onClickButtonCancel() = Unit
}
