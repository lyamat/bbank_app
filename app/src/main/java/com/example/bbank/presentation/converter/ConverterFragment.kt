package com.example.bbank.presentation.converter

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentConverterBinding
import com.example.bbank.domain.models.ConversionRate
import com.example.bbank.presentation.adapters.CurrencyAdapter
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
        setBtnAddCurrencyOnClickListener()
        setChipsOnClickListeners()
        observeConverterEvent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDataForConverterAdapter()
    }

    private fun setDataForConverterAdapter() = converterViewModel.getDataForConverterAdapter()

    private fun setBtnAddCurrencyOnClickListener() =
        binding.apply {
            btnAddCurrency.setOnClickListener {
                converterViewModel.getCurrencyValues()
            }
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
                    handleError(message)
                }
            )
        }


    override fun onPause() {
        super.onPause()
        binding.apply {
            val currencyValues =
                (currencyRecyclerView.adapter as CurrencyAdapter).getCurrencyValues()
            converterViewModel.setCurrencyValues(currencyValues)
        }
    }

    private fun observeConverterEvent() =
        viewLifecycleOwner.lifecycleScope.launch {
            converterViewModel.converterFlow().collectLatest {
                processConverterEvent(it)
            }
        }

    private fun processConverterEvent(currenciesEvent: ConverterViewModel.ConverterEvent) =
        when (currenciesEvent) {
            is ConverterViewModel.ConverterEvent.AdapterDataSuccess -> {
                handleAdapterDataSuccess(
                    currenciesEvent.currencyValues,
                    currenciesEvent.conversionRates
                )
                hideLoading()
            }

            is ConverterViewModel.ConverterEvent.CurrencyValuesSuccess -> {
                handleCurrencyValuesSuccess(currenciesEvent.currencyValues)
                hideLoading()
            }

            is ConverterViewModel.ConverterEvent.Loading -> {
                showLoading()
            }

            is ConverterViewModel.ConverterEvent.Error -> {
                handleError(currenciesEvent.message)
            }

            else -> Unit
        }

    private fun handleAdapterDataSuccess(
        currencyValues: List<Pair<String, String>>, conversionRates: List<ConversionRate>
    ) = populateConverterRecyclerView(currencyValues, conversionRates)

    private fun handleCurrencyValuesSuccess(currencyValues: List<Pair<String, String>>) {
        binding.apply {
            val checkBoxBuilder = AlertDialog.Builder(context)
            checkBoxBuilder.setTitle(getString(R.string.choose_currencies))

            val currencyCodes = resources.openRawResource(R.raw.currency_codes)
                .bufferedReader().use { it.readText() }
                .let { Gson().fromJson(it, Array<String>::class.java).toList() }

            val selectedCurrencies = currencyValues.map { it.first }
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
                    currencyValues.find { it.first == currencyCode }
                        ?: Pair(currencyCode, "")
                }
                (binding.currencyRecyclerView.adapter as CurrencyAdapter).updateCurrencyValues(
                    updatedCurrencyValues
                )
            }

            checkBoxBuilder.setNegativeButton(getString(R.string.cancel), null)
            val dialog = checkBoxBuilder.create()
            dialog.show()
        }
    }

    private data class CurrencyModel(
        val name: String,
        var isChecked: Boolean = false,
    )

    private fun handleError(error: String) =
        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()

    private fun hideLoading() {
        binding.progressIndicatorConverter.visibility = View.GONE
    }

    private fun showLoading() {
        binding.progressIndicatorConverter.visibility = View.VISIBLE
    }
}