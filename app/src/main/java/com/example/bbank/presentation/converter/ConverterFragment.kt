package com.example.bbank.presentation.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentConverterBinding
import com.example.bbank.presentation.adapters.CurrencyAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class ConverterFragment : Fragment() {
    private lateinit var binding: FragmentConverterBinding
    private val converterViewModel by viewModels<ConverterViewModel>()

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
        populateConverterRecyclerView()
        observeCurrenciesEvent()
    }

    private fun populateConverterRecyclerView() {
        binding.currencyRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CurrencyAdapter(
                currencyRates = listOf(
                    Pair("rub", ""),
                    Pair("usd", ""),
                    Pair("eur", "")
                ),
                onCurrencyValueChanged = { currencyValues, currencyCode, newValue ->
                    handleCurrenciesValuesChanged(currencyValues, currencyCode, newValue)
                }
            )
        }
    }

    private fun handleCurrenciesValuesChanged(
        currencyValues: List<Pair<String, String>>,
        currencyCode: String,
        newValue: String
    ) {
        converterViewModel.updateCurrencyValue(currencyValues, currencyCode, newValue)
    }

    private fun observeCurrenciesEvent() {
        CoroutineScope(Dispatchers.Main).launch {
            converterViewModel.currenciesFlow().collect {
                processCurrencyEvent(it)
            }
        }
    }

    private fun processCurrencyEvent(currenciesEvent: ConverterViewModel.CurrenciesEvent) {
        when (currenciesEvent) {
            is ConverterViewModel.CurrenciesEvent.CurrenciesSuccess -> {
                handleSuccess(currenciesEvent.currencyValues)
            }

            is ConverterViewModel.CurrenciesEvent.Error -> {
                handleError(currenciesEvent.message)
            }

            else -> Unit
        }
    }

    private fun handleSuccess(currencyValues: List<Pair<String, String>>) {
        (binding.currencyRecyclerView.adapter as CurrencyAdapter).updateCurrencyRates(
            currencyValues
        )
    }

    private fun handleError(error: String) {
        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()
    }
}