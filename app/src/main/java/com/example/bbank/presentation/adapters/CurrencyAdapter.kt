package com.example.bbank.presentation.adapters

import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.R
import com.example.bbank.databinding.ItemCurrencyRvBinding
import com.example.bbank.presentation.utils.DecimalDigitsInputFilter
import com.google.android.material.card.MaterialCardView
import java.util.Locale

internal class CurrencyAdapter(
    private var currencyValues: List<Pair<String, String>>
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var savedPosition = -1

    inner class CurrencyViewHolder(
        binding: ItemCurrencyRvBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val etCurrencyValue: EditText = binding.etCurrencyValue
        val ivCurrency: ImageView = binding.ivCurrency
        val tvCurrencyCode: TextView = binding.tvCurrencyCode
        val cvCurrency: MaterialCardView = binding.cvCurrency
        val btnClear: ImageView = binding.btnClear
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CurrencyViewHolder {
        val binding =
            ItemCurrencyRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencyValues[position]
        with(holder) {
            setIsRecyclable(false)
            etCurrencyValue.setText(currency.second)
            tvCurrencyCode.text = currency.first.uppercase(Locale.ROOT)
            etCurrencyValue.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter())
            ivCurrency.setImageResource(setResourceId(holder, currency))
            setupItem(holder, position)
            setCurrencyValueTextChangedListener(etCurrencyValue, position)
            setCurrencyValueOnFocusChangeListener(holder)
            setBtnClearOnClickListener(holder.btnClear, position)
        }
    }

    private fun setupItem(holder: CurrencyViewHolder, position: Int) {
        with(holder) {
            if (position == savedPosition) {
                etCurrencyValue.requestFocus()
                etCurrencyValue.setSelection(etCurrencyValue.text.length)
                setCurrencyViewAppearance(holder, R.color.lime_green, 6, View.VISIBLE)
            } else {
                setCurrencyViewAppearance(holder, R.color.gray, 1, View.GONE)
            }
        }
    }

    private fun setCurrencyValueTextChangedListener(etCurrencyValue: EditText, position: Int) {
        etCurrencyValue.doAfterTextChanged { it ->
            if (etCurrencyValue.hasFocus() && it.toString().isNotEmpty() && it.toString() != ".") {
                val newValue = it.toString()
                val newCurrencyValues =
                    getNewCurrencyValues(currencyValues, currencyValues[position].first, newValue)
                savedPosition = position
                updateCurrencyValues(newCurrencyValues)
            }
            if (it.toString().isEmpty()) {
                updateCurrencyValues(currencyValues.map { Pair(it.first, "") })
            }
        }
    }

    private fun setCurrencyValueOnFocusChangeListener(holder: CurrencyViewHolder) {
        with(holder) {
            etCurrencyValue.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    setCurrencyViewAppearance(this, R.color.lime_green, 6, View.VISIBLE)
                } else {
                    setCurrencyViewAppearance(this, R.color.gray, 1, View.GONE)
                }
            }
        }
    }

    private fun setCurrencyViewAppearance(
        holder: CurrencyViewHolder,
        strokeColor: Int,
        strokeWidth: Int,
        btnClearVisibility: Int
    ) {
        with(holder) {
            cvCurrency.strokeColor = ContextCompat.getColor(holder.cvCurrency.context, strokeColor)
            cvCurrency.strokeWidth = strokeWidth
            btnClear.visibility = btnClearVisibility
        }
    }

    private fun setBtnClearOnClickListener(btnClear: ImageView, position: Int) {
        btnClear.setOnClickListener {
            savedPosition = position
            updateCurrencyValues(currencyValues.map { Pair(it.first, "") })
        }
    }

    private fun setResourceId(holder: CurrencyViewHolder, currency: Pair<String, String>): Int =
        holder.ivCurrency.context.resources.getIdentifier(
            "ic_${currency.first}", "drawable", holder.ivCurrency.context.packageName
        )

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

    internal fun updateCurrencyValues(newCurrencyValues: List<Pair<String, String>>) {
        currencyValues = newCurrencyValues.toList()
        notifyDataSetChanged()
    }

    internal fun getCurrentCurrencyValues() = currencyValues

    override fun getItemCount() = currencyValues.size

}