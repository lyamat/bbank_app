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
import com.example.bbank.domain.models.Currency
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
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = CurrencyAdapter(
                currencies = emptyList(),
                onCurrencyValueChanged = { currencyCode, newValue ->
                }
            )
        }
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
                handleSuccess(currenciesEvent.currencies)
//                hideLoading()
            }

            is ConverterViewModel.CurrenciesEvent.Loading -> {
//                showLoading()
            }

            is ConverterViewModel.CurrenciesEvent.Error -> {
                handleError(currenciesEvent.message)
//                hideLoading()
            }

            else -> Unit
        }
    }

    private fun handleSuccess(currencies: List<Currency>) {
    }

    private fun handleError(error: String) {
        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()
    }


}