package com.example.bbank.presentation.adapters

import android.text.InputFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.databinding.ItemCurrencyRvBinding
import com.example.bbank.presentation.utils.DecimalDigitsInputFilter
import java.util.Locale
import javax.inject.Inject

internal class CurrencyAdapter @Inject constructor(
    private var currencyRates: List<Pair<String, String>>
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var savedPosition = -1

    inner class CurrencyViewHolder(
        binding: ItemCurrencyRvBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val etCurrencyValue: EditText = binding.etCurrencyValue
        val ivCurrency: ImageView = binding.ivCurrency
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CurrencyViewHolder {
        val binding =
            ItemCurrencyRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.setIsRecyclable(false)

        val currency = currencyRates[position]

        holder.etCurrencyValue.setText(currency.second)
        holder.ivCurrency.setImageResource(setResourceId(holder, currency))

        setSelectionForEditText(holder.etCurrencyValue, position)
        holder.etCurrencyValue.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter())
        setCurrencyValueTextChangedListener(holder.etCurrencyValue, position)
    }

    private fun setResourceId(holder: CurrencyViewHolder, currency: Pair<String, String>): Int =
        holder.ivCurrency.context.resources.getIdentifier(
            "ic_${currency.first}", "drawable", holder.ivCurrency.context.packageName
        )

    private fun setSelectionForEditText(view: EditText, position: Int) {
        if (position == savedPosition) {
            view.requestFocus()
            view.setSelection(view.text.length)
        }
    }

    private fun setCurrencyValueTextChangedListener(etCurrencyValue: EditText, position: Int) {
        etCurrencyValue.doAfterTextChanged {
            if (etCurrencyValue.hasFocus() && it.toString().isNotEmpty() && it.toString() != ".") {
                val newValue = it.toString()
                val newCurrencyValues =
                    getNewCurrencyValues(currencyRates, currencyRates[position].first, newValue)
                savedPosition = position
                updateCurrencyRates(newCurrencyValues)
            }
        }
    }

    override fun getItemCount() = currencyRates.size

    // TODO: move to domain layer OR add in adapter constructor val conversionRates
    private fun getNewCurrencyValues(
        currencyValues: List<Pair<String, String>>, currencyCode: String, newValue: String
    ): List<Pair<String, String>> {
        return currencyValues.map { pair ->
            if (pair.first == currencyCode) {
                pair.copy(second = newValue)
            } else {
                val conversionRate = getConversionRate(currencyCode, pair.first)
                val newPairValue = String.format(
                    Locale.US, "%.2f", newValue.replace(',', '.').toDouble() * conversionRate
                )
                pair.copy(second = newPairValue)
            }
        }
    }

    private fun getConversionRate(from: String, to: String): Double {
        return when {
            from == "usd" && to == "rub" -> 93.0
            from == "usd" && to == "eur" -> 0.9319
            from == "rub" && to == "usd" -> 0.0107
            from == "rub" && to == "eur" -> 0.0099
            from == "eur" && to == "usd" -> 1.0731
            from == "eur" && to == "rub" -> 100.6735
            else -> 1.0
        }
    }

    private fun updateCurrencyRates(newCurrencyRates: List<Pair<String, String>>) {
        currencyRates = newCurrencyRates.toList()
        notifyDataSetChanged()
    }
}