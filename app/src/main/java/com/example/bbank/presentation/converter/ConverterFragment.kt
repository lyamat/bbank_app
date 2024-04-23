package com.example.bbank.presentation.converter

import android.app.AlertDialog
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
import com.google.gson.Gson
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
        setBtnAddCurrencyOnClickListener()
    }

    private fun setBtnAddCurrencyOnClickListener() {
        binding.apply {
            btnAddCurrency.setOnClickListener {
                // TODO: remove  setCurrencyValues if i prefer to set it from adapter onTextChanged
                converterViewModel.setCurrencyValues((currencyRecyclerView.adapter as CurrencyAdapter).getCurrentCurrencyValues())
                val checkBoxBuilder = AlertDialog.Builder(context)
                checkBoxBuilder.setTitle("Choose Currencies")

                val currencyCodes = resources.openRawResource(R.raw.currency_codes)
                    .bufferedReader().use { it.readText() }
                    .let { Gson().fromJson(it, Array<String>::class.java).toList() }

                converterViewModel.getCurrencyValues { currencyValues ->
                    val selectedCurrencies = currencyValues.map { it.first }
                    val currencyList =
                        currencyCodes.map { CurrencyModel(it, it in selectedCurrencies) }
                    val onlyCurrencyNameList = currencyList.map { it.name }.toTypedArray()
                    val onlyCurrencyIsCheckedList =
                        currencyList.map { it.isChecked }.toBooleanArray()

                    checkBoxBuilder.setMultiChoiceItems(
                        onlyCurrencyNameList,
                        onlyCurrencyIsCheckedList
                    ) { _, position, isChecked ->
                        currencyList[position].isChecked = isChecked
                    }

                    checkBoxBuilder.setPositiveButton("Ok") { _, _ ->
                        val checkedCurrenciesList =
                            currencyList.filter { it.isChecked }.map { it.name }
                        val updatedCurrencyValues = checkedCurrenciesList.map { currencyCode ->
                            currencyValues.find { it.first == currencyCode }
                                ?: Pair(currencyCode, "")
                        }
                        converterViewModel.setCurrencyValues(updatedCurrencyValues)
                    }

                    checkBoxBuilder.setNegativeButton("Cancel", null)
                    val dialog = checkBoxBuilder.create()
                    dialog.show()
                }
            }
        }
    }

    override fun onDestroyView() {
        converterViewModel.setCurrencyValues((binding.currencyRecyclerView.adapter as CurrencyAdapter).getCurrentCurrencyValues())
        super.onDestroyView()
    }

    data class CurrencyModel(
        val name: String,
        var isChecked: Boolean = false,
    )


    private fun populateConverterRecyclerView() =
        converterViewModel.getCurrencyValues { currencyValues ->
            binding.currencyRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = CurrencyAdapter(currencyValues)
            }
        }

//    private fun handleCurrenciesValuesChanged(
//        currencyValues: List<Pair<String, String>>,
//        currencyCode: String,
//        newValue: String
//    ) {
//        converterViewModel.updateCurrencyValue(currencyValues, currencyCode, newValue)
//    }

    private fun observeCurrenciesEvent() =
        CoroutineScope(Dispatchers.Main).launch {
            converterViewModel.currenciesFlow().collect {
                processCurrencyEvent(it)
            }
        }

    private fun processCurrencyEvent(currenciesEvent: ConverterViewModel.CurrenciesEvent) =
        when (currenciesEvent) {
            is ConverterViewModel.CurrenciesEvent.CurrenciesSuccess -> {
                handleSuccess(currenciesEvent.currencyValues)
            }

            is ConverterViewModel.CurrenciesEvent.Error -> {
                handleError(currenciesEvent.message)
            }

            else -> Unit
        }

    private fun handleSuccess(currencyValues: List<Pair<String, String>>) {
        (binding.currencyRecyclerView.adapter as CurrencyAdapter).updateCurrencyValues(
            currencyValues
        )
    }

    private fun handleError(error: String) =
        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()
}